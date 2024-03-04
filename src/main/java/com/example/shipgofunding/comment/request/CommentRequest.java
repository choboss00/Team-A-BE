package com.example.shipgofunding.comment.request;

import lombok.Getter;
import lombok.Setter;

public class CommentRequest {

    @Getter
    @Setter
    public static class CreateCommentRequestDTO {
        private int fundingId;
        private String comment;
    }
}
