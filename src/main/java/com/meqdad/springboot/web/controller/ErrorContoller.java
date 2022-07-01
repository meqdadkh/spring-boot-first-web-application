package com.meqdad.springboot.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Controller("error")
public class ErrorContoller {
	
	@ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception ex) {

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex.getStackTrace());
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }
}
