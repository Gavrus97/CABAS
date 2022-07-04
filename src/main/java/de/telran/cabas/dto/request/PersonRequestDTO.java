package de.telran.cabas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequestDTO {

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String email;

    private String phoneNumber;

    private Long guardianId;


}
