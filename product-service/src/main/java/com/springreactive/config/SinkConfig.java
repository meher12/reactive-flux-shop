package com.springreactive.config;

import com.springreactive.dto.ProductDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

// 1. Declare this class as a Spring configuration class
@Configuration
public class SinkConfig {

    // 2. Define a bean that creates a Sinks.Many instance for broadcasting product events
    @Bean
    public Sinks.Many<ProductDto> sink() {
        // 3. Create a Sinks.Many with replay strategy (replays the latest item to new subscribers)
        //    and limit of 1 to store only the latest emitted item
        return Sinks.many().replay().limit(1);
    }

    // 4. Define a bean that creates a Flux for broadcasting events from the sink
    @Bean
    public Flux<ProductDto> productBroadcast(Sinks.Many<ProductDto> sink) {
        // 5. Convert the Sinks.Many into a Flux, allowing multiple subscribers to receive events
        return sink.asFlux();
    }
}
