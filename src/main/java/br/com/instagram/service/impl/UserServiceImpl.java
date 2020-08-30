package br.com.instagram.service.impl;

import br.com.instagram.model.DTO.UserDTO;
import br.com.instagram.model.domain.ProfileUser;
import br.com.instagram.model.entity.UserDocument;
import br.com.instagram.model.form.UserForm;
import br.com.instagram.repository.UserRepository;
import br.com.instagram.service.PostService;
import br.com.instagram.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    @Override
    public Flux<UserDTO> getAllUser() {
        log.info("Get all users");
        return getUserAndConverterToDTO();
    }

    @Override
    public Mono<UserDTO> getOneUserById(Long id) {
        log.info("Get just one user!");
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
    public Mono<UserDTO> saveUser(UserForm user) {
        log.info("Save user!");
        return userRepository.save(converterUserFormToUserDocument(user))
                .map(userDocument -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setId(userDocument.getId());
                    userDTO.setName(userDocument.getName());
                    userDTO.setRoles(userDocument.getRoles());
                    userDTO.setUsername(userDocument.getUsername());
                    userDTO.setEmail(userDocument.getEmail());
                    userDTO.setCellphone(userDocument.getCellPhone());

                    return userDTO;
                });
    }

    @Override
    public Mono<UserDTO> updateUser(Long id, UserForm userForm) {
        log.info("Update user!");
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error to find post with id: ".concat(id.toString()))))
                .map(userDocument -> {
                    userDocument.setUsername(userForm.getUsername() );
                    userDocument.setRoles(userForm.getRoles());
                    userDocument.setPassword(userForm.getPassword());
                    userDocument.setEmail(userForm.getEmail());
                    userDocument.setCellPhone(userForm.getCellPhone());

                    return userDocument;
                })
                .flatMap(userDocument -> userRepository.save(userDocument))
                .map(userDocument -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setId(userDocument.getId());
                    userDTO.setName(userDocument.getName());
                    userDTO.setRoles(userDocument.getRoles());
                    userDTO.setUsername(userDocument.getUsername());
                    userDTO.setEmail(userDocument.getEmail());
                    userDTO.setCellphone(userDocument.getCellPhone());

                    return userDTO;
                });

    }

    @Override
    public Mono<Void> deleteUser(Long id) {
        log.info("Delete user!");
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error to find post with id: ".concat(id.toString()))))
                .flatMap(userDocument -> userRepository.delete(userDocument));

    }
    private Flux<UserDTO> getUserAndConverterToDTO() {
        log.info("Get all user from database and converter to userDTO!");
        return userRepository.findAll()
                .map(userDocument -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setId(userDocument.getId());
                    userDTO.setCellphone(userDocument.getCellPhone());
                    userDTO.setEmail(userDocument.getEmail());
                    userDTO.setUsername(userDocument.getUsername());
                    userDTO.setRoles(userDocument.getRoles());
                    userDTO.setName(userDocument.getName());
                    return userDTO;
                })
                .switchIfEmpty(Flux.empty());
    }

    private UserDocument converterUserFormToUserDocument(UserForm user) {
        log.info("Converter userForm to userDocument for save in database");
        UserDocument userDocument = new UserDocument();
        ProfileUser profile = new ProfileUser();

        userDocument.setId(user.getId());
        userDocument.setName(user.getName());
        userDocument.setCellPhone(user.getCellPhone());
        userDocument.setEmail(user.getEmail());
        userDocument.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userDocument.setRoles(user.getRoles());
        userDocument.setUsername(user.getUsername());
        profile.setUserId(user.getId());
        profile.setUsername(user.getUsername());
        profile.setName(user.getName());
        userDocument.setProfile(profile);

        return userDocument;
    }
}
