package br.com.instagram.repository;

import br.com.instagram.model.entity.UserDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<UserDocument, Long> {

    Mono<UserDocument> findByEmail(String email);
    Mono<UserDocument> findByUsername(String username);
    Mono<UserDocument> findByUsername(Mono<String> username);
    Mono<UserDocument> findUserDocumentByUsername(String username);
}
