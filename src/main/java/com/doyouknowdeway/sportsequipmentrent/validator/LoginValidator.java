package com.doyouknowdeway.sportsequipmentrent.validator;

import com.doyouknowdeway.sportsequipmentrent.annotation.LoginValid;
import com.doyouknowdeway.sportsequipmentrent.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class LoginValidator implements ConstraintValidator<LoginValid, String> {

    private final ProfileRepository profileRepository;

    @Override
    public boolean isValid(final String login, final ConstraintValidatorContext context) {
        final List<String> messages = new ArrayList<>();
        if (profileRepository.existsByLogin(login)) {
            messages.add("Login exists.");
        }
        final String messageTemplate = String.join(", ", messages);
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return messages.size() == 0;
    }

}
