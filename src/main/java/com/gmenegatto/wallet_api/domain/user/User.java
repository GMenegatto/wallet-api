package com.gmenegatto.wallet_api.domain.user;

import com.gmenegatto.wallet_api.domain.base.BaseEntity;
import com.gmenegatto.wallet_api.enumeration.UserTypeEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "usr_users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cpf;

    private String email;

    private String password;

    private String fullName;

    @Enumerated(EnumType.STRING)
    private UserTypeEnum type;

    public User() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UserTypeEnum getType() {
        return type;
    }

    public void setType(UserTypeEnum type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
