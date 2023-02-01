package de.telran.cabas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePersonRequestDTO {

    @NotBlank(message = "firstName can't be blank")
    @Size(min = 2, max = 50, message = "fistName length must be between 2 and 50 chars")
    private String firstName;

    @NotBlank(message = "lastName can't be blank")
    @Size(min = 2, max = 50, message = "lastName length must be between 2 and 50 chars")
    private String lastName;

    @NotBlank(message = "email cannot be blank")
    @Email(message = "email form is incorrect")
    @Size(min = 10, max = 50, message = "email length must be between 10 and 50 chars")
    private String email;

    @NotBlank(message = "phoneNumber cannot be blank")
    @Pattern(regexp = "\\+\\d{2}( )\\d{3}( )\\d{8}", message = "phoneNumber must be like (+49 111 12345678)")
    private String phoneNumber;
}
