package com.expensemanager;

import com.expensemanager.entity.Category;
import com.expensemanager.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * Main application class for the Expense Manager Spring Boot application.
 * This class serves as the entry point and configuration provider for the app.
 */
@SpringBootApplication
public class ExpenseManagerApplication {

    /**
     * Entry point of the application.
     * @param args Command line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(ExpenseManagerApplication.class, args);
    }

    /**
     * Initializes default expense categories in the database on startup.
     * This bean is executed after the application context is loaded.
     * It ensures essential categories exist for users to assign to their expenses.
     *
     * @param categoryRepository The repository used to access and persist categories.
     * @return A CommandLineRunner that executes the data seeding logic.
     */
    @Bean
    public CommandLineRunner dataSeeder(CategoryRepository categoryRepository) {
        return args -> {
            // Check if categories table is empty to avoid duplicating data on restarts
            if (categoryRepository.count() == 0) {
                // Save a predefined list of common expense categories
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
