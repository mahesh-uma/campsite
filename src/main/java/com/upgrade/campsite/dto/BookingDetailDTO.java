package com.upgrade.campsite.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upgrade.campsite.validator.BookingCheckInDateValidator;
import com.upgrade.campsite.validator.BookingMaximumStayValidator;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
@BookingCheckInDateValidator
@BookingMaximumStayValidator
@JsonIgnoreProperties(ignoreUnknown = true)
@Generated
@AllArgsConstructor @NoArgsConstructor
public class BookingDetailDTO {

    @NotEmpty
    @Size(max = 50)
    private String email;

    @NotEmpty
    @Size(max = 50)
    private String fullName;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Future(message = "Booking check in must be a future date")
    private LocalDate checkIn;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Future(message = "Booking check in must be a future date")
    private LocalDate checkOut;
}
