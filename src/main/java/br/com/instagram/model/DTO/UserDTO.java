package br.com.instagram.model.DTO;

import br.com.instagram.model.entity.UserRoleDocument;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private String cellphone;
    private List<UserRoleDocument> roles;

}
