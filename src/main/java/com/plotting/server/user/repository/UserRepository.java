package com.plotting.server.user.repository;

import com.plotting.server.user.domain.User;
import com.plotting.server.user.domain.UserType.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);     // 이메일을 기반으로 사용자 검색

    boolean existsByEmail(String email);          // 특정 이메일 이미 존재하는지 확인

    boolean existsByNickname(String nickname);      // 닉네임 중복 검사

    Optional<User> findBySocialId(String socialId);

    @Query("SELECT u " +
            "FROM User u INNER JOIN UserStar s ON u.id = s.starUser.id " +
            "GROUP BY u.id " +
            "ORDER BY COUNT(s.id) DESC " +
            "LIMIT 5")
    List<User> findTop5Users();

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.role = :role WHERE u.id= :userId")
    void updateUserRoleById(Long userId, Role role);
}
