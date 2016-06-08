package main.java.com.fang.service.impl;

import org.springframework.stereotype.Service;

import main.java.com.fang.service.UserInfoService;
@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Override
	public void printHello() {
		System.out.println("Hello World");
	}

}
