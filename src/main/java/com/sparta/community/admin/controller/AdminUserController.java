package com.sparta.community.admin.controller;

import com.sparta.community.admin.dto.AdminUserListResponseDto;
import com.sparta.community.admin.dto.AdminUserResponseDto;
import com.sparta.community.admin.service.AdminService;
import com.sparta.community.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Secured(UserRoleEnum.Authority.ADMIN)
@RestController
@RequestMapping("/it/admin")
@RequiredArgsConstructor
public class AdminUserController {
    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<AdminUserListResponseDto> getUsers() {
        AdminUserListResponseDto result = adminService.getUsers();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<AdminUserResponseDto> getUserById(@PathVariable Long id) {
        AdminUserResponseDto result = adminService.getUserById(id);

        return ResponseEntity.ok().body(result);
    }
}
