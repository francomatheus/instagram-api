package br.com.instagram.model.entity;

import br.com.instagram.model.Comment;
import br.com.instagram.model.form.UserLikeForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "post")
public class PostDocument {

    @Id
    private Long id;
    @NotNull
    private String pathMedia;
    private LinkedHashSet<UserLikeForm> likeUserName = new LinkedHashSet<>();
    private Long userId;
    private String legend;
    private List<Comment> comments = new ArrayList<>();
    private Long datePosting;

}
