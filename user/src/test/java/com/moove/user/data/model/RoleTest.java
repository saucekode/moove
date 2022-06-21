package com.moove.user.data.model;

import com.moove.user.data.repository.RoleRepository;
import com.moove.user.web.controllers.AuthController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleTest {


    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    RoleRepository roleRepository;

    @Test
    @DisplayName("Test for all size of roles")
    public void testForRoleSize(){
        assertEquals(3, roleRepository.count());
    }

    @Test
    @DisplayName("Test for admin role")
    public void testForAdmin(){
//        System.out.println(roleRepository.findByName(UserRoles.ADMIN.getName()).isEmpty());
        System.out.println(UserRoles.ADMIN.name());
        System.out.println(roleRepository.findByName(UserRoles.ADMIN));
        assertEquals(UserRoles.ADMIN, roleRepository.findByName(UserRoles.ADMIN).getName());
        logger.info("Admin role: {}", UserRoles.ADMIN);
    }



}