package com.upgrade.campsite.controller;

import com.upgrade.campsite.dto.BookingDetailDTO;
import com.upgrade.campsite.dto.mapper.BookingDetailMapper;
import com.upgrade.campsite.model.BookingDetail;
import com.upgrade.campsite.service.CampsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController("/campsite")
public class CampsiteController {

    @Autowired
    private CampsiteService campsiteService;

    @GetMapping("/availability")
    public List<LocalDate> availability(@RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now()}") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                        @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now().plusDays(30)}") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        campsiteService.availability(startDate, endDate);
        return campsiteService.availability(startDate, endDate);
    }

    @PostMapping("/reserve")
    public ResponseEntity<BookingDetail> reserve(@RequestBody @Valid BookingDetailDTO bookingDetailDTO) {
        BookingDetail bd = BookingDetailMapper.INSTANCE.toBooking(bookingDetailDTO);
        return new ResponseEntity<>(campsiteService.reserve(bd)
                , HttpStatus.CREATED);
    }


    @PutMapping("/update/{uuid}")
    public ResponseEntity<BookingDetail> updateCampsite(@PathVariable @NotNull UUID uuid, @RequestBody @Valid BookingDetailDTO bookingDetailDTO) {
        BookingDetail bd = BookingDetailMapper.INSTANCE.toBooking(bookingDetailDTO);
        return new ResponseEntity<>(campsiteService.update(uuid,bd), HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> cancel(@PathVariable @NotNull UUID uuid) {
        boolean cancelled = campsiteService.cancel(uuid);
        if (cancelled) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
