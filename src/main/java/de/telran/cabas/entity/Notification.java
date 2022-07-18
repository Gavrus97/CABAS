package de.telran.cabas.entity;

import de.telran.cabas.converter.ListConverter;
import de.telran.cabas.entity.types.SeverityType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "notification")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Column(name = "area_id")
    private Long areaId;

    @Convert(converter = SeverityType.class)
    @Column(name = "severity_status")
    private SeverityType severityType;

    @Column(name = "people_notified")
    @Convert(converter = ListConverter.class)
    private List<Long> people;

    @CreatedDate
    @Column(name = "created_on")
    private LocalDateTime createdOn;
 }
