package com.springreactive.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

//@Service
public class DataSetupService implements CommandLineRunner {

    // Injects an SQL script resource from the classpath
    @Value("classpath:h2/init.sql")
    private Resource initSql;

    // Injects an R2DBC entity template for database interactions
    @Autowired
    private R2dbcEntityTemplate entityTemplate;

    // Implements the CommandLineRunner interface to execute code on application startup
    @Override
    public void run(String... args) throws Exception {
        // Reads the SQL script from the resource into a string
        String query = StreamUtils.copyToString(initSql.getInputStream(), StandardCharsets.UTF_8);

        // Prints the SQL query to the console (likely for debugging or logging purposes)
        System.out.println(query);

        // Executes the SQL query against the database using the entity template
        this.entityTemplate
                .getDatabaseClient()  // Obtains the database client
                .sql(query)          // Prepares the SQL statement
                .then()              // Initiates asynchronous execution
                .subscribe();        // Subscribes to handle the completion signal
    }
}
