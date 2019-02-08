package com.ozymern.spring.user.manager.domains.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;


@Entity
@Table(name = "users", schema = "public")
public class UserEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "age")
    private int age;
    @Column(name = "name")
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "email", unique = true)
    private String email;

    @Column(nullable = false, name = "enabled")

    private Boolean enabled;
    @Column(nullable = false, name = "password")
    private String password;

    @Column(name = "hash_id")
    private String hashId;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable
    private Set<RoleEntity> roles = new HashSet<>();


    public Long getUserId() {
        return userId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public int getAge() {
        return age;
    }


    public void setAge(int age) {
        this.age = age;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Date getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public Boolean getEnabled() {
        return enabled;
    }


    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public Set<RoleEntity> getRoles() {
        return roles;
    }


    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }


    public String getHashId() {
        return hashId;
    }


    public void setHashId(String hashId) {
        this.hashId = hashId;
    }


    @Override
    public String toString() {
        return "UserEntity [userId=" + userId + ", age=" + age + ", name=" + name + ", createdAt=" + createdAt
                + ", email=" + email + ", enabled=" + enabled + ", password=" + password + ", hashId=" + hashId
                + ", roles=" + roles + "]";
    }


}
