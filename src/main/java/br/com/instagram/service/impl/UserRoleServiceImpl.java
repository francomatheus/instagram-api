package br.com.instagram.service.impl;

import br.com.instagram.model.DTO.UserRoleDTO;
import br.com.instagram.model.entity.UserRoleDocument;
import br.com.instagram.model.form.UserRoleForm;
import br.com.instagram.repository.UserRoleRepository;
import br.com.instagram.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public Flux<UserRoleDTO> getAllRoles() {
        return userRoleRepository.findAll()
                .switchIfEmpty(Mono.empty())
                .map(allRoles -> {
                    return allRoles;
                }).map(userRoleDocument -> {
                    return new UserRoleDTO(userRoleDocument.getId(), userRoleDocument.getRole());
                });
    }

    @Override
    public Mono<UserRoleDTO> getOneRoleById(Long id) {
        return userRoleRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error to find id")))
                .map(userRoleDocument -> {
                    return new UserRoleDTO(userRoleDocument.getId(),userRoleDocument.getRole());
                });
    }

    @Override
    public Mono<UserRoleDTO> saveRole(UserRoleForm userRole) {
        UserRoleDocument userRoleDocument = converterUserRoleFormToUserRoleDocument(userRole);
        return userRoleRepository.save(userRoleDocument)
                .map(userRoleDocument1 -> {
                    return new UserRoleDTO(userRoleDocument1.getId(),userRoleDocument1.getRole());
                });
    }

    @Override
    public Mono<UserRoleDTO> updateRole(Long id, UserRoleForm userRole) {
        return userRoleRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error to find id ")))
                .map(userRoleDocument -> {
                    userRoleDocument.setRole(userRole.getRole());
                    return userRoleDocument;
                })
                .flatMap(userRoleDocument -> userRoleRepository.save(userRoleDocument))
                .map(userRoleDocument -> {
                    return new UserRoleDTO(userRoleDocument.getId(),userRoleDocument.getRole());
                });
    }

    @Override
    public Mono<Void> deleteRole(Long id) {
        return userRoleRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error to find  id ")))
                .flatMap(userRoleDocument -> userRoleRepository.delete(userRoleDocument));
    }

    private UserRoleDocument converterUserRoleFormToUserRoleDocument(UserRoleForm userRole) {
        return new UserRoleDocument(userRole.getId(), userRole.getRole());
    }
}
