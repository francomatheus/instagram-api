package br.com.instagram.service;

import br.com.instagram.model.DTO.UserDTO;
import br.com.instagram.model.form.UserForm;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Flux<UserDTO> getAllUser();

    Mono<UserDTO> getOneUserById(Long id);

    Mono<UserDTO> saveUser(UserForm user);

    Mono<UserDTO> updateUser(Long id, UserForm userForm);

    Mono<Void> deleteUser(Long id);

}
