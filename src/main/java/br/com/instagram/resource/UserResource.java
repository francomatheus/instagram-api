package br.com.instagram.resource;

import br.com.instagram.model.DTO.UserDTO;
import br.com.instagram.model.form.UserForm;
import br.com.instagram.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RestController
@RequestMapping("/v0/")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping("user")
    @Operation(summary = "List all users", tags = {"user"})
    public Flux<UserDTO> getAllUsers(){

        return userService.getAllUser();
    }

    @GetMapping("user/{id}")
    @Operation(summary = "Get just one User", tags = {"user"})
    public Mono<ResponseEntity<UserDTO>> getUserById(@PathVariable Long id){

        return userService.getOneUserById(id)
                .map(userDTO -> ResponseEntity.ok(userDTO))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("user")
    @Operation(summary = "Save user", tags = {"user"})
    public Mono<ResponseEntity<Mono<UserDTO>>> saveUser(@RequestBody UserForm user ) throws URISyntaxException {

        Mono<UserDTO> userDocumentMono = userService.saveUser(user);

        return Mono.just(ResponseEntity.created(new URI("/v0/user/".concat(userDocumentMono.map(UserDTO::getId).toString())))
                .body(userDocumentMono))
                .onErrorReturn(ResponseEntity.notFound().build());
    }

    @PutMapping("user/{id}")
    @Operation(summary = "Update user", tags = {"user"})
    public Mono<ResponseEntity<UserDTO>> updateUser(@PathVariable Long id, @RequestBody UserForm userForm){

        return userService.updateUser(id, userForm)
                .map(userEntity -> ResponseEntity.accepted().body(userEntity))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("user/{id}")
    @Operation(summary = "Delete user", tags = {"user"})
    public Mono<ResponseEntity<Object>> deleteUser(@PathVariable Long id){

        return userService.deleteUser(id)
                .map(unused -> ResponseEntity.ok().build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
