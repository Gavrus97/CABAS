package de.telran.cabas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityRequestDTO {

    @NotNull(message = "cityName cannot be null")
    @NotBlank(message = "cityName cannot be blank")
    @Size(min = 3, max = 50, message = "cityName field must contain 3-50 chars")
    private String cityName;

    @NotNull(message = "areaId cannot be null")
    @Positive(message = "areaId can be only positive number")
    private Long areaId;
}
