package io.arrogantprogrammer.init;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

public interface DataInitializer {
    
    @Transactional
    void initialize();
    
    int getOrder();
} 