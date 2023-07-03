package com.vehicle.main.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vehicle.main.entity.User;
import com.vehicle.main.entity.UserRole;
import com.vehicle.main.exception.ServiceException;
import com.vehicle.main.jwt.JwtUtil;
import com.vehicle.main.repository.RoleRepository;
import com.vehicle.main.repository.UserRepository;
import com.vehicle.main.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public void createUser(List<User> listUser) throws ServiceException {
		log.trace("The createUser method  start");
		listUser.stream().forEach(user -> {
			try {
				Optional<User> findByUsername = userRepo.findByUsername(user.getUsername());
				log.info("The userName we found {}" + findByUsername);
				if (findByUsername.isPresent()) {
					log.warn("User already exist");
					throw new ServiceException("User already exist ");
				} else {
					for (UserRole userRole : user.getUserRoles()) {
						roleRepo.save(userRole.getRole());
					}
					user.getUserRoles().addAll(user.getUserRoles());
					user.setPassword(passwordEncoder.encode(user.getPassword()));
					userRepo.save(user);
					log.warn("User Saved :::");
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		});
	}

	@Override
	public User getUser(String username) throws ServiceException {
		try {
			User user = null;
			log.trace("getUser Method start");
			Optional<User> newUser = userRepo.findByUsername(username);
			if (!newUser.isPresent()) {
				log.warn("Username is not found !");
				throw new ServiceException("Username is not found !");
			} else {
				user = newUser.get();
			}
			return user;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}

	}

	@Override
	public String generateToken(String username) throws ServiceException {
		try {
			log.info("generateToken method start {}");
			String token = "";
			token = jwtUtil.generateToken(username);
			log.info("Token {}" + token);
			return token;
		} catch (Exception e) {
			log.error("Invalid Token Access!");
			throw new ServiceException("Invalid Token Access!");
		}
	}

	@Override
	public String validateToken(String token) throws ServiceException {
		String message = "";
		try {
			jwtUtil.validateToken(token);
			log.info("Invalid Token Access!");
			message = "Token Validate Successfully";
			log.info(message);
		} catch (Exception e) {
			message = "Token Validate Fails";
			log.warn("Token Validate Fails");
			throw new ServiceException(e);
		}
		return message;
	}

	@Override
	public List<String> getUserByRole(String username) throws ServiceException {
		try {
			List<String> roleList = new ArrayList<>();
			List<User> userRolesList = userRepo.getUserRoles(username);
			if (!userRolesList.isEmpty()) {

				Optional<User> userData = userRolesList.stream()
						.filter(user -> user.getUsername().equalsIgnoreCase(username)).findFirst();

				if (userData.isPresent()) {
					Set<UserRole> userRoles = userData.get().getUserRoles();
					for (UserRole userRole : userRoles) {
						String roleName = userRole.getRole().getRoleName();
						roleList.add(roleName);
					}
				} else {
					log.warn("User Not Present");
					throw new ServiceException("User not present !");
				}
			}
			return roleList;
		} catch (Exception e) {
			log.error(" " + e);
			throw new ServiceException(e);
		}
	}

}
