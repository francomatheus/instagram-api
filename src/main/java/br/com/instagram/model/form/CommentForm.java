package br.com.instagram.model.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentForm {

    private Long id;
    private Long userId;
    private String nickname;
    private String comment;

}
