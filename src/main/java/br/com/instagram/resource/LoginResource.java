package br.com.instagram.resource;

import br.com.instagram.model.DTO.TokenDTO;
import br.com.instagram.model.request.UserRequest;
import br.com.instagram.security.jwt.JwtTokenService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v0/auth/login")
public class LoginResource {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private ReactiveAuthenticationManager authenticationManager;

    @PostMapping()
    @Operation(summary = "Login to get jwt token", tags = {"login"})
    public Mono<ResponseEntity<TokenDTO>> userLogin(@RequestBody UserRequest userRequest){

        try {
            Mono<Authentication> authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
            String token = jwtTokenService.generateToken(authenticate, userRequest.getUsername());

            return Mono.just(ResponseEntity.ok( new TokenDTO("Bearer ",token)));

        }catch (Exception e){
            e.printStackTrace();
            return Mono.just(ResponseEntity.badRequest().build());
        }

    }


}
