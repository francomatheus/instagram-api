package br.com.instagram.resource;

import br.com.instagram.model.FollowersDTO;
import br.com.instagram.service.FriendshipsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v0/friendship")
public class FriendshipsResource {

    @Autowired
    private FriendshipsService friendshipsService;

    @GetMapping("/follower/{userId}")
    @Operation(summary = "Get people follow user", tags = {"friendship"})
    public Mono<ResponseEntity<List<FollowersDTO>>> getAllFollowers(@PathVariable Long userId){
        return friendshipsService.getFollowers(userId)
                .map(followersDTO -> ResponseEntity.ok(followersDTO))
                .switchIfEmpty(Mono.empty());

    }

    @GetMapping("/following/{userId}")
    @Operation(summary = "Get people of user follow", tags = {"friendship"})
    public Mono<ResponseEntity<List<FollowersDTO>>> getAllFollowing(@PathVariable Long userId){
        return friendshipsService.getFollowing(userId)
                .map(followersDTO -> ResponseEntity.ok(followersDTO));
    }

    @PostMapping("/follow/{userId}/{userFollowId}")
    @Operation(summary = "Follow person", tags = {"friendship"})
    public Mono<ResponseEntity<String>> followUser(@PathVariable Long userId, @PathVariable Long userFollowId){
        return friendshipsService.followUser(userId, userFollowId)
                .map(o -> ResponseEntity.ok("Following!!"));
    }

    @DeleteMapping("/unfollow/{userId}/{userFollowId}")
    @Operation(summary = "Unfollow person", tags = {"friendship"})
    public Mono<ResponseEntity<String>> unfollowUser(@PathVariable Long userId, @PathVariable Long userFollowId){
        return friendshipsService.followUser(userId, userFollowId)
                .map(o -> ResponseEntity.ok("UnFollow!!"));
    }


}
