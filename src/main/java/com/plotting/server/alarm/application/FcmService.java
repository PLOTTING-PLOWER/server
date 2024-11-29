package com.plotting.server.alarm.application;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.plotting.server.alarm.domain.Alarm;
import com.plotting.server.alarm.exception.FcmFailedException;
import com.plotting.server.user.application.UserService;
import com.plotting.server.user.domain.User;
import com.plotting.server.user.domain.UserType.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import static com.plotting.server.alarm.exception.errorcode.AlarmErrorCode.FCM_FAILED;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {
    private final UserService userService;

    public void sendNotificationForUser(Long userId, Alarm alarm){
        User user = userService.getUser(userId);

        // 알람 허용 안하거나 탈퇴 회원인 경우
        if(!user.getIsAlarmAllowed() || user.getRole()== Role.WITHDRAWN) {
            log.info("User {} is not allowed to send notification", userId);
            return;
        }

        String fcmToken = user.getFcmToken();
        if(fcmToken == null) {
            log.warn("User with ID {} does not have a valid FCM token.", userId);
            return;
        }

        try {
            Message message= Message.builder()
                    .setToken(fcmToken)
                    .setNotification(Notification.builder()
                            .setTitle("Plotting")       //알아서 세팅..!
                            .setBody(alarm.getContent())
                            .build())
                    .build();
            String response = FirebaseMessaging.getInstance().send(message);

            log.info("Notification sent message: "+ response);
        } catch (Exception e) {
            log.error("Failed to send notification to token: {}", fcmToken, e);
            throw new FcmFailedException(FCM_FAILED);
        }

    }
}
