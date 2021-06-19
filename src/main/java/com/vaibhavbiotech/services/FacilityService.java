package com.vaibhavbiotech.services;

import com.vaibhavbiotech.models.Facility;

import java.util.List;

public interface FacilityService {
    List<Facility> getAllFacilities();

    Facility addFacility(Facility facility);

    Facility updateFacility(Facility facilityRequest);

    String deleteFacility(long id);

}
