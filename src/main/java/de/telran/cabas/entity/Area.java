package de.telran.cabas.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "area")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "area_id")
    private Long id;

    @Column(name = "area_name")
    private String areaName;

}
