package com.meqdad.springboot.web.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.meqdad.springboot.web.service.TodoService;

@Controller
@SessionAttributes("firstName")
public class TodoController {

	@Autowired
	TodoService todoService;

	@RequestMapping(value = "/list-todos", method = RequestMethod.GET)
	public String showTodos(ModelMap model) {
		String firstName = (String) model.get("firstName");
		model.put("todos", todoService.retrieveTodos(firstName));

		return "list-todos";
	}

	@RequestMapping(value = "/add-todo", method = RequestMethod.GET)
	public String showAddTodoPage(ModelMap model) {
		return "todo";
	}
	
	 
	@RequestMapping(value="/add-todo", method=RequestMethod.POST) 
	public String addTodo(ModelMap model, @RequestParam String desc) {
		
		String firstName = (String)model.get("firstName");
		todoService.addTodo(firstName, desc, new Date(), false);
		
		return "redirect:/list-todos"; 
	}	
	 
}
