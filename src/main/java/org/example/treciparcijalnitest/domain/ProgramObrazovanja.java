package org.example.treciparcijalnitest.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "ProgramObrazovanja")
public class ProgramObrazovanja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProgramObrazovanjaID")
    private int id;

    @Column(name = "Naziv")
    private String name;

    @Column(name = "CSVET")
    private int CSVET;

    public ProgramObrazovanja() {
    }

    public ProgramObrazovanja(int id, String name, int CSVET) {
        this.id = id;
        this.name = name;
        this.CSVET = CSVET;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCSVET() {
        return CSVET;
    }

    public void setCSVET(int CSVET) {
        this.CSVET = CSVET;
    }
}
