package com.vaibhavbiotech.controllers;

import com.vaibhavbiotech.models.Facility;
import com.vaibhavbiotech.services.FacilityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Services")
public class ServicesController {

    @Autowired
    private FacilityServiceImpl facilityServiceImpl;

    @PostMapping("/AddFacility")
    public Facility addProduct(@RequestBody Facility facility) {
        Facility storedfacility = facilityServiceImpl.addFacility(facility);
        return storedfacility;
    }

    @GetMapping("/GetFacilities")
    public List<Facility> getAllFacilities() {
        return facilityServiceImpl.getAllFacilities();
    }

    @PutMapping("/UpdateFacility")
    public Facility updateFacility(@RequestBody Facility facility) {
        return facilityServiceImpl.updateFacility(facility);
    }

    @DeleteMapping("/DeleteFacility/{id}")
    public String deleteFacility(@PathVariable("id") long id) {
        return facilityServiceImpl.deleteFacility(id);
    }

}
