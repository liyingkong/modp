package com.senlang.modp;

import com.senlang.modp.service.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(StorageProperties.class)
public class ModpApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModpApplication.class, args);
    }

    @Autowired
    StorageProperties storageProperties;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowCredentials(false).maxAge(3600);
            }

        };
    }


    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        RestTemplate restTmp =new RestTemplate();
        List<HttpMessageConverter<?>> cList = restTmp.getMessageConverters();
        Optional<HttpMessageConverter<?>> shmc = cList.parallelStream().filter(c->c.getClass()==StringHttpMessageConverter.class).findFirst();
        if(shmc.isPresent()){
            //cList.remove(shmc.get());
            int index = cList.indexOf(shmc.get());
            cList.set(index,new StringHttpMessageConverter(StandardCharsets.UTF_8));
        }
        restTmp.setMessageConverters(cList);
        return restTmp;
    }

//    @Bean
//    CommandLineRunner init(StorageService storageService) {
//        return (args) -> {
//            // storageService.deleteAll();
//            storageService.init();
//        };
//    }
}
