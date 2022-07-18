package de.telran.cabas.dto.response;

import de.telran.cabas.entity.types.SeverityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CityResponseDTO {

    private Long cityId;
    private String cityName;
    private Long areaId;
    private SeverityType severityType;
}
