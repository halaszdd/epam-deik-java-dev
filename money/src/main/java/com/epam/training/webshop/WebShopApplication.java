package com.epam.training.webshop;

import com.epam.training.webshop.config.ApplicationConfig;
import com.epam.training.webshop.presentation.cli.CliInterpreter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.Arrays;

/* Az alkalmazás belépés pontja. */
@SpringBootApplication
public class WebShopApplication {

    public static void main(String[] args) throws IOException {
//        ApplicationConfig.cliInterpreter().start();
        ConfigurableApplicationContext context = SpringApplication.run(WebShopApplication.class, args);
//        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
        CliInterpreter cli = context.getBean(CliInterpreter.class);
        cli.start();
    }
}
