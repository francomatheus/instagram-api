package br.com.instagram.service;

import br.com.instagram.model.DTO.PostDTO;
import br.com.instagram.model.entity.PostDocument;
import br.com.instagram.model.form.PostForm;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface PostService {

    Flux<PostDTO> getAllPostByUserWithPagination(Long userId,int page, int size);

    Mono<PostDTO> getOnePostById(Long id, Long userId);

    Mono<PostDTO> createNewPostImage(FilePart photo, PostForm postForm) throws IOException;

    Mono<PostDTO> createNewPostVideo(FilePart video, PostForm postForm) throws IOException;

    Mono<PostDocument> updatePost(Long id , Long userId, PostForm newPost);

    Mono<Void> deletePost(Long id);

}
