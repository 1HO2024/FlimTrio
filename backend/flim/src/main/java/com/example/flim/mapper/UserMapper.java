package com.example.flim.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.flim.dto.UserDTO;

@Mapper
public interface UserMapper {
	
        // 회원가입
		void insertUser(UserDTO userDTO);
		UserDTO findByEmail(String email);
		String getNickname(String email);
		String getPhonenumber(String email);
		UserDTO findUserForPass(UserDTO userDTO);
		UserDTO findUserByEmailPhone(UserDTO userDTO);
		void updateTempPassword(@Param("email") String email,
				                @Param("phoneNumber") String phoneNumber,
				                @Param("hashedTempPassword") String hashedTempPassword);
	}

