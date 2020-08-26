package br.com.instagram.service.impl;

import br.com.instagram.model.DTO.UserDTO;
import br.com.instagram.model.entity.UserDocument;
import br.com.instagram.model.form.UserForm;
import br.com.instagram.repository.UserRepository;
import br.com.instagram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Flux<UserDocument> getAllUser() {

        return userRepository.findAll().switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<UserDTO> getOneUserById(Long id) {

        Mono<UserDocument> userDocumentMono = userRepository.findById(id);

        Mono<UserDTO> userDTOMono = converterUserEntityToUserDTO(userDocumentMono);

        return userDTOMono;
    }

    @Override
    public Mono<UserDocument> saveUser(UserForm user) {

        return userRepository.save(converterUserFormToUserEntity(user));
    }

    @Override
    public Mono<UserDocument> updateUser(Long id, UserForm userForm) {


        return userRepository.findById(id)
                .map(userDocument -> {
                    userDocument.setUsername(userForm.getUsername());
                    userDocument.setRoles(userForm.getRoles());
                    userDocument.setPassword(userForm.getPassword());
                    userDocument.setEmail(userForm.getEmail());
                    userDocument.setCellPhone(userForm.getCellPhone());

                    return userDocument;
                })
                .flatMap(userDocument -> userRepository.save(userDocument))
                .switchIfEmpty(Mono.empty());


    }

    @Override
    public Mono<Void> deleteUser(Long id) {

        return userRepository.findById(id)
                .flatMap(userDocument -> userRepository.delete(userDocument))
                .switchIfEmpty(Mono.empty());

    }

    private Mono<UserDTO> converterUserEntityToUserDTO(Mono<UserDocument> userEntityMono) {


        return userEntityMono.map(userEntity -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setCellphone(userEntity.getCellPhone());
            userDTO.setEmail(userEntity.getEmail());
            userDTO.setUsername(userEntity.getUsername());
            userDTO.setRoles(userEntity.getRoles());

            return userDTO;
        });

    }

    private UserDocument converterUserFormToUserEntity(UserForm user) {
        UserDocument userDocument = new UserDocument();
        userDocument.setId(user.getId());
        userDocument.setCellPhone(user.getCellPhone());
        userDocument.setEmail(user.getEmail());
        userDocument.setPassword(user.getPassword());
        userDocument.setRoles(user.getRoles());
        userDocument.setUsername(user.getUsername());

        return userDocument;
    }
}
