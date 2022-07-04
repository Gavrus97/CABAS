package de.telran.cabas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuardianResponseDTO {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;




}
