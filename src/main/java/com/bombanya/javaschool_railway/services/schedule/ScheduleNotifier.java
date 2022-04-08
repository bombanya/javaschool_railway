package com.bombanya.javaschool_railway.services.schedule;

import com.bombanya.javaschool_railway.entities.routes.Run;
import com.bombanya.javaschool_railway.entities.routes.RunUpdate;
import com.bombanya.javaschool_railway.utils.JacksonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleNotifier {

    private final ScheduleUtils scheduleUtils;
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    @SneakyThrows
    public void notifyJmsClients(Run run, int stationId, RunUpdate update) {
        String msg = objectMapper
                .writerWithView(JacksonView.UserInfo.class)
                .writeValueAsString(scheduleUtils.generateScheduleInfo(run, stationId, update));
        jmsTemplate.convertAndSend("java:/testQueue", msg, message -> {
            message.setIntProperty("stationId", stationId);
            return message;
        });
    }


}
