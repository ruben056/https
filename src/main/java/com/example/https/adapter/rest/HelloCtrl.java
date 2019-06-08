package com.example.https.adapter.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class HelloCtrl {

	@RequestMapping(path = "/hello")
	public String hello() {
		return "Hello";
	}
}
