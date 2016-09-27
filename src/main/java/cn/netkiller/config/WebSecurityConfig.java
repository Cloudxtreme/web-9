package cn.netkiller.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	public WebSecurityConfig() {
		// TODO Auto-generated constructor stub
	}

/*	@Override
	public void init(WebSecurity web) {
		web.ignoring().antMatchers("/noauth/**");
	}*/

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/ping","/v1/*/ping","/v1/public/**" ).permitAll()
		.anyRequest().authenticated()
		.and().rememberMe().and().httpBasic()
		.and().csrf().disable();
	}
	
/*	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
                withUser("admin").password("admin").roles("ADMIN");

    }*/
    

}
