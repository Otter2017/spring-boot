package org.springframework.boot.peach.mybatis.mapper;

import org.springframework.boot.peach.mybatis.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface UserMapper {
	boolean create(User user);

	List<User> login(Map<String, String> params);
}
