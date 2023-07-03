package com.vehicle.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vehicle.main.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByUsername(String username);
	
	@Query("select u from User u inner join UserRole usr on usr.user = u.id inner join Role rl on rl.roleId=usr.role where u.username =:username")
	public List<User> getUserRoles(String username);
}
