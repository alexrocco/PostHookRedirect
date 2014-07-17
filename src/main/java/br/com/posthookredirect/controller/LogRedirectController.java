package br.com.posthookredirect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.posthookredirect.data.repository.LogRedirectRepository;

@Controller
@RequestMapping(value = "/logredirect")
public class LogRedirectController {
	
	@Autowired
	private LogRedirectRepository logRedirectRepository;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listLogRedirects() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("logredirects", logRedirectRepository.findAll());
		modelAndView.setViewName("listLogRedirects");
		
		return modelAndView;
	}
}
