package br.com.instagram.service;

import br.com.instagram.model.Comment;
import br.com.instagram.model.entity.PostDocument;
import br.com.instagram.model.form.CommentForm;
import br.com.instagram.model.form.CommentUpdateForm;
import reactor.core.publisher.Mono;

import java.util.LinkedHashSet;

public interface CommentService {

    Mono<LinkedHashSet<Comment>> getCommentsOnPost(Long postId);

    Mono<PostDocument> createNewComment(Long postId, CommentForm commentForm);

    Mono<PostDocument> updateComment(Long commentId, Long postId, CommentUpdateForm commentUpdateForm);

    Mono<PostDocument> deleteComment(Long commentId, Long postId);
}
