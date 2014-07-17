package br.com.posthookredirect.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.posthookredirect.data.model.RouteBean;
import br.com.posthookredirect.data.repository.RouteRepository;

@Controller
@RequestMapping(value = "/route")
public class RouteController {
	
	@Autowired
	private RouteRepository routeRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listRoutes() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("routes", routeRepository.findAll());
		modelAndView.setViewName("listRoutes");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("id") Long id) {
		routeRepository.delete(id);
		return listRoutes();
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("routeBean", new RouteBean());
		modelAndView.setViewName("addEditRoute");
		return modelAndView;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute @Valid RouteBean routeBean, BindingResult result) {
		if (result.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("routeBean", routeBean);
			modelAndView.setViewName("addEditRoute");
			return modelAndView;
		} else {
			routeRepository.save(routeBean);
			return listRoutes();
		}
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam("id") Long id) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("routeBean", routeRepository.findOne(id));
		modelAndView.setViewName("addEditRoute");
		return modelAndView;
	}

}
