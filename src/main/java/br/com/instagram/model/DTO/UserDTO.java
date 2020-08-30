package br.com.instagram.model.DTO;

import br.com.instagram.model.entity.UserRoleDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private String name;
    private String email;
    private String cellphone;
    private List<UserRoleDocument> roles;

}
