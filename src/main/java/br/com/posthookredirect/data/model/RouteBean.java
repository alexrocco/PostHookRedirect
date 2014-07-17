package br.com.posthookredirect.data.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "route")
public class RouteBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotEmpty(message = "Please insert a Git branch name.")
	private String gitBranch;
	
	@NotEmpty
	@URL(message = "Please insert a valid URL.")
	private String urlRedirect;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "routeBean")
	private Set<LogRedirectBean> logRedirects = new HashSet<LogRedirectBean>();
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGitBranch() {
		return gitBranch;
	}
	public void setGitBranch(String gitBranch) {
		this.gitBranch = gitBranch;
	}
	public String getUrlRedirect() {
		return urlRedirect;
	}
	public void setUrlRedirect(String urlRedirect) {
		this.urlRedirect = urlRedirect;
	}
	public Set<LogRedirectBean> getLogRedirects() {
		return logRedirects;
	}
	public void setLogRedirects(Set<LogRedirectBean> logRedirects) {
		this.logRedirects = logRedirects;
	}
}
