package com.vaibhavbiotech.models;

import javax.persistence.*;

@Entity
public class Facility extends AuditModel {
    @Id
    @SequenceGenerator(
            name = "facility_sequence",
            sequenceName = "facility_sequence",
            initialValue = 1,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "facility_sequence"
    )
    private long id;
    @Column(name = "facility_name")
    private String facilityName;
    @Column(name = "description")
    private String description;
    @Column(name = "image_link")
    private String imageLink;

    public Facility() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
