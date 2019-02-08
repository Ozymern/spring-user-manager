package com.ozymern.spring.user.manager.converters;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import com.ozymern.spring.user.manager.commands.UserForm;
import com.ozymern.spring.user.manager.domains.entities.UserEntity;

@Component
public class UserFormToUserEntity implements Converter<UserForm, UserEntity> {

    @Override
    public UserEntity convert(UserForm userForm) {
        UserEntity userEntity = new UserEntity();
        userEntity.setAge(userForm.getAge());
        userEntity.setEmail(userForm.getEmail());
        userEntity.setName(userForm.getName());
        userEntity.setEnabled(true);
        userEntity.setUserId(userForm.getId());
        userEntity.setPassword(userForm.getPassword());
        userEntity.setHashId(userForm.getHashId());
        return userEntity;
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        // TODO Auto-generated method stub
        return null;
    }

}
