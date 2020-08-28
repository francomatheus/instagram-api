package br.com.instagram.service.impl;

import br.com.instagram.model.FollowersDTO;
import br.com.instagram.repository.UserRepository;
import br.com.instagram.service.FriendshipsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class FriendshipsServiceImpl implements FriendshipsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<Object> followUser(Long userId, Long userFollowId) {
        userRepository.findById(userFollowId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!")))
                .map(userDocument -> {
                    FollowersDTO followersDTO = new FollowersDTO();
                    followersDTO.setId(userId);
                    userDocument.getProfile().getFollowers().add(followersDTO);
                    return userDocument;
                });

        userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!")))
                .map(userDocument -> {
                    FollowersDTO followersDTO = new FollowersDTO();
                    followersDTO.setId(userFollowId);
                    userDocument.getProfile().getFollowings().add(followersDTO);
                    return userDocument;
                });
        return Mono.empty();
    }

    @Override
    public Mono<Object> unfollowUser(Long userId, Long userFollowId) {
        FollowersDTO followersDTO = new FollowersDTO();

        userRepository.findById(userFollowId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!")))
                .map(userDocument -> {
                    return userDocument.getProfile().getFollowers();
                })
                .map(followersDTOS -> {
                    // followersDTOS.indexOf(followersDTO);
                    followersDTO.setId(userId);
                    return followersDTOS.remove(followersDTO);
                });

        userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!")))
                .map(userDocument -> {
                    return userDocument.getProfile().getFollowings();
                })
                .map(followingDTOS -> {
                    // followersDTOS.indexOf(followersDTO);
                    followersDTO.setId(userFollowId);
                    return followingDTOS.remove(followersDTO);
                });
        return Mono.empty();
    }

    @Override
    public Mono<List<FollowersDTO>> getFollowers(Long userId) {

        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!")))
                .map(userDocument -> {
                    return userDocument.getProfile().getFollowers();
                })
                ;

    }

    @Override
    public Mono<List<FollowersDTO>> getFollowing(Long userId) {

        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!")))
                .map(userDocument -> {
                    return userDocument.getProfile().getFollowings();
                });

    }
}
