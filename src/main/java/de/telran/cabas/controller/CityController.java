package de.telran.cabas.controller;

import de.telran.cabas.dto.request.CityRequestDTO;
import de.telran.cabas.dto.response.CityResponseDTO;
import de.telran.cabas.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CityController {

    @Autowired
    private CityService service;

    @PostMapping("/cities")
    public CityResponseDTO create(@RequestBody CityRequestDTO cityDTO){
        return service.create(cityDTO);
    }

    @GetMapping("/cities/all")
    public List<CityResponseDTO> getAll(){
        return service.getAll();
    }

    @GetMapping("/cities/{id}")
    public CityResponseDTO getById(@PathVariable("id") Long id){
        return service.getById(id);
    }

    @GetMapping("/cities")
    public CityResponseDTO getByName(@RequestParam("cityName") String cityName){
        return service.getByName(cityName);
    }
}
