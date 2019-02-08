package com.ozymern.spring.user.manager.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ozymern.spring.user.manager.services.security.LoginSuccesHandler;
import com.ozymern.spring.user.manager.services.security.SpringSecUserDetailsServiceImpl;


@EnableGlobalMethodSecurity(prePostEnabled = true)
// anotacion para habilitar la anotacione @secured                                                // (securedEnabled=true) y prePostEnabled=true @PreAuthorize
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private LoginSuccesHandler loginSuccesHandler;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SpringSecUserDetailsServiceImpl springSecurityConfig;


    // para las autorizaciones
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // permitir a todos
        http.authorizeRequests().antMatchers("/login", "/pages/**")
                .permitAll()
                // paginas a proteger
                /*
                 * .antMatchers("/ver/**").hasAnyRole("USER")
                 * .antMatchers("/uploads").hasAnyRole("USER")
                 * .antMatchers("/form/**").hasAnyRole("ADMIN")
                 * .antMatchers("/eliminar/**").hasAnyRole("ADMIN")
                 * .antMatchers("/factura/**").hasAnyRole("ADMIN")
                 */
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .usernameParameter("email")
                .loginPage("/login")
                .successHandler(loginSuccesHandler)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/error_403");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/vendors/**", "/static/**", "/docs/**", "/build/**", "/production/**", "/src/**", "/build/css/**", "/vendors/bootstrap/dist/**");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(springSecurityConfig).
                passwordEncoder(bCryptPasswordEncoder);

    }
}
