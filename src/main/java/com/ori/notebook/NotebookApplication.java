package com.ori.notebook;

import com.ori.notebook.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NotebookApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotebookApplication.class, args);
    }
    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }
}
