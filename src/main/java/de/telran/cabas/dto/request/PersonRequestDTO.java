package de.telran.cabas.dto.request;

import de.telran.cabas.entity.types.GenderType;
import de.telran.cabas.entity.types.LanguageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequestDTO {

    private GenderType gender;

    @NotBlank(message = "firstName can't be blank")
    @Size(min = 2, max = 50, message = "fistName length must be between 2 and 50 chars")
    private String firstName;

    @NotBlank(message = "lastName can't be blank")
    @Size(min = 2, max = 50, message = "lastName length must be between 2 and 50 chars")
    private String lastName;

    @NotNull(message = "dateOfBirth cannot be null")
    @Past(message = "dateOfBirth must be at the past from now")
    private LocalDate dateOfBirth;

    private LanguageType language;

    @NotBlank(message = "email cannot be blank")
    @Email(message = "email form is incorrect")
    @Size(min = 10, max = 50, message = "email length must be between 10 and 50 chars")
    private String email;

    @NotBlank(message = "phoneNumber cannot be blank")
    @Pattern(regexp = "\\+\\d{2}( )\\d{3}( )\\d{8}", message = "phoneNumber must be like (+49 111 12345678)")
    private String phoneNumber;

    @Positive(message = "guardianId can be null or positive number")
    private Long guardianId;

}
