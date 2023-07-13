package com.vigimod.api.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vigimod.api.security.entity.ERole;
import com.vigimod.api.security.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByRoleName(ERole roleName);

}
