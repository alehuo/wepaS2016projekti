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

import org.fluentlenium.adapter.FluentTest;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Jusaa
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegisterTest extends FluentTest {

    public WebDriver webDriver = new HtmlUnitDriver();

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @LocalServerPort
    private Integer port;

    @Test
    public void rekisteroityminenToimii() throws Exception {
        goTo("http://localhost:" + port);

        webDriver.findElement(By.name("register")).click();
        
        assertTrue(pageSource().contains("Rekisteröidy"));
        
        fill(find("#username")).with("matti");
        fill(find("#passwd")).with("meikalainen");
        fill(find("#email")).with("matti.meikalainen@localhost.fi");
        submit(find("form").first());
        
        assertTrue(pageSource().contains("Kirjaudu sisään"));
        
        fill(find("#username")).with("matti");
        fill(find("#passwd")).with("meikalainen");
        submit(find("form").first());
        
        assertTrue(pageSource().contains("Syöte"));

    }
}
