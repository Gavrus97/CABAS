package de.telran.cabas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Your own convention mismatch
// other classes names' structure: Name + Request/Response + DTO
// this class...
// would suggest names:
// - ApiErrorResponseDTO
// - RestErrorResponseDTO
// - CabasErrorResponseDTO
// - CabasApiErrorResponseDTO
public class ResponseExceptionDTO {

    private HttpStatus status;
    private String message;
}
