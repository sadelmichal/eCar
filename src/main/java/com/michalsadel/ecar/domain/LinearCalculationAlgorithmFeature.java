package com.michalsadel.ecar.domain;

import org.togglz.core.*;
import org.togglz.core.annotation.*;

enum LinearCalculationAlgorithmFeature implements Feature {
    @Label("Linear based calculation")
    @EnabledByDefault
    LINEAR_BASED_CALCULATION
}
