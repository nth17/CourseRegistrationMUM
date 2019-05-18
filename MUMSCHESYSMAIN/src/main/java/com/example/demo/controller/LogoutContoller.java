package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.model.User;

@Controller
@SessionAttributes("userid")
public class LogoutContoller {
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public RedirectView processlogin( Model model, HttpSession session) 
	{
		session.invalidate();
		return new RedirectView("/loginnn");
	}
}
