package com.ozymern.spring.user.manager.services;

import java.util.List;
import javax.validation.ConstraintViolationException;

import com.ozymern.spring.user.manager.commands.UserForm;
import com.ozymern.spring.user.manager.domains.entities.UserEntity;

public interface UserService {

    public List<UserEntity> findAll();

    public UserForm saveOrUpdate(UserForm userForm) throws ConstraintViolationException;

    public UserForm findById(String id);

    public List<UserForm> finAll();

    public void delete(String id);

    public UserForm findByHashId(String hashId);


}
