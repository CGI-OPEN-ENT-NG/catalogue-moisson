# Bug Swagger 2

1 - Dans SwaggerConfiguration il faut commenter @EnableSwagger2WebFlux et ajouter @ComponentScan({"springfox.documentation.schema"})
et remplacer return new Docket(DocumentationType.SWAGGER_2) par return new Docket(DocumentationType.OAS_30)

    package fr.openent.moisson.config.apidoc;

    import io.github.jhipster.config.JHipsterConstants;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.context.annotation.Primary;
    import org.springframework.context.annotation.Profile;
    import springfox.documentation.builders.PathSelectors;
    import springfox.documentation.builders.RequestHandlerSelectors;
    import springfox.documentation.spi.DocumentationType;
    import springfox.documentation.spring.web.plugins.Docket;

    @Primary
    @Profile(JHipsterConstants.SPRING_PROFILE_SWAGGER)
    @Configuration
    // @EnableSwagger2WebFlux
    @ComponentScan({"springfox.documentation.schema"})
    public class SwaggerConfiguration {

        @Bean
        public Docket api() {
            return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
        }
    }

2 - dans le pom.XML :

       <!-- Comment this dependencies -->
       <!--  <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>${springfox.version}</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>${springfox.version}</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-spring-webflux</artifactId>
            <version>${springfox.version}</version>
        </dependency> -->

    	<!-- Add this dependencies -->
    	<dependency>
    		<groupId>io.springfox</groupId>
    		<artifactId>springfox-boot-starter</artifactId>
    		<version>${springfox.version}</version>
    		<exclusions>
    			<exclusion>
    				<groupId>io.springfox</groupId>
    				<artifactId>springfox-bean-validators</artifactId>
    			</exclusion>
    			<exclusion>
    				<groupId>io.springfox</groupId>
    				<artifactId>springfox-swagger2</artifactId>
    			</exclusion>
    		</exclusions>
    	</dependency>
    	<dependency>
    		<groupId>io.springfox</groupId>
    		<artifactId>springfox-bean-validators</artifactId>
    		<version>${springfox.version}</version>
    	</dependency>
    	<dependency>
    		<groupId>io.springfox</groupId>
    		<artifactId>springfox-swagger2</artifactId>
    		<version>${springfox.version}</version>
    	</dependency>
    	<!--  end of add dependencies -->

Probl-me run avec IntelliJ :
rm -rf .idea \*.iml
