package br.com.posthookredirect.controller;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.Locale;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.posthookredirect.data.model.LogRedirectBean;
import br.com.posthookredirect.data.model.RouteBean;
import br.com.posthookredirect.data.repository.LogRedirectRepository;
import br.com.posthookredirect.data.repository.RouteRepository;
import br.com.posthookredirect.enumeration.Status;

@Controller
@RequestMapping(value = "/post")
public class PostReceiverController {
	
	@Autowired
	private RouteRepository routeRepository;
	
	@Autowired
	private LogRedirectRepository logRedirectRepository;
	
	@Autowired
	private ApplicationContext context;

    @RequestMapping(headers = "Content-Type=application/json", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
	public void processJsonRepository(@RequestBody String requestBody) {
        
		LogRedirectBean logRedirectBean = new LogRedirectBean();
		logRedirectBean.setDateTime(new Date());
    	
		if (!requestBody.isEmpty()) {
        	JSONObject jsonObject = new JSONObject(requestBody);
            String gitBranchJson = jsonObject.getJSONArray("commits").getJSONObject(0).getString("branch");
    	
            Iterable<RouteBean> routes = routeRepository.findAll();
            
            RouteBean routeBean = new RouteBean();
            for (RouteBean route : routes) {
            	if (route.getGitBranch().equals(gitBranchJson)) {
            		routeBean = route;
            	}
            }
            
            RestTemplate restTemplate = new RestTemplate();
            String result = null;
            try {
            	URL url = new URL(routeBean.getUrlRedirect());
				result = restTemplate.getForObject(url.toURI(), String.class);
			} catch (RestClientException | URISyntaxException | MalformedURLException e) {
				logRedirectBean.setStatus(Status.FAILED);
				logRedirectBean.setErrorMsg(e.getMessage());
			} 
            
            if (result != null) {
                logRedirectBean.setStatus(Status.SUCCESS);
            }
            
            logRedirectBean.setRouteBean(routeBean);
            
    	} else {
    		logRedirectBean.setStatus(Status.FAILED);
    		logRedirectBean.setErrorMsg(context.getMessage("error.jsonPostNotReceived", null, Locale.getDefault()));
    	}
    	
    	logRedirectRepository.save(logRedirectBean);
    }
    
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void testHttpRequestGet() {
    	System.out.println("Worked!");
    }
}
