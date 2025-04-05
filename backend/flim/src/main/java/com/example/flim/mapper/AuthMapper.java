package com.example.flim.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.flim.dto.UserDTO;

@Mapper
public interface AuthMapper {
	
        // 회원가입
		void insertUser(UserDTO userDTO);
		
		//회원정보 조회
		UserDTO findByEmail(String email);
		String getNickname(String email);
		String getPhonenumber(String email);
		
		//비밀번호 찾기
		UserDTO findUserForPass(UserDTO userDTO);
		UserDTO findUserByEmailPhone(UserDTO userDTO);
		void updateTempPassword(@Param("email") String email,
				                @Param("phoneNumber") String phoneNumber,
				                @Param("hashedTempPassword") String hashedTempPassword);
		
		//회원정보 수정
		void updateProfile(@Param("email")String email, 
				           @Param("nickname")String nickname, 
				           @Param("hasedupdatePass")String hasedupdatePass);
		//useridx 추출
		int getUserIdx(String email);
	}

