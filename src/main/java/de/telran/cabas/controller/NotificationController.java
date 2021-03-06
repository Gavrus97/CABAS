package de.telran.cabas.controller;

import de.telran.cabas.dto.response.NotificationResponseDTO;
import de.telran.cabas.entity.types.SeverityType;
import de.telran.cabas.service.PeopleToNotifyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class NotificationController {

    private final PeopleToNotifyService peopleToNotifyService;

    @PostMapping("/notifications/notify")
    public List<NotificationResponseDTO> notifyPeople(@RequestParam("area_code") String areaCode,
                                                      @RequestParam("severity") SeverityType severityType) {
        return peopleToNotifyService.getPeopleToNotify(areaCode, severityType);
    }
}
