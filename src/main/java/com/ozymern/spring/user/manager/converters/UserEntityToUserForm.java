package com.ozymern.spring.user.manager.converters;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import com.ozymern.spring.user.manager.commands.UserForm;
import com.ozymern.spring.user.manager.domains.entities.UserEntity;


@Component
public class UserEntityToUserForm implements Converter<UserEntity, UserForm> {

    @Override
    public UserForm convert(UserEntity userEntity) {
        UserForm userForm = new UserForm();
        userForm.setAge(userEntity.getAge());
        userForm.setEmail(userEntity.getEmail());
        userForm.setName(userEntity.getName());
        userForm.setPassword(userEntity.getPassword());
        userForm.setId(userEntity.getUserId());
        userForm.setHashId(userEntity.getHashId());
        return userForm;
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
