package com.doyouknowdeway.sportsequipmentrent.annotation;

import com.doyouknowdeway.sportsequipmentrent.validator.DatetimeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DatetimeValidator.class)
public @interface DatetimeValid {

    String message() default "Datetime invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
