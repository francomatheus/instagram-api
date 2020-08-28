package br.com.instagram.model.DTO;

import br.com.instagram.model.Comment;
import br.com.instagram.model.form.UserLikeForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private Long id;
    private String pathMedia;
    private Long userId;
    private String legend;
    private Long datePosting;
    private LinkedHashSet<Comment> comments = new LinkedHashSet<>();
    private LinkedHashSet<UserLikeForm> likeUserName = new LinkedHashSet<>();

}
