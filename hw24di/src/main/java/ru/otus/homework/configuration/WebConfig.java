package ru.otus.homework.configuration;

import lombok.var;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.IOException;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"ru.otus.homework"})
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FreeMarkerViewResolver freemarkerViewResolver() {
        var resolver = new FreeMarkerViewResolver();
        resolver.setCache(true);
        resolver.setSuffix(".html");

        // https://stackoverflow.com/questions/1249205/how-to-get-the-request-context-in-a-freemaker-template-in-spring
        resolver.setRequestContextAttribute("rc");

        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() throws IOException {
        // https://www.baeldung.com/freemarker-in-spring-mvc-tutorial
        var freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/templates/");

        return freeMarkerConfigurer;
    }
}