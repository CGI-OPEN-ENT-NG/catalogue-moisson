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
    docker-compose logs -f
}

init () {
    docker-compose --env-file .env.dev up -d maven
    sleep 15
    curl -XPUT "http://localhost:9200/articlenumerique/_settings" -H 'Content-Type: application/json' -d'{"index" : {"max_result_window" : 100000}}'
    curl -XPUT "http://localhost:9200/articlenumerique/_settings" -H 'Content-Type: application/json' -d'{"index" : {"max_result_window" : 100000}}'
}

start () {
    docker-compose --env-file .env.dev up -d maven
}

stop () {
    docker-compose --env-file .env.dev stop
}

compile () {
    docker-compose --env-file .env.dev run --rm --no-deps maven ./mvnw -Pprod -Dmaven.test.skip=true clean verify
}

manualLaunch () {
    TOKEN_DATA=$(docker exec -it moisson-crre curl -X POST -H 'Accept: application/json' -H 'Content-Type: application/json' --data '{"username":"admin","password":"admin"}' http://localhost:8085/api/authenticate)
    ID_TOKEN=$(echo $TOKEN_DATA | sed -n 's/.*"id_token":"\([^"]*\).*/\1/p')

    docker exec -it moisson-crre curl -X POST -H 'Accept: application/json' -H "Authorization: Bearer $ID_TOKEN" http://localhost:8085/api/json/all
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
        *)
            echo "Invalid argument : $param"
    esac
    if [ ! $? -eq 0 ]; then
        exit 1
    fi
done
