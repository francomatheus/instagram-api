package br.com.instagram.model.domain;

import br.com.instagram.model.DTO.FollowersDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUser {

    private Long userId;
    private String username;
    private String name;
    private String biography;
    private String site;
    private String pathProfileImage;
    private List<Long> postId = new ArrayList<>();
    private LinkedHashSet<FollowersDTO> followers = new LinkedHashSet<>();
    private LinkedHashSet<FollowersDTO> followings = new LinkedHashSet<>();

}
