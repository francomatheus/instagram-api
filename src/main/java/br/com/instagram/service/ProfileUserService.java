package br.com.instagram.service;

import br.com.instagram.model.domain.ProfileUser;
import br.com.instagram.model.form.ProfileUserForm;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public interface ProfileUserService {

    Mono<ProfileUser> getProfile(Long userId);

    Mono<ProfileUser> createProfile(Long userId, ProfileUserForm profileUserForm);

    Mono<ProfileUser> updateProfile(Long userId, ProfileUserForm profileUserForm);

    Mono<Object> updateImageProfile(Long userId, FilePart imageProfile);


}
