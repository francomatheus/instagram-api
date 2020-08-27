package br.com.instagram.model;

import br.com.instagram.model.entity.UserDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private UserDocument userDocument;
    private LocalDateTime dateOfComment;
    private String comments;

}
