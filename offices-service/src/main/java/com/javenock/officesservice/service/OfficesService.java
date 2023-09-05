package com.javenock.officesservice.service;

import com.javenock.officesservice.model.DbSequence;
import com.javenock.officesservice.model.Offices;
import com.javenock.officesservice.repository.OfficesRepository;
import com.javenock.officesservice.request.OfficeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
public class OfficesService {

    @Autowired
    private OfficesRepository officesRepository;

    @Autowired
    private MongoOperations mongoOperations;

    public Offices createOffice(OfficeRequest officeRequest) {
        Offices offices = Offices.builder()
                .officeCode(getSequenceNumber(Offices.SEQUENCE_NAME))
                .address(officeRequest.getAddress())
                .phone(officeRequest.getPhone())
                .country(officeRequest.getCountry())
                .city(officeRequest.getCity())
                .build();
        return officesRepository.save(offices);
    }

    public Offices getOfficeById(int id) {
        return officesRepository.findById((long) id).get();
    }

    public Offices getOfficeByCity(String city) {
        return officesRepository.findByCity(city).get();
    }

    public String updateOfficeRecord(OfficeRequest officeRequest, int id) {
        Offices office_pre = officesRepository.findById((long) id).get();
        office_pre.setAddress(officeRequest.getAddress());
        office_pre.setCity(officeRequest.getCity());
        office_pre.setCountry(officeRequest.getCountry());
        office_pre.setPhone(officeRequest.getPhone());
        officesRepository.save(office_pre);
        return "successfully saved";
    }

    //============================
    public int getSequenceNumber(String sequenceName){
        Query query = new Query(Criteria.where("id").is(sequenceName));
        Update update = new Update().inc("seq", 1);
        DbSequence counter = mongoOperations.findAndModify(query, update, options().returnNew(true).upsert(true), DbSequence.class);
        return !Objects.isNull(counter)? counter.getSeq() : 1;
    }

    public String deleteOfficeById(int id) {
        officesRepository.deleteById((long) id);
        return "deleted successfully";
    }

    public List<Offices> getAllOffices() {
        return officesRepository.findAll();
    }
}
