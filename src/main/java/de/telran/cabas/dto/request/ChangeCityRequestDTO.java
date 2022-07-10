package de.telran.cabas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeCityRequestDTO {

    @NotNull(message = "person id cannot be null")
    @Positive(message = "person id must be positive number")
    private Long personId;

    @Positive(message = "fromCity id must be positive number")
    private Long fromCityId;

    @NotNull(message = "toCity id cannot be null")
    @Positive(message = "toCity id must be positive number")
    private Long toCityId;

}
