package com.sachin.learning.sample1.sampleservice;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sachin.learning.util.SampleUtilities;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class SampleService {
	
	@RequestMapping(path="/getMessage", method=RequestMethod.GET)
	public String getMessage() {
		return "Sample Message";
	}
	
	@RequestMapping(path="/getSimpleMessage")
	public SimpleMessage getSimpleMessage() {
		return new SimpleMessage("Simple Message Bean");
	}
	
	@RequestMapping(path="/getSimpleUserMessage/{name}")
	public SimpleMessage getSimpleMessageForUser(@PathVariable String name) {
		return new SimpleMessage("Hello " + name);
	}
	
	@RequestMapping(path="/getUserAgent", method=RequestMethod.GET)
	public String getUserAgent(@RequestHeader(value = "User-Agent") String userAgent, HttpServletRequest request) {
		return "User Agent : " + userAgent + "<br> Browser : " + userAgent+ "<br> User IP : " + request.getRemoteAddr() + "<br><br> Client Info : + " + new SampleUtilities().printClientInfo(request);
	}

}
