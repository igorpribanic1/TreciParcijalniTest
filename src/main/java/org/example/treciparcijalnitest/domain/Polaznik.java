package org.example.treciparcijalnitest.domain;


import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "Polaznik")
public class Polaznik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PolaznikID")
    private int id;

    @Column(name = "Ime")
    private String firstName;

    @Column(name = "Prezime")
    private String lastName;

    public int getId() {
        return id;
    }

    public Polaznik() {
    }

    public Polaznik(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
