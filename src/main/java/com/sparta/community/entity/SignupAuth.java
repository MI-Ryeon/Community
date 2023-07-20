package com.sparta.community.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SignupAuth {
    @Id
    String email;

    @Column(nullable = false)
    private int authStatus;

    public SignupAuth(String email) {
        this.email = email;
        this.authStatus = 0;
    }

    public void changeStatusOK(){
        this.authStatus = 1;
    }
    public void changeStatusNO(){
        this.authStatus = 0;
    }
}
