package br.com.instagram.resource;

import br.com.instagram.model.DTO.UserRoleDTO;
import br.com.instagram.model.form.UserRoleForm;
import br.com.instagram.service.UserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/v0/role")
public class UserRoleResource {

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping()
    @Operation(summary = "List all roles", tags = {"role"})
    public Flux<UserRoleDTO> getAllRoles(){

        return userRoleService.getAllRoles();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get just one role", tags = {"role"})
    public Mono<ResponseEntity<UserRoleDTO>> getRoleById(@PathVariable Long id){

        return userRoleService.getOneRoleById(id)
                .map(userRoleDTO -> ResponseEntity.ok(userRoleDTO))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping()
    @Operation(summary = "Save role", tags = {"role"})
    public Mono<ResponseEntity<Mono<UserRoleDTO>>> saveRole(@RequestBody UserRoleForm userRoleForm ) throws URISyntaxException {

        Mono<UserRoleDTO> userRoleDocumentMono = userRoleService.saveRole(userRoleForm);

        return Mono.just(ResponseEntity.created(new URI("/v0/role/".concat(userRoleDocumentMono.map(UserRoleDTO::getId).toString())))
                .body(userRoleDocumentMono))
                .onErrorReturn(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update role", tags = {"role"})
    public Mono<ResponseEntity<UserRoleDTO>> updateUser(@PathVariable Long id, @RequestBody UserRoleForm userForm){

        return userRoleService.updateRole(id, userForm)
                .map(userRoleDTO -> ResponseEntity.accepted().body(userRoleDTO))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete role", tags = {"role"})
    public Mono<ResponseEntity<Object>> deleteUser(@PathVariable Long id){

        return userRoleService.deleteRole(id)
                .map(unused -> ResponseEntity.ok().build());
    }
}
