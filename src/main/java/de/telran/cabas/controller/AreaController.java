package de.telran.cabas.controller;

import de.telran.cabas.dto.request.AreaRequestDTO;
import de.telran.cabas.dto.response.AreaResponseDTO;
import de.telran.cabas.dto.response.AreaWithCitiesResponseDTO;
import de.telran.cabas.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AreaController {

    @Autowired
    private AreaService service;

    @PostMapping("/areas")
    public AreaResponseDTO create(@RequestBody AreaRequestDTO areaDto) {
        return service.create(areaDto);
    }

    @GetMapping("/areas/all")
    public List<AreaWithCitiesResponseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/areas")
    public AreaWithCitiesResponseDTO getByName(@RequestParam(name = "areaName") String areaName) {
        return service.getByName(areaName);
    }
}
