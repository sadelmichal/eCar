package com.michalsadel.ecar.infrastructure.togglz;

import org.springframework.context.annotation.*;
import org.togglz.core.context.*;
import org.togglz.core.manager.*;
import org.togglz.core.repository.mem.*;
import org.togglz.core.spi.*;
import org.togglz.core.user.*;

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
