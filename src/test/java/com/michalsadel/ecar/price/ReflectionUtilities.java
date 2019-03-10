package com.michalsadel.ecar.price;

import org.slf4j.*;
import org.springframework.data.util.*;

import javax.persistence.*;
import java.lang.reflect.*;

class ReflectionUtilities {
    private static final Logger log = LoggerFactory.getLogger(ReflectionUtilities.class);

    private ReflectionUtilities() {
    }

    static <T> void setId(Entity<T> entity, T id) {
        org.springframework.util.ReflectionUtils.FieldFilter fieldFilter = new ReflectionUtils.AnnotationFieldFilter(GeneratedValue.class);
        Field field = ReflectionUtils.findField(entity.getClass(), fieldFilter);
        if (field != null) {
            field.setAccessible(true);
            try {
                field.set(entity, id);
            } catch (IllegalAccessException e) {
                log.error(e.getMessage());
            }
        }
    }
}
