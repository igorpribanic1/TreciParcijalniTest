package org.example.treciparcijalnitest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UpisDTO {
    @NotNull(message = "Id Programa Obrazovanja ne smije biti null!")
    @Min(value = 1, message = "Id Programa Obrazovanja mora biti veći od 0!")
    private int programId;

    @NotNull(message = "Id Polaznika ne smije biti null!")
    @Min(value = 1, message = "Id Polaznika mora biti veći od 0!")
    private int studentId;

    public UpisDTO() {
    }

    public UpisDTO(int programId, int studentId) {
        this.programId = programId;
        this.studentId = studentId;
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
