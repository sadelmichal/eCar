package com.michalsadel.ecar.charge;

import org.togglz.core.*;
import org.togglz.core.annotation.*;

enum OverlapBasedCalculationFeature implements Feature {
    @Label("Overlap based calculation")
    @EnabledByDefault
    OVERLAP_BASED_CALCULATION_FEATURE
}
