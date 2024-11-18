package com.plotting.server.alarm.application;

import com.plotting.server.alarm.domain.Alarm;
import com.plotting.server.alarm.repository.AlarmRepository;
import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.domain.PloggingUser;
import com.plotting.server.plogging.repository.PloggingUserRepository;
import com.plotting.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.plotting.server.plogging.util.PloggingConstants.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final PloggingUserRepository ploggingUserRepository;

    public void saveDirectAlarm(User participant, User creator, Plogging plogging) {
        alarmRepository.save(Alarm.of(participant, plogging.getTitle() + DIRECT_PARTICIPANT.getMessage()));
        alarmRepository.save(Alarm.of(creator, participant.getNickname() + NAME.getMessage() + plogging.getTitle() + DIRECT_PARTICIPANT.getMessage()));
    }

    public void saveAssignAlarm(User participant, User creator, Plogging plogging) {
        alarmRepository.save(Alarm.of(participant, plogging.getTitle() + ASSIGN_COMPLETE_PARTICIPANT.getMessage()));
        alarmRepository.save(Alarm.of(creator, participant.getNickname() + NAME.getMessage() + plogging.getTitle() + ASSIGN_PARTICIPANT.getMessage()));
    }

    public void saveCompleteAlarm(User participant, User creator, Plogging plogging) {
        alarmRepository.save(Alarm.of(participant, plogging.getTitle() + DIRECT_COMPLETE_PARTICIPANT.getMessage()));
        alarmRepository.save(Alarm.of(creator, participant.getNickname() + NAME.getMessage() + plogging.getTitle() + DIRECT_PARTICIPANT.getMessage()));
    }

    public void saveUpdatePloggingAlarm(Plogging plogging) {
        ploggingUserRepository.findActivePloggingUsersByPloggingId(plogging.getId()).stream()
                .map(PloggingUser::getUser)
                .forEach(user -> alarmRepository.save(
                        Alarm.of(user, plogging.getUser().getNickname() + NAME.getMessage() + plogging.getTitle() + PLOGGING_UPDATE.getMessage())
                ));
    }
}
