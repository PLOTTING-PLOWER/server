package com.plotting.server.comment.repository;

import com.plotting.server.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.plogging.id = :ploggingId")
    List<Comment> findCommentsByPloggingId(Long ploggingId);
}
