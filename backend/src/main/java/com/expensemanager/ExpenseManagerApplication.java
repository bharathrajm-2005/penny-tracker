package com.expensemanager;

import com.expensemanager.entity.Category;
import com.expensemanager.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ExpenseManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpenseManagerApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataSeeder(CategoryRepository categoryRepository) {
        return args -> {
            if (categoryRepository.count() == 0) {
                categoryRepository.saveAll(List.of(
                        Category.builder().name("Food & Dining").icon("utensils").build(),
                        Category.builder().name("Transportation").icon("car").build(),
                        Category.builder().name("Entertainment").icon("film").build(),
                        Category.builder().name("Shopping").icon("shopping-bag").build(),
                        Category.builder().name("Utilities").icon("bolt").build(),
                        Category.builder().name("Housing").icon("home").build(),
                        Category.builder().name("Personal Care").icon("smile").build(),
                        Category.builder().name("Health").icon("heartbeat").build()
                ));
            }
        };
    }
}
