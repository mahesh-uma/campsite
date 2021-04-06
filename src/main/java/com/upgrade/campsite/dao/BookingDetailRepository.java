package com.upgrade.campsite.dao;

import com.upgrade.campsite.model.BookingDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingDetailRepository extends CrudRepository<BookingDetail, Integer> {

    Optional<BookingDetail> findByUuid(UUID uuid);
}
