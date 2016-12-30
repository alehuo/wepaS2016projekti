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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * Kehitysympäristön konfiguraatio
 *
 * @author alehuo
 */
@Configuration
@Profile("production")
@EnableWebSecurity
@EnableCaching
public class ProductionConfiguration extends WebMvcConfigurerAdapter {

    /**
     * Resurssien "mappaus"
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        super.addResourceHandlers(registry);
    }

    /**
     * Heroku -palvelussa käytetään PostgreSQL -tietokantaa tiedon
     * tallentamiseen. Tämä metodi hakee herokusta tietokantayhteyden
     * mahdollistavat ympäristömuuttujat.
     *
     * @return @throws URISyntaxException
     */
    @Bean
    public BasicDataSource dataSource() throws URISyntaxException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

        //Autocommit pois päältä
        basicDataSource.setDefaultAutoCommit(false);

        return basicDataSource;
    }

    /**
     * Lokalisaatiotiedostojen lataaminen
     *
     * @return
     */
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:lang");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * Lokalisaatioevästeen asetus
     *
     * Oletusarvoinen lokalisaatio: fi_FI
     *
     * @return
     */
    @Bean
    public CookieLocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        Locale finnishLocale = new Locale.Builder().setLanguage("fi").setRegion("FI").build();
        localeResolver.setDefaultLocale(finnishLocale);
        localeResolver.setCookieName("locale");
        //Eväste vanhenee tunnissa
        localeResolver.setCookieMaxAge(3600);
        return localeResolver;
    }

    /**
     * Tämän avulla voidaan asettaa sivuston kieli lang GET -parametrillä.
     * Esim. http://localhost:8080?lang=fi
     * 
     * @return 
     */
    @Bean
    public LocaleChangeInterceptor localeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    /**
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeInterceptor());
    }

}
