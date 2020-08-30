package br.com.instagram.security;

import br.com.instagram.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class UserDetailsService implements ReactiveUserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        log.info("Verify username!!");
        return userRepository.findUserDocumentByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found!!")))
                .map(userDocument -> {
                    return new User(userDocument.getUsername()
                            , userDocument.getPassword()
                            , userDocument.getAuthorities());
                }).cast(UserDetails.class);

    }
}
