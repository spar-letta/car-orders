package com.javenock.officesservice.repository;

import com.javenock.officesservice.model.Offices;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OfficesRepository extends MongoRepository<Offices, Long> {
    Optional<Offices> findByCity(String city);
}
