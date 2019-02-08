package com.ozymern.spring.user.manager.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozymern.spring.user.manager.commands.UserForm;
import com.ozymern.spring.user.manager.converters.UserEntityToUserForm;
import com.ozymern.spring.user.manager.converters.UserFormToUserEntity;
import com.ozymern.spring.user.manager.domains.entities.RoleEntity;
import com.ozymern.spring.user.manager.domains.entities.UserEntity;
import com.ozymern.spring.user.manager.exception.NumberException;
import com.ozymern.spring.user.manager.repositories.RoleRepository;
import com.ozymern.spring.user.manager.repositories.UserRepository;
import com.ozymern.spring.user.manager.util.Encrypt;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFormToUserEntity UserFormToUserEntity;

    @Autowired
    UserEntityToUserForm userEntityToUserForm;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private Encrypt encrypt;

    @Override
    public List<UserEntity> findAll() {

        return userRepository.findAll();
    }

    @Override
    @Transactional
    public UserForm saveOrUpdate(UserForm userForm) throws ConstraintViolationException {

        userForm.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));

        userForm.setHashId(encrypt.getEncrypt(userForm.getEmail()));
        System.err.println("hashid " + encrypt.getEncrypt(userForm.getEmail()));

        UserEntity userEntity = UserFormToUserEntity.convert(userForm);

        RoleEntity userRole = roleRepository.findByRole("user");

        userEntity.setRoles(new HashSet<RoleEntity>(Arrays.asList(userRole)));

        return userEntityToUserForm.convert(userRepository.save(userEntity));

    }

    @Override
    public UserForm findById(String id) {
        if (NumberException.isNumeric(id)) {

            return userEntityToUserForm.convert(userRepository.findById(Long.parseLong(id)).orElse(new UserEntity()));
        } else {
            return new UserForm();
        }

    }

    @Override
    public void delete(String hashId) {

        UserEntity userEntity = UserFormToUserEntity.convert(this.findByHashId(hashId));

        userRepository.delete(userEntity);

    }

    @Override
    public List<UserForm> finAll() {

        List<UserForm> listUserForm = new ArrayList<>();

        userRepository.findAll().forEach(x ->
        {

            listUserForm.add(userEntityToUserForm.convert(x));
        });

        return listUserForm;
    }

    @Override
    public UserForm findByHashId(String hashId) {

        return userEntityToUserForm.convert(userRepository.findByHashId(hashId).orElse(new UserEntity()));
    }

}
