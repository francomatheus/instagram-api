package br.com.instagram.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUser {

    private Long userId;
    private String nickname;
    private String name;
    private List<Long> postId = new ArrayList<>();
    private List<FollowersDTO> followers = new ArrayList<>();
    private List<FollowersDTO> followings = new ArrayList<>();
    private String biography;
    private String site;
    private String pathProfileImage;

}
