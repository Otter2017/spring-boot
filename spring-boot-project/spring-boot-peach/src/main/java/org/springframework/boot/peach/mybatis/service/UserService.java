package org.springframework.boot.peach.mybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.peach.mybatis.entity.User;
import org.springframework.boot.peach.mybatis.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;

	public boolean create(User user) {
		return userMapper.create(user);
	}

	public List<User> login(Map<String, String> params) {
		//TODO 为密码加密
		return userMapper.login(params);
	}
}