package edu.miu.cs590.SA.Ecommerce.config;

import edu.miu.cs590.SA.Ecommerce.constant.RestEndpoints;
import edu.miu.cs590.SA.Ecommerce.util.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {

    private JwtFilter jwtFilter;

    @Autowired
    public WebConfig(JwtFilter jwtFilter){
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(
                        "/"+RestEndpoints.ACCOUNT_PREFIX+RestEndpoints.REGISTER,
                        "/"+ RestEndpoints.ACCOUNT_PREFIX+RestEndpoints.LOGIN
                ).permitAll()
                .antMatchers(
                        "/"+ RestEndpoints.ACCOUNT_PREFIX,
                        "/"+ RestEndpoints.ACCOUNT_PREFIX+RestEndpoints.BY_ID,
                        "/"+ RestEndpoints.PRODUCT_PREFIX,
                        "/"+ RestEndpoints.PRODUCT_PREFIX+RestEndpoints.BY_ID,
                        "/"+ RestEndpoints.ORDER_PREFIX,
                        "/"+ RestEndpoints.ORDER_PREFIX+RestEndpoints.BY_ID,
                        "/"+ RestEndpoints.ORDER_PREFIX+RestEndpoints.BY_ID+RestEndpoints.PAY_POSTFIX
                ).authenticated().and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}