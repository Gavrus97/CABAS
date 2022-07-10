package de.telran.cabas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AreaRequestDTO {
    // offtop
    // this is why Java is being hated: a class for a single property :)
    private String areaName;
}
