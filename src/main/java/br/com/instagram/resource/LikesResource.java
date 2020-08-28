package br.com.instagram.resource;

import br.com.instagram.model.DTO.UserLikeDTO;
import br.com.instagram.model.entity.PostDocument;
import br.com.instagram.model.form.UserLikeForm;
import br.com.instagram.service.LikesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v0/likes")
public class LikesResource {

    @Autowired
    private LikesService likesService;

    @GetMapping("/{postId}")
    public Mono<ResponseEntity<List<UserLikeDTO>>> getAllLikeOfOnePost(@PathVariable Long postId){
        return likesService.getAllLikeOfPost(postId)
                .map(userLikeDTOS -> ResponseEntity.ok(userLikeDTOS));
    }


    @PostMapping("/{postId}/like")
    public Mono<ResponseEntity<PostDocument>> likePost(@PathVariable Long postId, @RequestBody UserLikeForm userLikeForm){
        return likesService.likePost(postId, userLikeForm)
                .map(postDocument -> ResponseEntity.ok(postDocument));
    }

    @DeleteMapping("/{postId}/unlike")
    public Mono<ResponseEntity<PostDocument>> unLikePost(@PathVariable Long postId, @RequestBody UserLikeForm userLikeForm){
        return likesService.unLikePost(postId, userLikeForm)
                .map(postDocument -> ResponseEntity.ok(postDocument));
    }

}
