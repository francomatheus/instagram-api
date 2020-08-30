package br.com.instagram.service.impl;

import br.com.instagram.model.DTO.UserLikeDTO;
import br.com.instagram.model.entity.PostDocument;
import br.com.instagram.model.form.UserLikeForm;
import br.com.instagram.repository.PostRepository;
import br.com.instagram.service.LikesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LikesServiceImpl implements LikesService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Mono<List<UserLikeDTO>> getAllLikeOfPost(Long postId) {
        log.info("Get all person who like the post!");
        return postRepository.findById(postId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!")))
                .map(postDocument -> {
                    return postDocument.getLikeUserName();
                })
                .map(userLikeForms -> {

                    return userLikeForms.stream()
                            .map(userLikeForm -> {
                                UserLikeDTO userLikeDTO = new UserLikeDTO();
                                userLikeDTO.setUserId(userLikeForm.getUserId());
                                userLikeDTO.setNickname(userLikeForm.getNickname());
                                return userLikeDTO;
                            }).collect(Collectors.toList());

                });
    }

    @Override
    public Mono<PostDocument> likePost(Long postId, UserLikeForm userLikeForm) {
        log.info("Like post!");
        return addLikeOnPost(postId, userLikeForm);
    }

    @Override
    public Mono<PostDocument> unLikePost(Long postId, UserLikeForm userLikeForm) {
        log.info("unlike post!");
        return removeLikeOnPost(postId, userLikeForm);
    }

    private Mono<PostDocument> addLikeOnPost(Long postId, UserLikeForm userLikeForm) {
        log.info("Add like on post!");
        return postRepository.findById(postId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!")))
                .map(postDocument -> {
                    postDocument.getLikeUserName().add(userLikeForm);
                    return postDocument;
                })
                .flatMap(postDocument -> postRepository.save(postDocument));
    }

    private Mono<PostDocument> removeLikeOnPost(Long postId, UserLikeForm userLikeForm) {
        log.info("Remove like on post!");
        return postRepository.findById(postId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!")))
                .map(postDocument -> {
                    postDocument.getLikeUserName().remove(userLikeForm);
                    return postDocument;
                })
                .flatMap(postDocument -> postRepository.save(postDocument));
    }
}
