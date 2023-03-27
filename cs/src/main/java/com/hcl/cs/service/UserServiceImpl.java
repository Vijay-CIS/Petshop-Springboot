package com.hcl.cs.service;





import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import com.hcl.cs.model.User;
import com.hcl.cs.repository.UserRepository;



@Service
public  class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	

	@Override
	public void save(User user) {
		user.getUserPassword();
		userRepository.save(user);
	}

	@Override
	public User findByUsername(String userName) {
		return userRepository.findByUserName(userName);
	}

	@Override
	public User findByUsernameAndPassword(String userName, String userPassword) throws Exception {
		User user = userRepository.findByUserNameAndUserPassword(userName, userPassword).orElse(null);
		if(user==null) {
			throw new Exception("Invalid Login Credential");
		}
		return user;
	}

	@Override
	public User findByUserId(Long userId) {
		User user = userRepository.findByUserId(userId).orElse(null);
		if(user==null) {
			throw new RuntimeException("Invalid User ID");
		}
		return user;
	}






	




	
}
