package ru.itpark.issue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import ru.itpark.issue.domain.Issue;
import ru.itpark.issue.service.IssueService;

import static org.springframework.web.servlet.function.RouterFunctions.route;
import static org.springframework.web.servlet.function.ServerResponse.noContent;
import static org.springframework.web.servlet.function.ServerResponse.ok;

@SpringBootApplication
public class IssueApplication {
    public static void main(String[] args) {
        SpringApplication.run(IssueApplication.class, args);
    }

    @Bean
    public RouterFunction routerFunction(IssueService issueService) {
        return route()
                .path("/api/issues", builder -> builder
                        .GET("", req -> ok().body(issueService.getAll()))
                        .GET("/search", req -> ok().body(issueService.getByName(req.param("name").get())))
                        .GET("/{id}", req -> ok().body(issueService.getById(Integer.valueOf(req.pathVariable("id")))))
                        .POST("", req -> {
                            var issue = req.body(Issue.class);
                            issueService.save(issue);
                            return noContent().build();
                        })
                ).build();
    }

    @Bean
    public ViewResolver viewResolver() {
        return (viewName, locale) -> new MappingJackson2JsonView();
    }


//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl("jdbc:postgresql://127.0.0.1:5432/devdb");
//        dataSource.setUsername("postgres");
//        dataSource.setPassword("Zaq12wsx");
//        return dataSource;
//    }

    @Bean
    public CorsFilter corsFilter() {
        var config = new CorsConfiguration();
        config.applyPermitDefaultValues();

        // URL - CORS
        var source = new UrlBasedCorsConfigurationSource();
        // ** - съедают /
        source.registerCorsConfiguration("/api/**", config);

        return new CorsFilter(source);
    }
}

