package com.javenock.officesservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "offices")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Offices {
    @Transient
    public static final String SEQUENCE_NAME = "office_sequence";

    @Id
    private int officeCode;
    private String city;
    private String phone;
    private String address;
    private String country;
}
