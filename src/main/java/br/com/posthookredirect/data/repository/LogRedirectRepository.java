package br.com.posthookredirect.data.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.posthookredirect.data.model.LogRedirectBean;

public interface LogRedirectRepository extends CrudRepository<LogRedirectBean, Long>{

}
