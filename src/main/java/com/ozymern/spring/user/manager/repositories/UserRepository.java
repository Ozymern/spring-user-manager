package com.ozymern.spring.user.manager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ozymern.spring.user.manager.domains.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public String findByName(Long userId);

    public UserEntity findByEmail(String email);

    public Optional<UserEntity> findByHashId(String hashId);

}
