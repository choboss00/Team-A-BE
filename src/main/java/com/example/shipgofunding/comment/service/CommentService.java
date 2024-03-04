package com.example.shipgofunding.comment.service;

import com.example.shipgofunding.comment.domain.Comment;
import com.example.shipgofunding.comment.repository.CommentJpaRepository;
import com.example.shipgofunding.comment.request.CommentRequest;
import com.example.shipgofunding.config.auth.PrincipalUserDetails;
import com.example.shipgofunding.config.errors.exception.Exception404;
import com.example.shipgofunding.funding.domain.Funding;
import com.example.shipgofunding.funding.repository.FundingJpaRepository;
import com.example.shipgofunding.user.domain.User;
import com.example.shipgofunding.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class CommentService {
    private final UserRepository userRepository;

    private final CommentJpaRepository commentJpaRepository;
    private final FundingJpaRepository fundingJpaRepository;

    @Transactional
    public void createComment(CommentRequest.CreateCommentRequestDTO requestDTO, PrincipalUserDetails userDetails) {
        /**
         * 댓글 기능 구현하기
         * 1. 유저 인증
         * 2. 댓글 내용을 받아와서 저장하기
         * **/

        // 유저 인증
        userRepository.findById(Math.toIntExact(userDetails.getUser().getId()))
                .orElseThrow(() -> new Exception404("해당 사용자가 존재하지 않습니다."));

        // 댓글 내용을 받아와서 저장하기
        Funding funding = fundingJpaRepository.findById(requestDTO.getFundingId())
                .orElseThrow(() -> new Exception404("해당 펀딩이 존재하지 않습니다."));

        // 댓글 내용 받아와서 저장하기
        Comment comment = Comment.builder()
                .funding(funding)
                .user(userDetails.getUser())
                .content(requestDTO.getComment())
                .build();

        commentJpaRepository.save(comment);

    }
}
