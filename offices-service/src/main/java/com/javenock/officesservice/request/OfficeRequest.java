package com.javenock.officesservice.request;

import lombok.Data;

@Data
public class OfficeRequest {
    private String city;
    private String phone;
    private String address;
    private String country;
}
