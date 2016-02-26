package com.bigdata2016.mysite3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bigdata2016.mysite3.service.UserService;

@Controller
@RequestMapping( "/user" )
public class UserController {

	@Autowired
	UserService userService;
}
