#!/bin/bash

case `uname -s` in
  MINGW*)
    USER_UID=1000
    GROUP_UID=1000
    ;;
  *)
    if [ -z ${USER_UID:+x} ]
    then
      USER_UID=`id -u`
      GROUP_GID=`id -g`
    fi
esac

logs () {
    docker-compose -f docker-compose.dev.yml logs -f
}

init () {
    if [ ! -f .env.dev ]; then
      touch .env.dev
      echo "File .env.dev created successfully!"
    else
      echo "File .env.dev already exists."
    fi

    docker-compose -f docker-compose.dev.yml --env-file .env.dev up -d maven
    echo "Waiting 60s for starting spring application"
    sleep 60
    curl -XPUT "http://localhost:9200/articlenumerique/_settings" -H 'Content-Type: application/json' -d'{"index" : {"max_result_window" : 100000}}'
    curl -XPUT "http://localhost:9200/articlepapier/_settings" -H 'Content-Type: application/json' -d'{"index" : {"max_result_window" : 100000}}'
    echo "Finish init"
}

start () {
    docker-compose -f docker-compose.dev.yml --env-file .env.dev run --rm --no-deps maven ./mvnw clean
    sleep 3
    docker-compose -f docker-compose.dev.yml --env-file .env.dev up -d maven
}

stop () {
    docker-compose -f docker-compose.dev.yml --env-file .env.dev stop
}

compile () {
    docker-compose -f docker-compose.dev.yml --env-file .env.dev run --rm --no-deps maven ./mvnw -Pprod -Dmaven.test.skip=true clean verify
}

manualLaunch () {
    TOKEN_DATA=$(docker exec -it moisson-crre curl -X POST -H 'Accept: application/json' -H 'Content-Type: application/json' --data '{"username":"admin","password":"admin"}' http://localhost:8085/api/authenticate)
    ID_TOKEN=$(echo "$TOKEN_DATA" | sed -n 's/.*"id_token":"\([^"]*\).*/\1/p')

    docker exec -it moisson-crre curl -X POST -H 'Accept: application/json' -H "Authorization: Bearer $ID_TOKEN" http://localhost:8085/api/json/all
}

diff () {
    if docker ps | grep -q "moisson-crre"; then
      docker exec -it moisson-crre mvn clean compile liquibase:diff
    else
      docker-compose -f docker-compose.dev.yml --env-file .env.dev run --rm --no-deps maven mvn clean compile liquibase:diff
    fi
}

update () {
    if docker ps | grep -q "moisson-crre"; then
      docker exec -it moisson-crre mvn clean compile liquibase:update
    else
      docker-compose -f docker-compose.dev.yml --env-file .env.dev run --rm --no-deps maven mvn clean compile liquibase:update
    fi
}

for param in "$@"
do
    case $param in
        logs)
            logs
            ;;
        compile)
            compile
            ;;
        init)
            init
            ;;
        start)
            start
            ;;
        stop)
            stop
            ;;
        manualLaunch)
            manualLaunch
            ;;
        diff)
            diff
            ;;
        update)
            update
            ;;
        *)
            echo "Invalid argument : $param"
    esac
    if [ ! $? -eq 0 ]; then
        exit 1
    fi
done
