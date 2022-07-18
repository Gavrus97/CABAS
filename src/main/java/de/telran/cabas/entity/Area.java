package de.telran.cabas.entity;

import de.telran.cabas.entity.types.SeverityType;
import de.telran.cabas.entity.types.SeverityTypeConverter;
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

    @Column(name = "area_code")
    private String areaCode;

    @Column(name = "severity_type")
    @Convert(converter = SeverityTypeConverter.class)
    private SeverityType severityType;

}
