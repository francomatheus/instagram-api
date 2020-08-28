package br.com.instagram.service.impl;

import br.com.instagram.model.FollowersDTO;
import br.com.instagram.model.entity.UserDocument;
import br.com.instagram.repository.UserRepository;
import br.com.instagram.service.FriendshipsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedHashSet;

@Slf4j
@Service
public class FriendshipsServiceImpl implements FriendshipsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Flux<UserDocument> followUser(Long userId, Long userFollowId) {
        return Flux.merge(addFollowerToPersor(userId, userFollowId),
                addFollowingToUser(userId, userFollowId))
                .onErrorContinue( (throwable, o) -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
                //.map(userDocument -> {return Mono.just("Sucess follow!!");});
    }

    @Override
    public Flux<UserDocument> unfollowUser(Long userId, Long userFollowId) {
        return Flux.merge(removeFollowerFromPerson(userId, userFollowId),
                removeFollowingFromUser(userId, userFollowId))
                .onErrorContinue( (throwable, o) -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
                //.map(userDocument -> {return Mono.just("Sucess follow!!");});

    }

    @Override
    public Mono<LinkedHashSet<FollowersDTO>> getFollowers(Long userId) {

        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!")))
                .map(userDocument -> {
                    return userDocument.getProfile().getFollowers();
                })
                ;
    }

    @Override
    public Mono<LinkedHashSet<FollowersDTO>> getFollowing(Long userId) {

        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!")))
                .map(userDocument -> {
                    return userDocument.getProfile().getFollowings();
                });
    }

    private Mono<UserDocument> addFollowerToPersor(Long userId, Long userFollowId) {
        return userRepository.findById(userFollowId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!")))
                .map(userDocument -> {
                    FollowersDTO followersDTO = new FollowersDTO();
                    followersDTO.setId(userId);
                    userDocument.getProfile().getFollowers().add(followersDTO);
                    return userDocument;
                }).flatMap(userDocument -> userRepository.save(userDocument));
    }

    private Mono<UserDocument> addFollowingToUser(Long userId, Long userFollowId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!")))
                .map(userDocument -> {
                    FollowersDTO followersDTO = new FollowersDTO();
                    followersDTO.setId(userFollowId);
                    userDocument.getProfile().getFollowings().add(followersDTO);
                    return userDocument;
                }).flatMap(userDocument -> userRepository.save(userDocument));
    }

    private Mono<UserDocument> removeFollowerFromPerson(Long userId, Long userFollowId) {
        return userRepository.findById(userFollowId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!")))
                .map(userDocument -> {
                    FollowersDTO followersDTO = new FollowersDTO();
                    followersDTO.setId(userId);
                    userDocument.getProfile().getFollowers().remove(followersDTO);

                    return userDocument;
                })
                .flatMap(userDocument -> userRepository.save(userDocument) );
    }

    private Mono<UserDocument> removeFollowingFromUser(Long userId, Long userFollowId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!")))
                .map(userDocument -> {
                    FollowersDTO followersDTO = new FollowersDTO();
                    followersDTO.setId(userFollowId);
                    userDocument.getProfile().getFollowings().remove(followersDTO);

                    return userDocument;
                })
                .flatMap(userDocument -> userRepository.save(userDocument));
    }

}
