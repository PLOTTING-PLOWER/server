package com.plotting.server.plogging.fixture;

import com.plotting.server.plogging.domain.PloggingUser;
import org.springframework.test.util.ReflectionTestUtils;

import static com.plotting.server.plogging.fixture.PloggingFixture.PLOGGING;
import static com.plotting.server.user.fixture.UserFixture.USER;

public class PloggingUserFixture {
    public static final PloggingUser PLOGGING_USER = PloggingUser.builder()
            .user(USER)
            .plogging(PLOGGING)
            .isAssigned(true)
            .build();

    public static final PloggingUser PLOGGING_USER2 = PloggingUser.builder()
            .user(USER)
            .plogging(PLOGGING)
            .isAssigned(false)
            .build();

    static {
        ReflectionTestUtils.setField(PLOGGING_USER, "id", 1L);
        ReflectionTestUtils.setField(PLOGGING_USER2, "id", 2L);
    }
}