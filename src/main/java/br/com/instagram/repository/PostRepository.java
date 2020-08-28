package br.com.instagram.repository;

import br.com.instagram.model.entity.PostDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostRepository extends ReactiveMongoRepository<PostDocument, Long> {

    Flux<PostDocument> findAllByUserId(Long userId, Pageable page);

    Flux<PostDocument> findAllByUserId(Long userId);

    Mono<PostDocument> findByIdAndUserId(Long id, Long userId);



}
