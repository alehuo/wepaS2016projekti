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
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
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

    public WebDriver webDriver = new HtmlUnitDriver();

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @LocalServerPort
    private Integer port;

    @Test
//    @Ignore
    public void kirjautuminenSisaanJaUlosToimii() throws Exception {
        goTo("http://localhost:" + port);

        assertTrue(pageSource().contains("Kirjaudu sisään"));

        fill(find("#username")).with("admin");
        fill(find("#passwd")).with("admin");
        submit(find("form").first());

        assertTrue(pageSource().contains("Syöte"));

        webDriver.findElement(By.xpath("//*[text()[contains(.,'Kirjaudu ulos')]]")).click();

        assertTrue(pageSource().contains("Kirjaudu sisään"));

        fill(find("#username")).with("vaaratunnus");
        fill(find("#passwd")).with("vaaratunnus");
        submit(find("form").first());

        assertTrue(pageSource().contains("Kirjaudu sisään"));
    }
}
