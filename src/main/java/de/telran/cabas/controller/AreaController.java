package de.telran.cabas.controller;

import de.telran.cabas.dto.request.AreaRequestDTO;
import de.telran.cabas.dto.response.AreaResponseDTO;
import de.telran.cabas.dto.response.AreaWithCitiesResponseDTO;
import de.telran.cabas.service.AreaService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AreaController {


    private final AreaService service;

    @PostMapping("/areas")
    public AreaResponseDTO create(@RequestBody @Valid AreaRequestDTO areaDto) {
        return service.create(areaDto);
    }

    @GetMapping("/areas/all")
    public List<AreaWithCitiesResponseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/areas")
    public AreaWithCitiesResponseDTO getByName(@RequestParam(name = "areaName")
                                               @NotBlank(message = "Area name cannot be blank or null") String areaName) {
        return service.getByName(areaName);
    }
}
