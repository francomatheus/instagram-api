package br.com.instagram.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_role")
public class UserRoleDocument implements GrantedAuthority {

    @Id
    private Long id;
    private String role;


    @Override
    public String getAuthority() {
        return role;
    }
}