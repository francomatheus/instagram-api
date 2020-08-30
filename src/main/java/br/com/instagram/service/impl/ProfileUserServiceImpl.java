package br.com.instagram.service.impl;

import br.com.instagram.model.domain.ProfileUser;
import br.com.instagram.model.entity.UserDocument;
import br.com.instagram.model.form.ProfileUserForm;
import br.com.instagram.repository.UserRepository;
import br.com.instagram.service.ProfileUserService;
import br.com.instagram.service.SaveMediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ProfileUserServiceImpl implements ProfileUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SaveMediaService saveMediaService;

    @Override
    public Mono<ProfileUser> getProfile(Long userId) {
        log.info("Get profile!");
        Mono<ProfileUser> mapProfile = getJustProfileUser(userId);
        return mapProfile;
    }

    @Override
    public Mono<ProfileUser> createProfile(Long userId, ProfileUserForm profileUserForm) {
        log.info("Create a new profile!");
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!")))
                .map(userDocument -> {
                    ProfileUser profileUser = new ProfileUser();
                    profileUser.setSite(profileUserForm.getSite());
                    profileUser.setName(profileUserForm.getName());
                    profileUser.setBiography(profileUserForm.getBiography());
                    profileUser.setUserId(userDocument.getProfile().getUserId());
                    profileUser.setUsername(userDocument.getProfile().getUsername());
                    profileUser.setFollowers(userDocument.getProfile().getFollowers());
                    profileUser.setFollowings(userDocument.getProfile().getFollowings());


                    userDocument.setProfile(profileUser);
                    return userDocument;
                })
                .flatMap(userDocument -> userRepository.save(userDocument))
                .map(userDocument -> {
                    ProfileUser profileUser = new ProfileUser();

                    profileUser.setBiography(userDocument.getProfile().getBiography());
                    profileUser.setFollowers(userDocument.getProfile().getFollowers());
                    profileUser.setFollowings(userDocument.getProfile().getFollowings());
                    profileUser.setName(userDocument.getProfile().getName());
                    profileUser.setUsername(userDocument.getProfile().getUsername());
                    profileUser.setPathProfileImage(userDocument.getProfile().getPathProfileImage());
                    profileUser.setPostId(userDocument.getProfile().getPostId());
                    profileUser.setSite(userDocument.getProfile().getSite());
                    profileUser.setUserId(userDocument.getProfile().getUserId());

                    return profileUser;
                });


    }

    @Override
    public Mono<ProfileUser> updateProfile(Long userId, ProfileUserForm profileUserForm) {
        log.info("Update a profile!");
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!")))
                .map(userDocument -> {
                    ProfileUser profileUser = new ProfileUser();
                    profileUser.setSite(profileUserForm.getSite());
                    profileUser.setName(profileUserForm.getName());
                    profileUser.setBiography(profileUserForm.getBiography());
                    profileUser.setUserId(userDocument.getProfile().getUserId());
                    profileUser.setUsername(userDocument.getProfile().getUsername());
                    profileUser.setPathProfileImage(userDocument.getProfile().getPathProfileImage());
                    profileUser.setPostId(userDocument.getProfile().getPostId());
                    profileUser.setFollowers(userDocument.getProfile().getFollowers());
                    profileUser.setFollowings(userDocument.getProfile().getFollowings());

                    userDocument.setProfile(profileUser);
                    return userDocument;
                })
                .flatMap(userDocument -> userRepository.save(userDocument))
                .map(userDocument -> {
                    ProfileUser profileUser = new ProfileUser();

                    profileUser.setBiography(userDocument.getProfile().getBiography());
                    profileUser.setFollowers(userDocument.getProfile().getFollowers());
                    profileUser.setFollowings(userDocument.getProfile().getFollowings());
                    profileUser.setName(userDocument.getProfile().getName());
                    profileUser.setUsername(userDocument.getProfile().getUsername());
                    profileUser.setPathProfileImage(userDocument.getProfile().getPathProfileImage());
                    profileUser.setPostId(userDocument.getProfile().getPostId());
                    profileUser.setSite(userDocument.getProfile().getSite());
                    profileUser.setUserId(userDocument.getProfile().getUserId());

                    return profileUser;
                });
    }

    @Override
    public Mono<Object> updateImageProfile(Long userId, FilePart imageProfile) {
        log.info("Update a profile image!");
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!")))
                .map(userDocument -> {
                    String newImage = saveMediaService.saveProfileImage(imageProfile, userId);

                    ProfileUser profileUser = new ProfileUser();

                    profileUser.setBiography(userDocument.getProfile().getBiography());
                    profileUser.setFollowers(userDocument.getProfile().getFollowers());
                    profileUser.setFollowings(userDocument.getProfile().getFollowings());
                    profileUser.setName(userDocument.getProfile().getName());
                    profileUser.setUsername(userDocument.getProfile().getUsername());
                    profileUser.setPathProfileImage(newImage);
                    profileUser.setPostId(userDocument.getProfile().getPostId());
                    profileUser.setSite(userDocument.getProfile().getSite());

                    userDocument.setProfile(profileUser);

                    return userDocument;
                })
                .flatMap(userDocument -> userRepository.save(userDocument));

    }

    private Mono<ProfileUser> getJustProfileUser(Long userId) {
        log.info("Get one profile by userId!");
        Mono<UserDocument> byId = userRepository.findById(userId);

        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!")))
                .map(userDocument -> {
                    ProfileUser profileUser = new ProfileUser();

                    profileUser.setBiography(userDocument.getProfile().getBiography());
                    profileUser.setFollowers(userDocument.getProfile().getFollowers());
                    profileUser.setFollowings(userDocument.getProfile().getFollowings());
                    profileUser.setName(userDocument.getProfile().getName());
                    profileUser.setUsername(userDocument.getProfile().getUsername());
                    profileUser.setPathProfileImage(userDocument.getProfile().getPathProfileImage());
                    profileUser.setPostId(userDocument.getProfile().getPostId());
                    profileUser.setSite(userDocument.getProfile().getSite());
                    profileUser.setUserId(userDocument.getProfile().getUserId());

                    return profileUser;
                })
                .switchIfEmpty(Mono.empty());
    }
}
