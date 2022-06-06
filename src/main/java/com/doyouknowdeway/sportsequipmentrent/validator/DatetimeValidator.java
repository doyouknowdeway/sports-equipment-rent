package com.doyouknowdeway.sportsequipmentrent.validator;

import com.doyouknowdeway.sportsequipmentrent.annotation.DatetimeValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class DatetimeValidator implements ConstraintValidator<DatetimeValid, String> {

    @Override
    public boolean isValid(final String instant, final ConstraintValidatorContext context) {
        final List<String> messages = new ArrayList<>();
        if (instant == null) {
            buildConstraintValidatorContext(context, "Datetime is null.");
            return false;
        }

        if (instant.isBlank()) {
            messages.add("Datetime is blank.");
        }

        try {
            Instant.parse(instant);
        } catch (final DateTimeParseException dateTimeParseException) {
            messages.add("Datetime is invalid.");
        }

        if (messages.size() == 0) {
            return true;
        }

        final String messageTemplate = String.join(", ", messages);
        buildConstraintValidatorContext(context, messageTemplate);
        return false;
    }

    private void buildConstraintValidatorContext(final ConstraintValidatorContext context, final String messageTemplate) {
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
    }

}
