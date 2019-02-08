package com.ozymern.spring.user.manager.commands;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;


@Component
public class UserForm {

    @NotNull
    @Min(18)
    @Max(140)
    private Integer age;


    private Long id;

    @NotEmpty
    @Size(min = 3, max = 50)
    private String name;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;

    private String hashId;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    @Override
    public String toString() {
        return "UserForm [age=" + age + ", id=" + id + ", name=" + name + ", email=" + email + ", password=" + password
                + ", hashId=" + hashId + "]";
    }


}
