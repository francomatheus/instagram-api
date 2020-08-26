package br.com.instagram.service;

import br.com.instagram.model.DTO.UserDTO;
import br.com.instagram.model.entity.UserDocument;
import br.com.instagram.model.form.UserForm;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Flux<UserDocument> getAllUser();

    Mono<UserDTO> getOneUserById(Long id);

    Mono<UserDocument> saveUser(UserForm user);

    Mono<UserDocument> updateUser(Long id, UserForm userForm);

    Mono<Void> deleteUser(Long id);

}
