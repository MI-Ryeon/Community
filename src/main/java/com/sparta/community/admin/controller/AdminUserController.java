package com.sparta.community.admin.controller;

import com.sparta.community.admin.dto.AdminUserListResponseDto;
import com.sparta.community.admin.dto.AdminUserResponseDto;
import com.sparta.community.admin.dto.AdminUserRoleRequestDto;
import com.sparta.community.admin.service.AdminUserService;
import com.sparta.community.common.dto.ApiResponseDto;
import com.sparta.community.user.dto.UserRequestDto;
import com.sparta.community.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Secured(UserRoleEnum.Authority.ADMIN)
@RestController
@RequestMapping("/it/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final AdminUserService adminUserService;

    // 전체 회원정보 조회
    @GetMapping
    public ResponseEntity<AdminUserListResponseDto> getUsers() {
        AdminUserListResponseDto result = adminUserService.getUsers();
        return ResponseEntity.ok().body(result);
    }

    // 개별 회원정보 조회
    @GetMapping("/{id}/profile")
    public ResponseEntity<AdminUserResponseDto> getUserById(@PathVariable Long id) {
        AdminUserResponseDto result = adminUserService.getUserById(id);

        return ResponseEntity.ok().body(result);
    }

    // 회원 정보 수정
    @PutMapping("/{id}/profile")
    public ResponseEntity<ApiResponseDto> updateProfile(@PathVariable Long id, @RequestBody UserRequestDto requestDto) {
        adminUserService.updateUserProfile(id, requestDto);
        return ResponseEntity.ok().body(new ApiResponseDto("회원 정보 수정 완료", HttpStatus.OK.value()));
    }

    // 회원 권한 변경
    @PutMapping("/{id}/role")
    public ResponseEntity<ApiResponseDto> updateRole(@PathVariable Long id, @RequestBody AdminUserRoleRequestDto requestDto) {
        adminUserService.updateUserRole(id, requestDto);
        return ResponseEntity.ok().body(new ApiResponseDto("권한 변경 완료", HttpStatus.OK.value()));
    }

    // 회원 탈퇴
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> deleteUser(@PathVariable Long id) {
        adminUserService.deleteUser(id);
        return ResponseEntity.ok().body(new ApiResponseDto("회원 탈퇴 완료", HttpStatus.OK.value()));
    }

    // 회원 차단
    @PutMapping("/block/{id}")
    public ResponseEntity<ApiResponseDto> blockUser(@PathVariable Long id) {
        adminUserService.blockUser(id);
        return ResponseEntity.ok().body(new ApiResponseDto("회원 차단 완료", HttpStatus.OK.value()));
    }
}
