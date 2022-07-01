package com.meqdad.springboot.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.meqdad.springboot.web.model.Todo;
import com.meqdad.springboot.web.service.TodoService;

@Controller
public class TodoController {

    @Autowired
    TodoService todoService;
    
    @InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}

    @RequestMapping(value = "/list-todos", method = RequestMethod.GET)
    public String showTodos(ModelMap model) {
        String firstName = getLoggedInUserName(model);
        model.put("todos", todoService.retrieveTodos(firstName));

        return "list-todos";
    }

	private String getLoggedInUserName(ModelMap model) {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		if (principal instanceof UserDetails)
			return ((UserDetails) principal).getUsername();

		return principal.toString();
	}

    @RequestMapping(value = "/add-todo", method = RequestMethod.GET)
    public String showAddTodoPage(ModelMap model) {
        model.addAttribute("todo", new Todo(0, getLoggedInUserName(model), "Default Desc", new Date(), false));
        return "todo";
    }


    @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
    public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result) {

        if (result.hasErrors()) {
            return "todo";
        }
        String firstName = getLoggedInUserName(model);
        todoService.addTodo(firstName, todo.getDesc(), todo.getTargetDate(), false);

        return "redirect:/list-todos";
    }

    @RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
    public String deleteTodo(@RequestParam int id) {
        todoService.deleteTodo(id);
        return "redirect:/list-todos";
    }

    @RequestMapping(value = "/update-todo", method = RequestMethod.GET)
    public String showUpdatedTodoPage(@RequestParam int id, ModelMap model) {
        Todo todo = todoService.retrieveTodo(id);
        model.put("todo", todo);
        return "todo";
    }

    @RequestMapping(value = "/update-todo", method = RequestMethod.POST)
    public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {

        if (result.hasErrors()) {
            return "todo";
        }
        todo.setUser(getLoggedInUserName(model));
        todoService.updateTodo(todo);
        return "redirect:/list-todos";
    }

}
