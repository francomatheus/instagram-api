package br.com.instagram.service.impl;

import br.com.instagram.model.DTO.UserDTO;
import br.com.instagram.model.ProfileUser;
import br.com.instagram.model.entity.UserDocument;
import br.com.instagram.model.form.UserForm;
import br.com.instagram.repository.UserRepository;
import br.com.instagram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Flux<UserDTO> getAllUser() {

        return getUserAndConverterToDTO();
    }

    private Flux<UserDTO> getUserAndConverterToDTO() {
        return userRepository.findAll()
                .map(userDocument -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setId(userDocument.getId());
                    userDTO.setCellphone(userDocument.getCellPhone());
                    userDTO.setEmail(userDocument.getEmail());
                    userDTO.setUsername(userDocument.getUsername());
                    userDTO.setRoles(userDocument.getRoles());
                    return userDTO;
                })
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<UserDTO> getOneUserById(Long id) {

        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error to find post with id: ".concat(id.toString()))))
                .map(userDocument -> {
                        UserDTO userDTO = new UserDTO();
                        userDTO.setId(userDocument.getId());
                        userDTO.setCellphone(userDocument.getCellPhone());
                        userDTO.setName(userDocument.getName());
                        userDTO.setEmail(userDocument.getEmail());
                        userDTO.setUsername(userDocument.getUsername());
                        userDTO.setRoles(userDocument.getRoles());

                        return userDTO;
                });

    }

    @Override
    public Mono<UserDocument> saveUser(UserForm user) {

        return userRepository.save(converterUserFormToUserEntity(user));
    }

    @Override
    public Mono<UserDocument> updateUser(Long id, UserForm userForm) {


        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error to find post with id: ".concat(id.toString()))))
                .map(userDocument -> {
                    userDocument.setUsername(userForm.getNickname() );
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
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error to find post with id: ".concat(id.toString()))))
                .flatMap(userDocument -> userRepository.delete(userDocument));

    }

    private UserDocument converterUserFormToUserEntity(UserForm user) {
        UserDocument userDocument = new UserDocument();
        ProfileUser profile = new ProfileUser();

        userDocument.setId(user.getId());
        userDocument.setName(user.getName());
        userDocument.setCellPhone(user.getCellPhone());
        userDocument.setEmail(user.getEmail());
        userDocument.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userDocument.setRoles(user.getRoles());
        userDocument.setUsername(user.getNickname());
        profile.setUserId(user.getId());
        profile.setNickname(user.getNickname());
        profile.setName(user.getName());
        userDocument.setProfile(profile);

        return userDocument;
    }
}
