package com.Security.Service;

import com.Security.DTO.RoleDTO;
import com.Security.Model.Role;
import com.Security.Model.User;
import com.Security.Repository.RoleRepository;
import com.Security.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public RoleService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void createRole(RoleDTO roleDTO){
        String roleName=roleDTO.getRoleName();

        if(roleDTO.getRoleName() ==null)
            throw new IllegalArgumentException("Role Cannot be null");

        try{
            logger.info("attempting to create Role: {}" , roleName);
            if(roleRepository.findByRoleName(roleName).isPresent()){
                logger.warn("Role already present with name: {}" , roleName);
                throw new IllegalArgumentException("Role already taken");
            }

            Role role=new Role();
            role.setRoleName(roleName);

            roleRepository.save(role);
            logger.info("Role created successfully: {}",roleName);


        }
        catch (Exception exception){
            logger.error("Error while creating Role:{}", exception.getMessage());
            throw new RuntimeException("Role Creation Failed");

        };
    }



}
