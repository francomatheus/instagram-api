package br.com.instagram.model.entity;

import br.com.instagram.model.domain.Comment;
import br.com.instagram.model.form.UserLikeForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "post")
public class PostDocument {

    @Id
    private Long id;
    @NotNull
    private String pathMedia;
    private Long userId;
    private String legend;
    private Long datePosting;
    private LinkedHashSet<Comment> comments = new LinkedHashSet<>();
    private LinkedHashSet<UserLikeForm> likeUserName = new LinkedHashSet<>();

}
