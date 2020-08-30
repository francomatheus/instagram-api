package br.com.instagram.repository;

import br.com.instagram.model.entity.UserDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;


public interface UserRepository extends ReactiveMongoRepository<UserDocument, Long> {

    Mono<UserDocument> findByUsername(String username);

    Mono<UserDocument> findByUsername(Mono<String> username);

}
