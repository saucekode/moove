package com.shoplyst.user.data.repository;

import com.shoplyst.user.data.model.Role;
import com.shoplyst.user.data.model.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findRoleByUserRoles(UserRoles name);

}
