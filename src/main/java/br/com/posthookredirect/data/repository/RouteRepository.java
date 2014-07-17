package br.com.posthookredirect.data.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.posthookredirect.data.model.RouteBean;

public interface RouteRepository extends CrudRepository<RouteBean, Long>{

}
