package br.com.instagram.security;

import br.com.instagram.model.entity.UserDocument;
import br.com.instagram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class UserDetailsService implements ReactiveUserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {

        Mono<UserDocument> userByEmail = Optional.ofNullable(userRepository.findByEmail(username)).orElseThrow(() -> {throw new UsernameNotFoundException("User not found");});

        return userByEmail.cast(UserDetails.class);
    }
}
