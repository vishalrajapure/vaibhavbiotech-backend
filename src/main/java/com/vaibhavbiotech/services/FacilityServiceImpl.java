package com.vaibhavbiotech.services;

import com.vaibhavbiotech.models.Facility;
import com.vaibhavbiotech.repository.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityServiceImpl implements FacilityService {

    @Autowired
    private FacilityRepository facilityRepository;

    @Override
    public Facility addFacility(Facility facility) {
        Facility storedFacility = facilityRepository.save(facility);
        return storedFacility;
    }

    @Override
    public List<Facility> getAllFacilities() {
        return facilityRepository.findAll();
    }

    @Override
    public Facility updateFacility(Facility facilityRequest) {
        Facility facilityFromDb = facilityRepository.getById(facilityRequest.getId());
        facilityFromDb.setFacilityName(facilityRequest.getFacilityName());
        facilityFromDb.setDescription(facilityRequest.getDescription());
        facilityFromDb.setImageLink(facilityRequest.getImageLink());
        return facilityRepository.save(facilityFromDb);
    }

    @Override
    public String deleteFacility(long id) {
        facilityRepository.deleteById(id);
        return "facility deleted";
    }
}
