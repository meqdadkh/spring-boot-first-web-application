package com.meqdad.springboot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.meqdad.springboot.web.service.LoginService;

@Controller
@SessionAttributes("firstName")
public class LoginController {
	
	@Autowired
    private LoginService service;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String showLoginPage(ModelMap model) {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public String showWelcomePage(ModelMap model, @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String password) {

        boolean isValidUser = service.validateUser(firstName, password);

        if (isValidUser) {
            model.put("firstName", firstName);
            model.put("lastName", lastName);
            return "welcome";
        } else {
            model.put("errorMessage", "Invalid Credentials!!");
            return "login";
        }
    }

}
