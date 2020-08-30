package br.com.instagram.resource;

import br.com.instagram.model.domain.Comment;
import br.com.instagram.model.entity.PostDocument;
import br.com.instagram.model.form.CommentForm;
import br.com.instagram.model.form.CommentUpdateForm;
import br.com.instagram.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.LinkedHashSet;

@RestController
@RequestMapping("/v0/comment")
public class CommentsResource {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{postId}/comments")
    @Operation(summary = "Get all comments of post", tags = {"comment"})
    private Mono<ResponseEntity<LinkedHashSet<Comment>>> getCommentsOnPost(@PathVariable Long postId){
        return commentService.getCommentsOnPost(postId)
                .map(comments -> ResponseEntity.ok(comments));
    }

    @PostMapping("/create/post/{postId}")
    @Operation(summary = "Create a new comment on post", tags = {"comment"})
    private Mono<ResponseEntity<Object>> createNewComment(@PathVariable Long postId,
                                                          @RequestBody CommentForm commentForm){
        return commentService.createNewComment(postId,commentForm)
                .map(postDocument -> ResponseEntity.status(HttpStatus.CREATED).body(postDocument));
    }

    @PutMapping("/{commentId}/post/{postId}")
    @Operation(summary = "Update comment", tags = {"comment"})
    private Mono<ResponseEntity<PostDocument>> updateComment(@PathVariable Long commentId,
                                                             @PathVariable Long postId,
                                                             @RequestBody CommentUpdateForm commentUpdateForm){
        return commentService.updateComment(commentId, postId, commentUpdateForm)
                .map(postDocument -> ResponseEntity.accepted().body(postDocument));

    }

    @DeleteMapping("/{commentId}/post/{postId}")
    @Operation(summary = "Unfollow person", tags = {"comment"})
    private Mono<ResponseEntity<Object>> deleteComment(@PathVariable Long commentId,
                                                       @PathVariable Long postId){
        return commentService.deleteComment(commentId, postId)
                .map(postDocument -> ResponseEntity.ok().build());

    }

}
