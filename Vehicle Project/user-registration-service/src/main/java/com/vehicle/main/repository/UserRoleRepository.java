package com.vehicle.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vehicle.main.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}
