package br.com.instagram.service;

import br.com.instagram.model.DTO.UserLikeDTO;
import br.com.instagram.model.entity.PostDocument;
import br.com.instagram.model.form.UserLikeForm;
import reactor.core.publisher.Mono;

import java.util.List;

public interface LikesService {

    Mono<List<UserLikeDTO>> getAllLikeOfPost(Long postId);

    Mono<PostDocument> likePost(Long postId, UserLikeForm userLikeForm);

    Mono<PostDocument> unLikePost(Long postId, UserLikeForm userLikeForm);

}
