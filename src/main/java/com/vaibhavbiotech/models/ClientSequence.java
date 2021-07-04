package com.vaibhavbiotech.models;

import javax.persistence.*;

@Entity
@Table(name = "client_sequence")
public class ClientSequence {
    @Id
    @Column(name = "next_val")
    private long next_val;

    public ClientSequence(){

    }

    public long getNext_val() {
        return next_val;
    }

    public void setNext_val(long next_val) {
        this.next_val = next_val;
    }
}
