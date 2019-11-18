package ru.otus.homework.configuration;

import lombok.var;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import java.io.File;
import java.io.IOException;

@org.springframework.context.annotation.Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"ru.otus.homework.configuration"})
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FreeMarkerViewResolver freemarkerViewResolver() {

        var resolver = new FreeMarkerViewResolver();
        resolver.setCache(true);
        resolver.setSuffix(".html");
        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() throws IOException {
        var freeMarkerConfigurer = new FreeMarkerConfigurer();

        freemarker.template.Configuration configuration = new freemarker.template.Configuration();
        configuration.setDirectoryForTemplateLoading(new File("C:\\Program Files\\OtusHW\\hw24di\\src\\main\\resources\\templates"));
        freeMarkerConfigurer.setConfiguration(configuration);
        return freeMarkerConfigurer;
    }
}