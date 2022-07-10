package de.telran.cabas.repository;


import de.telran.cabas.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AreaRepository extends JpaRepository<Area, Long> {

    Boolean existsByAreaName(String areaName);

    Optional<Area> findByAreaName(String areaName);
}
