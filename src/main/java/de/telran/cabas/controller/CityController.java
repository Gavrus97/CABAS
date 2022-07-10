package de.telran.cabas.controller;

import de.telran.cabas.dto.request.CityRequestDTO;
import de.telran.cabas.dto.response.CityResponseDTO;
import de.telran.cabas.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Validated
public class CityController {

    private final CityService service;

    @PostMapping("/cities")
    public CityResponseDTO create(@RequestBody @Valid CityRequestDTO cityDTO) {
        return service.create(cityDTO);
    }

    @GetMapping("/cities/all")
    public List<CityResponseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/cities/{id}")
    public CityResponseDTO getById(@PathVariable("id")
                                   @Positive(message = "Id in URL must be positive") Long id) {
        return service.getById(id);
    }

    @GetMapping("/cities")
    public CityResponseDTO getByName(@RequestParam("cityName")
                                     @NotBlank(message = "City name cannot be blank") String cityName) {
        return service.getByName(cityName);
    }
}
