package br.com.instagram.resource;


import br.com.instagram.model.ProfileUser;
import br.com.instagram.model.form.ProfileUserForm;
import br.com.instagram.service.ProfileUserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/v0/profile")
public class ProfileResource {

    @Autowired
    private ProfileUserService profileUserService;

    @GetMapping("/{userId}")
    @Operation(summary = "Get profile about user", tags = {"profile"})
    public Mono<ResponseEntity<ProfileUser>> getProfile(@PathVariable Long userId){

        return profileUserService.getProfile(userId)
                .map(profileUser ->  ResponseEntity.ok(profileUser))
                .switchIfEmpty(Mono.empty());
    }

    @PostMapping("/{userId}")
    @Operation(summary = "Create a profile", tags = {"profile"})
    public Mono<ResponseEntity<ProfileUser>> createProfile(@PathVariable Long userId, @RequestBody ProfileUserForm profileForm){
        return profileUserService.createProfile(userId,profileForm)
                .map(profileUser -> ResponseEntity.status(HttpStatus.CREATED).body(profileUser));
    }

    @PutMapping("/update/{userId}")
    @Operation(summary = "Update values about user profile", tags = {"profile"})
    public Mono<ResponseEntity<ProfileUser>> updateProfile(@PathVariable Long userId, @RequestBody ProfileUserForm profileForm ){
        return profileUserService.updateProfile(userId,profileForm)
                .map(profileUser -> ResponseEntity.ok(profileUser));
    }

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Update image profile", tags = {"profile"})
    public Mono<ResponseEntity<String>> updateImageProfile(@RequestPart String userId, @RequestPart("file") FilePart imageProfile){
        return profileUserService.updateImageProfile(Long.parseLong(userId), imageProfile)
            .map(o -> ResponseEntity.ok("Update Sucessfull!!"));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Just one post about id and userId ", tags = {"profile"})
    public void deleteProfile(@PathVariable Long id){

    }

}
