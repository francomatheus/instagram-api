package br.com.instagram.service;

import br.com.instagram.model.FollowersDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface FriendshipsService {

    Mono<Object> followUser(Long userId, Long userFollowId);

    Mono<Object> unfollowUser(Long userId, Long userFollowId);

    Mono<List<FollowersDTO>> getFollowers(Long userId);

    Mono<List<FollowersDTO>> getFollowing(Long userId);

}
