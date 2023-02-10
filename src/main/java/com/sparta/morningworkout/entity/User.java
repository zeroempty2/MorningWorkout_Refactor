package com.sparta.morningworkout.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@Entity(name = "users")
@SuperBuilder
public class User extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;


    public void changeSeller(){
        this.role = UserRoleEnum.SELLER;
    }
    public void changeCustomer(){
        this.role = UserRoleEnum.CUSTOMER;
    }

    @Builder
    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public boolean checkUser(Long userId){
        return userId.equals(id);
    }
}

