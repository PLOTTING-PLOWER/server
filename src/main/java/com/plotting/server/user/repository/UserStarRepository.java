package com.plotting.server.user.repository;

import com.plotting.server.plogging.domain.PloggingUser;
import com.plotting.server.user.domain.UserStar;
import com.plotting.server.user.dto.response.MyUserStarResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStarRepository extends JpaRepository<UserStar, Long> {

    @Query("SELECT us FROM UserStar us JOIN FETCH us.user WHERE us.user.id = :userId")
    List<UserStar> findAllByUserIdWithUser(Long userId);

}
