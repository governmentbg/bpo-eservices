package eu.ohim.sp.common.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.context.annotation.Configuration;


@EnableWebSecurity
@Configuration
public class SPWebSecurityConfigurerAdapter  extends  WebSecurityConfigurerAdapter {

    @Value("${security.client.csrf.protection.enabled}")
    private String csrfFilterEnabled;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if (!Boolean.parseBoolean(csrfFilterEnabled)){
            http.csrf().disable();
        }

    }
}
