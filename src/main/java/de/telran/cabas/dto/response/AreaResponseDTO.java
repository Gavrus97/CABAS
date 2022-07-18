package de.telran.cabas.dto.response;

import de.telran.cabas.entity.types.SeverityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AreaResponseDTO {

    private String areaName;
    private Long id;
    private String areaCode;
    private SeverityType severityType;
}
