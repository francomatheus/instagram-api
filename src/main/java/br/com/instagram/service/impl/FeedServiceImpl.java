package br.com.instagram.service.impl;

import br.com.instagram.model.entity.PostDocument;
import br.com.instagram.repository.PostRepository;
import br.com.instagram.repository.UserRepository;
import br.com.instagram.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Flux<PostDocument> getFeedForUser(Long userId) {

        LinkedHashSet<PostDocument> postFeed = new LinkedHashSet<>();
        List<Long> longs = new ArrayList<>();

        return Flux.merge(userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!")))
                .map(userDocument -> {
                    return userDocument.getProfile().getFollowings();
                })
                .map(followersDTOS -> {
                    return followersDTOS.parallelStream()
                            .map(followersDTO -> {
                                longs.add(followersDTO.getId());
                                return followersDTO.getId();
                            }).collect(Collectors.toList());
                })
                .flatMapMany(Flux::fromIterable)
                .map(aLong -> {
                    return postRepository.findAllByUserId(aLong)
                            .onErrorReturn(new PostDocument())
                            .map(postDocument -> {
                                return postDocument;
                            });
                }));


    }
}
