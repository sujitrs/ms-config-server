package org.sj.msconfigserver;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource({ "classpath:persistence.properties" })
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	 @Autowired
	    private Environment env;

    @Autowired
    private CustomAccessTokenConverter customAccessTokenConverter;

    @Override
    public void configure(final HttpSecurity http) throws Exception {
    	http
        .authorizeRequests()
        .antMatchers("/**").access("#oauth2.hasScope('config_read')")
        .anyRequest().authenticated();
        // @formatter:on            
    	    }

    @Override
    public void configure(final ResourceServerSecurityConfigurer config) {
        config.tokenServices(tokenServices());
    }

    @Bean
    public TokenStore tokenStore() {
       // return new JwtTokenStore(accessTokenConverter()); /* In case if JWT based tokens are to be used*/
    	return new JdbcTokenStore(dataSource());
    }
    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.pass"));
        return dataSource;
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setAccessTokenConverter(customAccessTokenConverter);

        converter.setSigningKey("123");
        // final Resource resource = new ClassPathResource("public.txt");
        // String publicKey = null;
        // try {
        // publicKey = IOUtils.toString(resource.getInputStream());
        // } catch (final IOException e) {
        // throw new RuntimeException(e);
        // }
        // converter.setVerifierKey(publicKey);
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }

}

