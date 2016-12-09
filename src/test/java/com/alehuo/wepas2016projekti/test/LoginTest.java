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
import java.util.logging.Level;
import org.fluentlenium.adapter.FluentTest;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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

    public WebDriver webDriver = new CustomHtmlUnitDriver(BrowserVersion.BEST_SUPPORTED, true);

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @LocalServerPort
    private Integer port;

    @Test
    public void kirjautuminenSisaanJaUlosToimii() throws Exception {
        goTo("http://localhost:" + port);

        assertTrue("\nError: ei löydy 'Kirjaudu sisään' -tekstiä\n" + pageSource() + "\n", pageSource().contains("Kirjaudu sisään"));

        fill(find("#username")).with("admin");
        fill(find("#passwd")).with("admin");
        submit(find("#loginForm"));

        //Nuku vähän aikaa
        Thread.sleep(500);

        assertTrue("\nError: ei löydy 'syöte' -tekstiä\n" + pageSource() + "\n", pageSource().contains("Syöte"));

        webDriver.findElement(By.id("logout")).click();

        assertTrue("\nError: ei löydy 'Kirjaudu sisään' -tekstiä\n" + pageSource() + "\n", pageSource().contains("Kirjaudu sisään"));

        fill(find("#username")).with("vaaratunnus");
        fill(find("#passwd")).with("vaaratunnus");
        submit(find("form").first());

        assertTrue("\nError: ei löydy 'Kirjaudu sisään' -tekstiä\n" + pageSource() + "\n", pageSource().contains("Kirjaudu sisään"));
    }
}
