package com.bigdata2016.mysite3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bigdata2016.mysite3.dao.UserDao;
import com.bigdata2016.mysite3.vo.UserVo;

@Service
public class UserService {
	@Autowired
	UserDao userDao;
	
	public void join( UserVo vo ) {
		userDao.insert(vo);
	}
	
}
