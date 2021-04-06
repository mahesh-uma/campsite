package com.upgrade.campsite.validator;

import com.upgrade.campsite.dto.BookingDetailDTO;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.Period;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {BookingMaximumStayValidatorConstraint.class})
public @interface BookingMaximumStayValidator {
}

class BookingMaximumStayValidatorConstraint implements ConstraintValidator<BookingMaximumStayValidator, BookingDetailDTO> {
    @Override
    public boolean isValid(BookingDetailDTO bookingDetailDTO, ConstraintValidatorContext constraintValidatorContext) {
        return Period.between(bookingDetailDTO.getCheckIn(), bookingDetailDTO.getCheckOut()).getDays() <= 3;
    }
}