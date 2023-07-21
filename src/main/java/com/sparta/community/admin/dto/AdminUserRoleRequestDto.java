package com.sparta.community.admin.dto;

import com.sparta.community.user.entity.UserRoleEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUserRoleRequestDto {

    @NotBlank
    private UserRoleEnum role;
}
