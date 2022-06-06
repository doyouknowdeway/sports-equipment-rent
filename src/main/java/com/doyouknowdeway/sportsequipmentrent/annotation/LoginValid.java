package com.doyouknowdeway.sportsequipmentrent.annotation;

import com.doyouknowdeway.sportsequipmentrent.validator.LoginValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LoginValidator.class)
public @interface LoginValid {

    String message() default "Such login already exists.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
