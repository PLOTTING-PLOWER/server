package com.plotting.server.user.repository;

import com.plotting.server.user.domain.UserStar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStarRepository extends JpaRepository<UserStar, Long> {
}
