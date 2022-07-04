package de.telran.cabas.repository;


import de.telran.cabas.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area, Long> {

    Boolean existsByAreaName(String areaName);

    Area findByAreaName(String areaName);
}
