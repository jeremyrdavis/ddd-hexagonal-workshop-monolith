package io.arrogantprogrammer.init;

import io.quarkus.logging.Log;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import java.util.Comparator;
import java.util.List;

@ApplicationScoped
@Startup
public class StartupInitializer {

    @Inject
    Instance<DataInitializer> initializers;

    @PostConstruct
    void onStart() {
        Log.info("Starting data initialization...");
        
        List<DataInitializer> sortedInitializers = initializers.stream()
            .sorted(Comparator.comparingInt(DataInitializer::getOrder))
            .toList();
            
        for (DataInitializer initializer : sortedInitializers) {
            try {
                Log.info("Running initializer: " + initializer.getClass().getSimpleName());
                initializer.initialize();
            } catch (Exception e) {
                Log.error("Error during initialization: " + initializer.getClass().getSimpleName(), e);
            }
        }
        
        Log.info("Data initialization completed.");
    }
} 