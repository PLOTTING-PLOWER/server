package com.plotting.server.user.repository;

import com.plotting.server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u " +
            "FROM User u INNER JOIN UserStar s ON u.id = s.starUser.id " +
            "GROUP BY u.id " +
            "ORDER BY COUNT(s.id) DESC " +
            "LIMIT 5")
    List<User> findTop5Users();
}
