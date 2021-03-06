package br.com.instagram.service.impl;

import br.com.instagram.model.DTO.PostDTO;
import br.com.instagram.model.entity.PostDocument;
import br.com.instagram.model.form.PostForm;
import br.com.instagram.repository.PostRepository;
import br.com.instagram.repository.UserRepository;
import br.com.instagram.service.PostService;
import br.com.instagram.service.SaveMediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private SaveMediaService saveMediaService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Flux<PostDTO> getAllPostByUserWithPagination(Long userId, int page, int size) {
        log.info("Get all post about one User!");

        Flux<PostDTO> map = getAllPostAndConverToDTO(userId, page, size);

        return map;
    }

    @Override
    public Mono<PostDTO> getOnePostById(Long id, Long userId) {
        log.info("Get one specific post about User!");
        Mono<PostDTO> mapPost = getOnePostAndConverterToDTO(id, userId);
        return mapPost;
    }

    @Override
    public Mono<PostDTO> createNewPostImage(FilePart photo, PostForm postForm) throws IOException {
        log.info("Start create a new image post!");

        return userRepository.findById(postForm.getUser_id())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found!!")))
                .map(userDocument -> {

                    String pathImage = saveMediaService.saveImage(photo, postForm.getUser_id());
                    PostDocument postDocumentMono = converterFormToEntity(pathImage, postForm);
                    return postRepository.save(postDocumentMono)
                            .map(postDocument -> {
                                PostDTO postDTO = new PostDTO();
                                postDTO.setDatePosting(postDocument.getDatePosting());
                                postDTO.setId(postDocument.getId());
                                postDTO.setLegend(postDocument.getLegend());
                                postDTO.setPathMedia(postDocument.getPathMedia());
                                postDTO.setUserId(postDocument.getUserId());

                                return postDTO;
                            });

                }).flatMap(postDTOMono -> {
                    return Mono.first(postDTOMono);
                });

    }

    @Override
    public Mono<PostDTO> createNewPostVideo(FilePart video, PostForm postForm) throws IOException {
        log.info("Start create a new video post!");

        return userRepository.findById(postForm.getUser_id())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found!!")))
                .map(userDocument -> {

                    String pathImage = saveMediaService.saveVideo(video, postForm.getUser_id());

                    PostDocument postDocumentMono = converterFormToEntity(pathImage, postForm);
                    return postRepository.save(postDocumentMono)
                            .map(postDocument -> {
                                PostDTO postDTO = new PostDTO();
                                postDTO.setDatePosting(postDocument.getDatePosting());
                                postDTO.setId(postDocument.getId());
                                postDTO.setLegend(postDocument.getLegend());
                                postDTO.setPathMedia(postDocument.getPathMedia());
                                postDTO.setUserId(postDocument.getUserId());

                                return postDTO;
                            });


                }).flatMap(postDTOMono -> {
                    return Mono.first(postDTOMono);
                });
    }

    @Override
    public Mono<PostDocument> updatePost(Long id, Long userId, PostForm newPost) {
        log.info("Update post!");
        return postRepository.findByIdAndUserId(id, userId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error to find post with id: ".concat(id.toString()))))
                .map(postDocument -> {
                    postDocument.setLegend(newPost.getLegend());
                    postDocument.setComments(postDocument.getComments());
                    postDocument.setLikeUserName(postDocument.getLikeUserName());
                    return postDocument;
                })
                .flatMap(postDocument -> postRepository.save(postDocument))
                .switchIfEmpty(Mono.empty());

    }

    @Override
    public Mono<Void> deletePost(Long id) {
        log.info("Delete post!");
        return postRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error to find post with id: ".concat(id.toString()))))
                .map(postDocument -> {
                    saveMediaService.deleteMedia(postDocument.getPathMedia());
                    return postDocument;
                })
                .flatMap(postDocument -> postRepository.delete(postDocument));
    }

    private Flux<PostDTO> getAllPostAndConverToDTO(Long userId, int page, int size) {
        log.info("Get all post from database and converter to PostDTO!");
        Flux<PostDTO> map = postRepository.findAllByUserId(userId, PageRequest.of(page, size))
                .map(postDocument -> {
                    PostDTO postDTO = new PostDTO();

                    postDTO.setComments(postDocument.getComments());
                    postDTO.setDatePosting(postDocument.getDatePosting());
                    postDTO.setId(postDocument.getId());
                    postDTO.setLegend(postDocument.getLegend());
                    postDTO.setLikeUserName(postDocument.getLikeUserName());
                    postDTO.setPathMedia(postDocument.getPathMedia());
                    postDTO.setUserId(postDocument.getUserId());

                    return postDTO;
                });
        return map;
    }

    private Mono<PostDTO> getOnePostAndConverterToDTO(Long id, Long userId) {
        log.info("Get One post from database and converter to postDTO!");
        Mono<PostDTO> mapPost = postRepository.findByIdAndUserId(id, userId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error to find post with id: ".concat(id.toString()))))
                .map(postDocument -> {
                    PostDTO postDTO = new PostDTO();
                    postDTO.setComments(postDocument.getComments());
                    postDTO.setDatePosting(postDocument.getDatePosting());
                    postDTO.setId(postDocument.getId());
                    postDTO.setLegend(postDocument.getLegend());
                    postDTO.setLikeUserName(postDocument.getLikeUserName());
                    postDTO.setPathMedia(postDocument.getPathMedia());
                    postDTO.setUserId(postDocument.getUserId());
                    return postDTO;
                });
        return mapPost;
    }

    private PostDocument converterFormToEntity(String pathImage, PostForm postForm) {
        log.info("Converter postForm in to postDocument!");
        PostDocument postDocument = new PostDocument();
        postDocument.setDatePosting(getDateNowInMilliseconds());
        postDocument.setId(postForm.getId());
        postDocument.setPathMedia(pathImage);
        postDocument.setLegend(postForm.getLegend());
        postDocument.setUserId(postForm.getUser_id());
        postDocument.setId(postForm.getId());

        return postDocument;
    }

    private Long getDateNowInMilliseconds(){
        log.info("Get datetime in milliseconds!");
        ZoneId zoneId = ZoneId.systemDefault();
        long dateMilliseconds = ZonedDateTime.now(zoneId).toInstant().toEpochMilli();

        return dateMilliseconds;
    }
}
