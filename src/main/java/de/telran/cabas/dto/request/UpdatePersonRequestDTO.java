package de.telran.cabas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePersonRequestDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;
}
