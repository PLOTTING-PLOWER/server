package com.plotting.server.user.repository;

import com.plotting.server.user.domain.UserStar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserStarRepository extends JpaRepository<UserStar, Long> {

    @Query("SELECT us FROM UserStar us JOIN FETCH us.user WHERE us.user.id = :userId AND us.user.role != 'WITHDRAWN'")
    List<UserStar> findAllByUserIdWithUser(Long userId);

    Optional<UserStar> findByUserIdAndStarUserId(Long userId, Long userStarId);
    boolean existsByUserIdAndStarUserId(Long userId, Long starUserId);
}
