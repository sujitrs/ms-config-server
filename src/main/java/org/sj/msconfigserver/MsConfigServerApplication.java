package org.sj.msconfigserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@SpringBootApplication
@EnableConfigServer
@EnableAutoConfiguration
@EnableResourceServer
@EnableWebSecurity
public class MsConfigServerApplication extends WebSecurityConfigurerAdapter {
	
	/*
	 * @Value("${oauth2ServerCheckTokenUri}") private String uri;
	 */
	
	public static void main(String[] args) {
		SpringApplication.run(MsConfigServerApplication.class, args);
	}

	
	/*
	 * @Bean public ResourceServerTokenServices tokenServices() {
	 * RemoteTokenServices tokenServices = new RemoteTokenServices();
	 * tokenServices.setClientId("config-server");
	 * tokenServices.setClientSecret("password");
	 * tokenServices.setCheckTokenEndpointUrl(uri); return tokenServices; }
	 * 
	 * @Override public AuthenticationManager authenticationManagerBean() throws
	 * Exception { OAuth2AuthenticationManager authenticationManager = new
	 * OAuth2AuthenticationManager();
	 * authenticationManager.setTokenServices(tokenServices()); return
	 * authenticationManager; }
	 */
}

