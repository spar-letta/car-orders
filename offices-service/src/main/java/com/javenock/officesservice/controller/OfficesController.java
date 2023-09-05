package com.javenock.officesservice.controller;

import com.javenock.officesservice.model.Offices;
import com.javenock.officesservice.request.OfficeRequest;
import com.javenock.officesservice.service.OfficesService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/offices")
@RestController
public class OfficesController {

    private OfficesService officesService;

    public OfficesController(OfficesService officesService) {
        this.officesService = officesService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Offices createOffice(@RequestBody OfficeRequest officeRequest){
        return officesService.createOffice(officeRequest);
    }

    @GetMapping("/office-code/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Offices getOfficeById(@PathVariable int id){
        return officesService.getOfficeById(id);
    }

    @GetMapping("/city/{city}")
    @ResponseStatus(HttpStatus.OK)
    public Offices getOfficeByCity(@PathVariable String city){
        return officesService.getOfficeByCity(city);
    }

    @GetMapping("/all-offices")
    @ResponseStatus(HttpStatus.OK)
    public List<Offices> getAllOffices(){
        return officesService.getAllOffices();
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateOfficeRecord(@RequestBody OfficeRequest officeRequest, @PathVariable int id){
        return officesService.updateOfficeRecord(officeRequest,id);
    }

    @DeleteMapping("/delete-office/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteOffice(@PathVariable int id){
        return officesService.deleteOfficeById(id);
    }


}
