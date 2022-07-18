package de.telran.cabas.dto.response;

import de.telran.cabas.entity.types.SeverityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class AreaWithCitiesResponseDTO {

    private Long areaId;
    private String areaName;
    private String areaCode;
    private List<Long> cityIds;
    private SeverityType severityType;
}
