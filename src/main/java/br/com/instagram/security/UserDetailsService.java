package br.com.instagram.security;

import br.com.instagram.model.entity.UserDocument;
import br.com.instagram.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class UserDetailsService implements ReactiveUserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        log.info("Verify username!!");

        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.defer(() -> {
                    return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found!!"));
                }))
                .map(userDetails -> {
                    return new User(userDetails.getUsername(),
                            userDetails.getPassword(),
                            userDetails.getAuthorities());
                });

    }
}
