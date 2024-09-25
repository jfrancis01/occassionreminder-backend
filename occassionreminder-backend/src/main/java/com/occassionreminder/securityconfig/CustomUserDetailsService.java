package com.occassionreminder.securityconfig;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.occassionreminder.model.User;
import com.occassionreminder.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	
	private final UserRepository userRepo;
	
	public CustomUserDetailsService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User customUser = userRepo.findByEmail(username);
			List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("user"));
			return new org.springframework.security.core.userdetails.User(customUser.getUserID(), customUser.getPassword(), authorities);
		}
		catch(UsernameNotFoundException unfe) {
			throw new UsernameNotFoundException(username + " Not found");
		}
	}

}
