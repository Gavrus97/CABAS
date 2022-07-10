package de.telran.cabas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeGuardianRequestDTO {

    @Positive(message = "fromGuardian must be positive number")
    @NotNull(message = "fromGuardian cannot be null")
    private Long fromGuardian;

    @NotNull(message = "toGuardian cannot be null")
    @Positive(message = "toGuardian must be positive number")
    private Long toGuardian;

    @NotNull(message = "List of children to move may not be null")
    @NotEmpty(message = "List of children to move must contain something")
    private List<@Positive(message = "each id must be positive") Long> childrenIds;
}
