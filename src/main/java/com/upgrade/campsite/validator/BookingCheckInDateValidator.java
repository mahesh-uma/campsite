package com.upgrade.campsite.validator;

import com.upgrade.campsite.dto.BookingDetailDTO;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {BookingCheckInValidatorConstraint.class})
public @interface BookingCheckInDateValidator {
    String message() default "Booking start date must be from 1 day to up to 1 month ahead";

}

class BookingCheckInValidatorConstraint implements ConstraintValidator<BookingCheckInDateValidator, BookingDetailDTO> {

    @Override
    public boolean isValid(BookingDetailDTO booking, ConstraintValidatorContext constraintValidatorContext) {
        return LocalDate.now().isBefore(booking.getCheckIn())
                && booking.getCheckIn().isBefore(LocalDate.now().plusMonths(1).plusDays(1));
    }
}