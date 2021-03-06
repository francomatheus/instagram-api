package br.com.instagram.service.impl;

import br.com.instagram.model.domain.Comment;
import br.com.instagram.model.entity.PostDocument;
import br.com.instagram.model.form.CommentForm;
import br.com.instagram.model.form.CommentUpdateForm;
import br.com.instagram.repository.PostRepository;
import br.com.instagram.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Mono<LinkedHashSet<Comment>> getCommentsOnPost(Long postId) {
        log.info("Get comments!!");
        return postRepository.findById(postId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post not found!")))
                .map(postDocument -> {
                    return postDocument.getComments();
                });
    }

    @Override
    public Mono<PostDocument> createNewComment(Long postId, CommentForm commentForm) {
        log.info("Create a nem comment");
        Comment comment = converterCommentFormToComment(commentForm);
        return postRepository.findById(postId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post not found!")))
                .map(postDocument -> {
                    postDocument.getComments().add(comment);
                    return postDocument;
                })
                .flatMap(postDocument -> postRepository.save(postDocument));
    }


    @Override
    public Mono<PostDocument> updateComment(Long commentId, Long postId, CommentUpdateForm commentUpdateForm) {
        log.info("Update a comment");
        return postRepository.findById(postId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post not found!")))
                .map(postDocument -> {
                    postDocument.getComments().stream()
                            .filter(comment -> comment.getId() == commentUpdateForm.getId())
                            .map(comment -> {
                                comment.setComment(commentUpdateForm.getComment());
                                return comment;
                            }).collect(Collectors.toList());
                    return postDocument;
                })
                .flatMap(postDocument -> postRepository.save(postDocument));
    }

    @Override
    public Mono<PostDocument> deleteComment(Long commentId, Long postId) {
        log.info("Delete comment");
        return postRepository.findById(postId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post not found!")))
                .map(postDocument -> {
                    Comment comment = new Comment();
                    comment.setId(commentId);
                    postDocument.getComments().remove(comment);
                    return postDocument;
                })
                .flatMap(postDocument -> postRepository.save(postDocument));
    }

    private Comment converterCommentFormToComment(CommentForm commentForm) {
        log.info("Converter comment send by user to format comment domain ");
        Comment comment = new Comment();
        comment.setComment(commentForm.getComment());
        comment.setDateOfComment(getDataTimeMilliWhenCreateComment());
        comment.setId(commentForm.getId());
        comment.setNickname(commentForm.getNickname());
        comment.setUserId(commentForm.getUserId());

        return comment;
    }

    private Long getDataTimeMilliWhenCreateComment(){
        log.info("Get data in milliseconds for comment");
        ZoneId zoneId = ZoneId.systemDefault();
        long dateMilliseconds = ZonedDateTime.now(zoneId).toInstant().toEpochMilli();

        return dateMilliseconds;

    }


}
