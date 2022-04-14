package com.bombanya.javaschool_railway.services.schedule;

import com.bombanya.javaschool_railway.entities.routes.Run;
import com.bombanya.javaschool_railway.entities.routes.RunUpdate;
import com.bombanya.javaschool_railway.utils.JacksonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleNotifier {

    private final ScheduleUtils scheduleUtils;
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void notifyJmsClients(Run run, int stationId, Optional<RunUpdate> update) {
        String msg = objectMapper
                .writerWithView(JacksonView.UserInfo.class)
                .writeValueAsString(scheduleUtils.generateScheduleInfo(run, stationId, update));
        jmsTemplate.convertAndSend("java:/testQueue", msg, message -> {
            message.setIntProperty("stationId", stationId);
            return message;
        });
    }


}
