package com.michalsadel.ecar.charge;

import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;

enum OverlapBasedCalculationFeature implements Feature {
    @Label("Overlap based calculation")
    @EnabledByDefault
    OVERLAP_BASED_CALCULATION_FEATURE
}
