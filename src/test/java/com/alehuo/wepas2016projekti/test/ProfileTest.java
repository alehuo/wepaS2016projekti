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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author alehuo
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebAppConfiguration
public class ProfileTest extends FluentTest {

    public WebDriver webDriver = new HtmlUnitDriver();

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @LocalServerPort
    @Value("${local.server.port}")
    private Integer port;

    @Test
    public void profiiliSivunSelaaminenToimii1() throws Exception {
        //Aiempi testi testaa jo siirtymisen kirjautumissivulle
        goTo("http://localhost:" + port);

        assertTrue(pageSource().contains("Kirjaudu sisään"));

        fill(find("#username")).with("admin");
        fill(find("#passwd")).with("admin");
        submit(find("form").first());

        assertTrue(pageSource().contains("Syöte"));

        goTo("http://localhost:" + port + "/profile/user");
//        Thread.sleep(Integer.MAX_VALUE);
        assertTrue(pageSource().contains("Käyttäjän"));
        assertTrue(pageSource().contains("user"));
        
        goTo("http://localhost:" + port + "/profile/user2");
        assertTrue(pageSource().contains("Profiilia ei löydy"));
    }
}
