package br.com.instagram.service;

import br.com.instagram.model.FollowersDTO;
import br.com.instagram.model.entity.UserDocument;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedHashSet;

public interface FriendshipsService {

    Flux<UserDocument> followUser(Long userId, Long userFollowId);

    Flux<UserDocument> unfollowUser(Long userId, Long userFollowId);

    Mono<LinkedHashSet<FollowersDTO>> getFollowers(Long userId);

    Mono<LinkedHashSet<FollowersDTO>> getFollowing(Long userId);

}
