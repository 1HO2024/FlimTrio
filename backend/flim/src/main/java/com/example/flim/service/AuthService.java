package com.example.flim.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.flim.dto.UserDTO;
import com.example.flim.mapper.UserMapper;

@Service
public class AuthService implements UserDetailsService {

	@Autowired
	private UserMapper userMapper;
	private PasswordEncoder passwordEncoder;
	 
	//회원 가입
	public void registerUser(UserDTO userDTO) {
		 // 비밀번호를 BCrypt로 암호화
	    String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt());

	    // 암호화된 비밀번호를 UserDTO에 설정
	    userDTO.setPassword(hashedPassword);
	    
		userMapper.insertUser(userDTO);
	}
	
	
	public String getNickname(String email) {
	 String nickname = userMapper.getNickname(email);
		return nickname;
	}

	public String getPhonenumber(String email) {
	 String phoneNumber = userMapper.getPhonenumber(email);
		return phoneNumber;
	}
	
	
	
	 @Override
	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	        UserDTO userDTO = userMapper.findByEmail(email); // 이메일로 조회
	        if (userDTO == null) {
	            throw new UsernameNotFoundException("User not found with email: " + email);
	        }
	        // 사용자 정보를 UserDetails로 변환하여 리턴
	        return User.builder()
	                .username(userDTO.getEmail()) 
	                .password(userDTO.getPassword())  
	                .roles("USER")  // 예시로 USER 역할 설정
	                .build();
	    }
	 
	  // 이메일과 비밀번호로 인증
    public boolean authenticateUser(String email, String password) {
        // 이메일로 사용자 조회
        UserDTO user = userMapper.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return true;
        }
        return false;
    }


	
}