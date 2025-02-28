package com.taniya.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String fullName;
    private String email;
    private String image;
    private String location;
    private String website;
    private String birthDate;
    private String mobile;
    private String backgroundImage;
    private String bio;
    private boolean reqUser; // ✅ CamelCase convention follow kiya

    private boolean loginWithGoogle; // ✅ Consistent naming
    private boolean followed;
    private boolean verified; // ✅ isVerified → verified (getter will be isVerified)

    private List<UserDto> followers = new ArrayList<>();
    private List<UserDto> following = new ArrayList<>();
}
