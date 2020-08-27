package br.com.instagram.model.DTO;

import br.com.instagram.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private Long id;
    private String pathMedia;
    private List<String> likeUserName = new ArrayList<>();
    private Long userId;
    private String legend;
    private List<Comment> comments = new ArrayList<>();
    private Long datePosting;

}