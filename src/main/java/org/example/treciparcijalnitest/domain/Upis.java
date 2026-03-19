package org.example.treciparcijalnitest.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "Upis")
public class Upis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UpisID")
    private int id;

    @Column(name = "IDProgramObrazovanja")
    private int programId;

    @Column(name = "IDPolaznik")
    private int studentId;

    public Upis() {
    }

    public Upis(int id, int programId, int studentId) {
        this.id = id;
        this.programId = programId;
        this.studentId = studentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
