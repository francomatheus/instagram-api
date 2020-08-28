package br.com.instagram.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowersDTO {

    private Long id;
    private String nickname;
    private String name;

}
