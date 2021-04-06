package com.upgrade.campsite.service;

import com.upgrade.campsite.configuration.Config;
import com.upgrade.campsite.dao.BookingDetailRepository;
import com.upgrade.campsite.exception.BookingDetailNotFoundException;
import com.upgrade.campsite.exception.ReservedDateNotAvailableException;
import com.upgrade.campsite.model.BookingDetail;
import com.upgrade.campsite.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CampsiteService {

   @Autowired
    BookingDetailRepository bookingDetailRepository;

   @Transactional
    public BookingDetail reserve(BookingDetail bd) {
        Optional<List<LocalDate>> optionalLocalDates= Config.getReservedDates();
        if(!optionalLocalDates.isEmpty()) {
            if(optionalLocalDates.get().size() > 0) {
                checkAvailability(bd.getBookingDates(), optionalLocalDates.get());
            }
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization(){
                public void afterCommit(){
                    Config.addReservedDates(bd.getBookingDates());
                }
            });
        }
        bd.setStatus(Status.ACTIVE);
       return bookingDetailRepository.save(bd);
    }

    private void checkAvailability(List<LocalDate> givenListOfDates, List<LocalDate> rdList) {
        rdList.forEach(rd ->
        {
            if (givenListOfDates.contains(rd)) {
                throw new ReservedDateNotAvailableException("reserved date: " + rd + "not available");
            }
        });
    }

    public List<LocalDate> availability(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> listOfDatesFromGivenDates = startDate.datesUntil(endDate)
                .collect(Collectors.toList());
        Optional<List<LocalDate>> optionalReservedDates =  Config.getReservedDates();
        if(optionalReservedDates.isEmpty()) {
            return listOfDatesFromGivenDates;
        }
        return listOfDatesFromGivenDates.stream().filter(optionalReservedDates.get()::contains).collect(Collectors.toList());
    }

    @Transactional
    public BookingDetail update( UUID uuid, BookingDetail bd) {
        Optional<BookingDetail> bookingDetailOptional = bookingDetailRepository.findByUuid(uuid);
        Optional<List<LocalDate>> optionalLocalDates= Config.getReservedDates();
        Config.removeReservedDates(bd.getBookingDates());

        if(bookingDetailOptional.isEmpty()) {
            throw new BookingDetailNotFoundException(String.format("Booking was not found for uuid=%s", bd.getUuid()));
        }
        BookingDetail bookingDetail = bookingDetailOptional.get();
        if(!bd.getBookingDates().equals(bookingDetail.getBookingDates())) {
            checkAvailability(bd.getBookingDates(), optionalLocalDates.get());
        }
        bookingDetail.setCheckIn(bd.getCheckIn());
        bookingDetail.setCheckOut(bd.getCheckOut());
        bookingDetail.setEmail(bd.getEmail());
        bookingDetail.setFullName(bd.getFullName());
        Config.addReservedDates(bd.getBookingDates());
        return bookingDetailRepository.save(bd);
    }

    @Transactional
    public boolean cancel(UUID uuid) {
        Optional<BookingDetail> bookingDetail = bookingDetailRepository.findByUuid(uuid);
        if(bookingDetail.isEmpty()) {
            throw new BookingDetailNotFoundException(String.format("Booking was not found for uuid=%s", uuid));
        }
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization(){
            public void afterCommit(){
                Config.removeReservedDates(bookingDetail.get().getBookingDates());
            }
        });
        bookingDetail.get().setStatus(Status.CANCELED);
        bookingDetailRepository.save(bookingDetail.get());
        return true;
    }
}
