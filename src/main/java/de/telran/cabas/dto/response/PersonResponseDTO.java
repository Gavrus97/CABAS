package de.telran.cabas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonResponseDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private GuardianResponseDTO guardian;

    private List<ChildResponseDTO> children;

    private Long cityId;

    private Long areaId;

}
