package com.springbootexample.services;

import java.util.Map;

import com.springbootexample.model.User;


public interface UserService {
	
	public User findUserByEmail(String email);
	
	public void saveUser(User user);
	
	public long getConutOfUsers();

	public Map<String, String> getUserNameRoleTopHeader();

}
