package com.example.shipgofunding.comment.response;

import com.example.shipgofunding.comment.domain.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CommentResponse {

    @Getter
    @Setter
    public static class CommentResponseDTO {

        private int commentId;
        private String nickname;
        private long createdAt;
        private String content;

        // 생성 날짜와 현재 날짜의 차이를 일수로 반환하는 메소드
        public long getDaysSinceCreated(LocalDateTime createdAt) {
            return ChronoUnit.DAYS.between(createdAt, LocalDateTime.now());
        }

        public CommentResponseDTO(Comment comment) {
            this.commentId = comment.getId();
            this.nickname = comment.getUser().getNickname();
            this.createdAt = getDaysSinceCreated(comment.getCreatedAt());
            this.content = comment.getContent();
        }

    }


}
