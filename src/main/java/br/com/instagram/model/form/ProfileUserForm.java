package br.com.instagram.model.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUserForm {

    private String name;
    private String biography;
    private String site;

}
