package com.plotting.server.alarm.application;

import com.plotting.server.alarm.domain.Alarm;
import com.plotting.server.alarm.repository.AlarmRepository;
import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.plotting.server.plogging.util.PloggingConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService {

    private final AlarmRepository alarmRepository;

    @Transactional
    public void saveDirectAlarm(User participant, User creator, Plogging plogging) {
        alarmRepository.save(Alarm.of(participant, plogging.getTitle() + DIRECT_PARTICIPANT.getMessage()));
        alarmRepository.save(Alarm.of(creator, participant.getNickname() + NAME.getMessage() + plogging.getTitle() + DIRECT_PARTICIPANT.getMessage()));
    }

    @Transactional
    public void saveAssignAlarm(User participant, User creator, Plogging plogging) {
        alarmRepository.save(Alarm.of(participant, plogging.getTitle() + ASSIGN_COMPLETE_PARTICIPANT.getMessage()));
        alarmRepository.save(Alarm.of(creator, participant.getNickname() + NAME.getMessage() + plogging.getTitle() + ASSIGN_PARTICIPANT.getMessage()));
    }
}
