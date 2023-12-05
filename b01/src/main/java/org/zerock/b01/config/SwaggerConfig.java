package org.zerock.b01.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@OpenAPIDefinition(
//        info = @Info(title = "Zerock App",version = "v1"))
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi restApi() {

        return GroupedOpenApi.builder()
                .group("api")
                .packagesToScan("org.zerock.b01.controller")
                .pathsToExclude("/board/*")
                .build();
    }
    @Bean
    public GroupedOpenApi commonApi() {

        return GroupedOpenApi.builder()
                .group("basic")
                .pathsToMatch("/board/*")
                .build();
    }

//    @Bean
//    public GroupedOpenApi restApi(){
//
//        return GroupedOpenApi.builder()
//                .pathsToMatch("/replies/**")
//                .group("REST API")
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi restUpload(){
//
//        return GroupedOpenApi.builder()
//                .pathsToMatch("/upload/**")
//                .group("REST UPLOAD API")
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi commonApi() {
//
//        return GroupedOpenApi.builder()
//                .pathsToMatch("/**/*")
//                .pathsToExclude("/upload/**/*", "/replies/**/*")
//                .group("COMMON API")
//                .build();
//    }

   /* @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/**"};

        return GroupedOpenApi.builder()
                .group("Zerock OPEN API v1")
                .pathsToMatch(paths)
                .build();
    }*/

}
