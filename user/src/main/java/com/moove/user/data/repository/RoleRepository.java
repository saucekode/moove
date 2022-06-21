package com.moove.user.data.repository;

import com.moove.user.data.model.Role;
import com.moove.user.data.model.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(UserRoles name);

}
