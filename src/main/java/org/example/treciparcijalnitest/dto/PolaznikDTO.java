package org.example.treciparcijalnitest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PolaznikDTO {
    @NotBlank(message = "Obavezno je upisati ime polaznika!")
    private String firstName;

    @NotBlank(message = "Obavezno je upisati prezime polaznika!")
    private String lastName;

    public PolaznikDTO() {
    }

    public PolaznikDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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
