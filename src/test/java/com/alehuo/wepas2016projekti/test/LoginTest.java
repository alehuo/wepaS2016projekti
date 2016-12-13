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
import com.alehuo.wepas2016projekti.service.InitService;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.fluentlenium.adapter.FluentTest;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author alehuo
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTest extends FluentTest {

    @Autowired
    private InitService initService;

    public WebDriver webDriver = new CustomHtmlUnitDriver(BrowserVersion.getDefault(), true);

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @LocalServerPort
    private Integer port;

    @Test
    @Ignore
    public void kirjautuminenSisaanJaUlosToimii() throws Exception {

        goTo("http://localhost:" + port);

        assertTrue("\nError: ei löydy 'Kirjaudu sisään' -tekstiä\n" + pageSource() + "\n", pageSource().contains("Kirjaudu sisään"));

        //Nuku vähän aikaa
        Thread.sleep(4000);

        //        fill(find("#username")).with("admin");
//        fill(find("#password")).with("admin");
        webDriver.findElement(By.id("username")).sendKeys("admin");
        webDriver.findElement(By.id("password")).sendKeys("admin");

        System.out.println("USERNAME VALUE: " + find("#username").getValue());
        System.out.println("PASSWORD VALUE: " + find("#password").getValue());

        submit(find("#loginForm"));

        //Nuku vähän aikaa
        Thread.sleep(500);

        
        System.out.println("\n\n\n\n\n\n\n\n" + webDriver.getCurrentUrl() + "\n\n\n\n\n\n\n\n");
      
        assertFalse("Sovellus ei kirjaudu sisään / ohjaa oikein etusivulle", webDriver.getCurrentUrl().contains("/login"));

        assertTrue("\nError: ei löydy 'syöte' -tekstiä" + "\n" + pageSource() + "\n", pageSource().contains("Syöte"));

        webDriver.findElement(By.id("logout")).click();

        assertTrue("\nError: ei löydy 'Kirjaudu sisään' -tekstiä\n" + pageSource() + "\n", pageSource().contains("Kirjaudu sisään"));

        fill(find("#username")).with("vaaratunnus");
        fill(find("#password")).with("vaaratunnus");
        submit(find("form").first());

        assertTrue("\nError: ei löydy 'Kirjaudu sisään' -tekstiä\n" + pageSource() + "\n", pageSource().contains("Kirjaudu sisään"));
    }
}
