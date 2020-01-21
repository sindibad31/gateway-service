package fr.grimeh.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
    @Bean
    RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(r -> r
                    .path("/publicCountries/**")
                    .filters(f -> f
                            .addRequestHeader("x-rapidapi-host", "restcountries-v1.p.rapidapi.com")
                            .addRequestHeader("x-rapidapi-key", "eddf15ba4fmsh15aabb222d7f4c2p13df0fjsn11ab01991ef7")
                            .rewritePath("/publicCountries/(?<segment>.*)", "/${segment}")
                    )
                    .uri("https://restcountries-v1.p.rapidapi.com/").id("r1"))
                .route(r -> r
                        .path("/muslim/**")
                        .filters(f -> f
                                .addRequestHeader("x-rapidapi-host", "muslimsalat.p.rapidapi.com")
                                .addRequestHeader("x-rapidapi-key", "eddf15ba4fmsh15aabb222d7f4c2p13df0fjsn11ab01991ef7")
                                .rewritePath("/muslim/(?<segment>.*)", "/${segment}")
                        )
                        .uri("https://muslimsalat.p.rapidapi.com/").id("r2"))
                .build();

        /*return builder.routes()
                .route(r -> r.path("/customers/**").uri("lb://CUSTOMER-SERVICE").id("r1"))
                .route(r -> r.path("/products/**").uri("lb://INVENTORY-SERVICE").id("r2"))
                .build();*/
        /*return builder.routes()
                .route(r->r.path("/customers/**").uri("http://localhost:8081/").id("r1"))
                .route(r->r.path("/products/**").uri("http://localhost:8082/").id("r2"))
                .build();*/
    }

    @Bean
    DiscoveryClientRouteDefinitionLocator dynamicRoutes(ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties dlp) {
        return new DiscoveryClientRouteDefinitionLocator(rdc, dlp);
    }
}

