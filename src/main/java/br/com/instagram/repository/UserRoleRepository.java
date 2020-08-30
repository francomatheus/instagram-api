package br.com.instagram.repository;

import br.com.instagram.model.entity.UserRoleDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRoleRepository extends ReactiveMongoRepository<UserRoleDocument, Long> {
}
