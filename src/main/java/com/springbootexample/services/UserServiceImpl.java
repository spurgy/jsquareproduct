package com.springbootexample.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springbootexample.dao.RoleRepository;
import com.springbootexample.dao.UserRepository;
import com.springbootexample.model.Role;
import com.springbootexample.model.User;


@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        //Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	@Override
	public long getConutOfUsers() {
		return userRepository.count();
	}

	@Override
	public Map<String, String> getUserNameRoleTopHeader() {
		// TODO Auto-generated method stub
		Map<String, String> allusers= new HashMap<String, String>();
		for(User users : userRepository.findAll()) {
			if(users.getEmail().equals(currentUsername("username"))) {
				allusers.put(users.getName()+" "+users.getLastName(), currentUsername("userrole"));
			}
		}
		return allusers;
	}
	
	@RequestMapping( method = RequestMethod.GET)
    @ResponseBody
    public String currentUsername(String usernameORrole) {
		if(usernameORrole.equals("username")) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			return username;
		}else {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			System.out.println("Principal : "+authentication.getPrincipal());
			Collection<? extends GrantedAuthority> userrole = authentication.getAuthorities();
			System.out.println("userRole : "+userrole.toString());
			return userrole.toString();
		}
		
    }
}

