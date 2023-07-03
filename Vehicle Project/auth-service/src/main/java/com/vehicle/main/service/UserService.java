package com.vehicle.main.service;

import java.util.List;

import com.vehicle.main.entity.User;
import com.vehicle.main.exception.ServiceException;

public interface UserService {

	public void createUser(List<User> user) throws ServiceException;
	
	public User getUser(String username) throws ServiceException;
	
	public String generateToken(String username) throws ServiceException;
	
	public String validateToken(String token) throws ServiceException;
	
	public List<String> getUserByRole(String username) throws ServiceException;
}
