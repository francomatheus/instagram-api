package br.com.instagram.service;

import br.com.instagram.model.DTO.UserRoleDTO;
import br.com.instagram.model.form.UserRoleForm;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRoleService {

    Flux<UserRoleDTO> getAllRoles();

    Mono<UserRoleDTO> getOneRoleById(Long id);

    Mono<UserRoleDTO> saveRole(UserRoleForm userRole);

    Mono<UserRoleDTO> updateRole(Long id, UserRoleForm userRole);

    Mono<Void> deleteRole(Long id);

}
