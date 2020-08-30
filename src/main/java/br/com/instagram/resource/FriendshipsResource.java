package br.com.instagram.resource;

import br.com.instagram.model.DTO.FollowersDTO;
import br.com.instagram.model.entity.UserDocument;
import br.com.instagram.service.FriendshipsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedHashSet;

@Slf4j
@RestController
@RequestMapping("/v0/friendship")
public class FriendshipsResource {

    @Autowired
    private FriendshipsService friendshipsService;

    @GetMapping("/follower/{userId}")
    @Operation(summary = "Get people follow user", tags = {"friendship"})
    public Mono<ResponseEntity<LinkedHashSet<FollowersDTO>>> getAllFollowers(@PathVariable Long userId){
        return friendshipsService.getFollowers(userId)
                .map(followersDTO -> ResponseEntity.ok(followersDTO))
                .switchIfEmpty(Mono.empty());

    }

    @GetMapping("/following/{userId}")
    @Operation(summary = "Get people of user follow", tags = {"friendship"})
    public Mono<ResponseEntity<LinkedHashSet<FollowersDTO>>> getAllFollowing(@PathVariable Long userId){
        return friendshipsService.getFollowing(userId)
                .map(followersDTO -> ResponseEntity.ok(followersDTO))
                .switchIfEmpty(Mono.empty());
    }

    @PostMapping("/{userId}/follow-to/{userFollowId}")
    @Operation(summary = "Follow person", tags = {"friendship"})
    public Flux<UserDocument> followUser(@PathVariable Long userId, @PathVariable Long userFollowId){
         return friendshipsService.followUser(userId, userFollowId);
                 //.map(s -> ResponseEntity.ok(s));

    }

    @DeleteMapping("/{userId}/unfollow-to/{userFollowId}")
    @Operation(summary = "Unfollow person", tags = {"friendship"})
    public Flux<UserDocument> unfollowUser(@PathVariable Long userId, @PathVariable Long userFollowId){
        return friendshipsService.unfollowUser(userId, userFollowId);
                //.map(s -> ResponseEntity.ok(s));
    }


}
