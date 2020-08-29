package br.com.instagram.resource;

import br.com.instagram.model.DTO.PostDTO;
import br.com.instagram.model.entity.PostDocument;
import br.com.instagram.model.form.PostForm;
import br.com.instagram.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/v0/post")
@Slf4j
public class PostResource {

    @Autowired
    private PostService postService;

    @GetMapping()
    @Operation(summary = "List pageable all post about userId", tags = {"post"})
    public Flux<PostDTO> getAllPostByUser(@RequestParam Long userId,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "1000") int size){

        return postService.getAllPostByUserWithPagination(userId, page, size);
    }

    @GetMapping("/{id}/user/{userId}")
    @Operation(summary = "Just one post about id and userId ", tags = {"post"})
    public Mono<ResponseEntity<PostDTO>> getPost(@PathVariable Long id, @PathVariable Long userId){

        return postService.getOnePostById(id, userId)
                .map(postDTO -> ResponseEntity.ok(postDTO))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create a image post", tags = {"post"})
    public Mono<ResponseEntity<Mono<PostDTO>>> createNewPostImage(@RequestPart("file") FilePart photo,
                                                                  @RequestPart String id,
                                                                  @RequestPart(required = false) String userId,
                                                                  @RequestPart(required = false) String legend){

        try {
            Mono<PostDTO> newPostImage = postService.createNewPostImage(photo,
                    new PostForm(Long.parseLong(id),Long.parseLong(userId),legend));

            return Mono.just(ResponseEntity.created(new URI("/v0/post/".concat(newPostImage.map(PostDTO::getId).toString())))
                    .body(newPostImage))
                    .onErrorReturn(ResponseEntity.notFound().build());


        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return Mono.error(() -> e);
        }
    }

    @PostMapping("/video")
    @Operation(summary = "Create a video post", tags = {"post"})
    public Mono<ResponseEntity<Mono<PostDTO>>> createNewPostVideo(@RequestParam FilePart video,
                                                                  @RequestPart String id,
                                                                  @RequestPart(required = false) String userId,
                                                                  @RequestPart(required = false) String legend){
        try {
            Mono<PostDTO> newPostVideo = postService.createNewPostVideo(video,
                    new PostForm(Long.parseLong(id),Long.parseLong(userId),legend));

            return Mono.just(ResponseEntity.created(new URI("/v0/post/".concat(newPostVideo.map(PostDTO::getId).toString())))
                    .body(newPostVideo))
                    .onErrorReturn(ResponseEntity.notFound().build());


        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            return Mono.error(() -> e);
        }

    }

    @PutMapping("/{id}/user/{userId}")
    @Operation(summary = "Update especific post", tags = {"post"})
    public Mono<ResponseEntity<PostDocument>> updatePost(@PathVariable Long id, @PathVariable Long userId, @RequestBody PostForm newPost){

        return postService.updatePost(id, userId, newPost)
                .map(postDocument -> ResponseEntity.accepted().body(postDocument))
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete post", tags = {"post"})
    public Mono<ResponseEntity<Object>> deletePost(@PathVariable Long id){

        return postService.deletePost(id)
                .map(unused -> ResponseEntity.ok().build());
    }

}
