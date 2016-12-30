/*
 * Copyright (C) 2016 alehuo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.alehuo.wepas2016projekti.configuration;

/**
 *
 * @author alehuo
 */
import com.alehuo.wepas2016projekti.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Kehitysympäristön turvallisuuskonfiguraatio
 * 
 * @author Aleksi
 */
@Profile({"default", "development"})
@Configuration
@EnableWebSecurity
public class DevelopmentSecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * Käyttäjätietojen palvelu
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /**
     * Konfiguroi Spring Security -lisäosan
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin();
        //Sallitaan pääsy resurssikansioihin sekä H2 -konsoliin
//        http.csrf().disable();
        //Kirjautumislokame löytyy GET -reitistä /login
        http.authorizeRequests()
                .antMatchers("/delete/**").hasAuthority(Role.ADMINISTRATOR.toString())  
                .antMatchers("/js/**", "/css/**", "/manifest.json", "/resources/**", "/h2-console/**", "/register", "/fi_FI.png", "/en_EN.png", "/login**", "/fonts/roboto/**").permitAll().anyRequest().permitAll()
                .anyRequest().authenticated().and()
                .formLogin().defaultSuccessUrl("/", true).loginPage("/login").permitAll().and()
                .logout().permitAll();

    }

    /**
     * Aseta käyttäjätietojen hakuun käytettävä palvelu
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * Salasanaenkooderi
     * 
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
