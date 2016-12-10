/*
 * Copyright (C) 2016 Pivotal Software, Inc.
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
package com.alehuo.wepas2016projekti.test;

import com.alehuo.wepas2016projekti.CustomHtmlUnitDriver;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.fluentlenium.adapter.FluentTest;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Jusaa
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SearchTest extends FluentTest {
    
    public WebDriver webDriver = new CustomHtmlUnitDriver(BrowserVersion.BEST_SUPPORTED, true);
    
    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }
    
    @LocalServerPort
    private Integer port;
    
    
    @Test
    public void hakuToimii() throws Exception{
        goTo("http://localhost:" + port);
        
        fill(find("#username")).with("admin");
        fill(find("#passwd")).with("admin");
        submit(find("#loginForm"));
        
        webDriver.findElement(By.id("haku")).click();
        assertTrue("\nError: ei löydy 'Hae käyttäjiä' -tekstiä \n" + pageSource() + "\n", pageSource().contains("Hae käyttäjiä"));
        
        fill(find("#usrname")).with("user");
        submit(find("#searchForm")); 
        System.out.println(pageSource());
        assertTrue("\nError: ei löydy 'href=\"/profile/user\"' -tekstiä \n" + pageSource() + "\n", pageSource().contains("href=\"/profile/user\""));
        
        fill(find("#usrname")).with("eiuser");
        submit(find("#searchForm"));
        
        assertFalse("\nError: löytyy 'href=\"/profile/user\"' -teksti \n" + pageSource() + "\n", pageSource().contains("href=\"/profile/user\""));
    }
}
