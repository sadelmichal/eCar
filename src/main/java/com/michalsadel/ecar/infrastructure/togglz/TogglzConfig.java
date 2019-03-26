package com.michalsadel.ecar.infrastructure.togglz;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.togglz.core.context.StaticFeatureManagerProvider;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.manager.FeatureManagerBuilder;
import org.togglz.core.repository.mem.InMemoryStateRepository;
import org.togglz.core.spi.FeatureProvider;
import org.togglz.core.user.NoOpUserProvider;

@Configuration
class TogglzConfig {
    @Bean
    FeatureManager featureManager(FeatureProvider featureProvider) {
        FeatureManager featureManager = FeatureManagerBuilder.begin()
                .featureProvider(featureProvider)
                .stateRepository(new InMemoryStateRepository())
                .userProvider(new NoOpUserProvider())
                .build();
        StaticFeatureManagerProvider.setFeatureManager(featureManager);
        return featureManager;
    }
}
