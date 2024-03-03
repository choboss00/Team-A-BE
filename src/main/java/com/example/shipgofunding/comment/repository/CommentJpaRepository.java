package com.example.shipgofunding.comment.repository;

import com.example.shipgofunding.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentJpaRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByFundingId(int fundingId);
}
