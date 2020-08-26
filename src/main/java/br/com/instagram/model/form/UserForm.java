package br.com.instagram.model.form;

import br.com.instagram.model.entity.UserRoleDocument;
import lombok.Data;

import java.util.List;

@Data
public class UserForm {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String cellPhone;

    private List<UserRoleDocument> roles;

}
