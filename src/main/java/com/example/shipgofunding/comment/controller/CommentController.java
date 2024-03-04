package com.example.shipgofunding.comment.controller;

import com.example.shipgofunding.comment.request.CommentRequest.CreateCommentRequestDTO;
import com.example.shipgofunding.comment.service.CommentService;
import com.example.shipgofunding.config.auth.PrincipalUserDetails;
import com.example.shipgofunding.config.utils.ApiResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Comment", description = "댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 생성", description = "댓글을 생성합니다.")
    @ApiResponse(responseCode = "200", description = "댓글 생성 성공")
    @PostMapping("/comment")
    public ResponseEntity<?> createComment(@RequestBody @Valid CreateCommentRequestDTO requestDTO, @AuthenticationPrincipal PrincipalUserDetails userDetails) {
        commentService.createComment(requestDTO, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }
}
