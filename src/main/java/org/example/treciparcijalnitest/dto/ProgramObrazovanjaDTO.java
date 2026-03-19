package org.example.treciparcijalnitest.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ProgramObrazovanjaDTO {
    @NotBlank(message = "Obavezno je upisati naziv programa!")
    private String name;

    @NotNull(message = "Vrijednost CSVET bodova ne smije biti null!")
    @Min(value = 1, message = "Vrijednost CSVET bodova mora biti veća od 0!")
    @Positive(message = "Vrijednost CSVET bodova mora biti pozitivna!")
    @Pattern(regexp = "^-?\\d+$", message = "Vrijednost CSVET bodova mora biti cijeli broj!")
    private int CSVET;

    public ProgramObrazovanjaDTO() {
    }

    public ProgramObrazovanjaDTO(String name, int CSVET) {
        this.name = name;
        this.CSVET = CSVET;
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
