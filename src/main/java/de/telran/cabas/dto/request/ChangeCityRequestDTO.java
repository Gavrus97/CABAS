package de.telran.cabas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeCityRequestDTO {

    private Long personId;
    private Long fromCityId;
    private Long toCityId;

}
