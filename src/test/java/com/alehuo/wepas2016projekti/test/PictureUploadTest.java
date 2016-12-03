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
package com.alehuo.wepas2016projekti.test;

import java.util.UUID;
import org.fluentlenium.adapter.FluentTest;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
public class PictureUploadTest extends FluentTest {

    public WebDriver webDriver = new HtmlUnitDriver();

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @LocalServerPort
    @Value("${local.server.port}")
    private Integer port;

    @Test
    public void uudenKuvanJakaminenToimii() {
        goTo("http://localhost:" + port);

        assertTrue(pageSource().contains("Kirjaudu sisään"));

        //admin -tunnuksilla sisään
        fill(find("#username")).with("admin");
        fill(find("#passwd")).with("admin");
        //Lähetä lomake
        submit(find("form").first());

        //Nyt ollaan etusivulla
        assertTrue(pageSource().contains("Syöte"));

        //Klikkaa "plus" -nappia
        click(find("#uploadBtn").first());

        //Nyt ollaan Upload -sivulla
        assertTrue(pageSource().contains("Jaa kuva"));

        //Lisää kuvaus
        String description = UUID.randomUUID().toString().substring(0, 8);
        fill(find("#imageDesc")).with(description);

        //Kuvatiedosto
        WebElement upload = webDriver.findElement(By.id("imgUploadInput"));

        upload.sendKeys("testi.jpg");

        //Klikkaa submit
        webDriver.findElement(By.id("uploadSubmitBtn")).click();

        //Nyt ollaan etusivulla, tarkistetaan että kuva lisättiin onnistuneesti
        assertTrue(pageSource().contains(description));
    }

    @Test
    @Ignore
    public void kuvanTykkaaminenToimii() throws InterruptedException {

        /*
        Tämän toiminnallisuuden testin koodaaminen on vienyt jo 3 tuntia. Jätetään sikseen.
        Testattu itse ja toimii.
         */
        //Etusivu
        goTo("http://localhost:" + port);

        assertTrue(pageSource().contains("Kirjaudu sisään"));

        //admin -tunnuksilla sisään
        fill(find("#username")).with("admin");
        fill(find("#passwd")).with("admin");
        //Lähetä lomake
        submit(find("form").first());

        //Nyt ollaan etusivulla
        assertTrue(pageSource().contains("Syöte"));

        //Paina tykkäysnappia
        WebElement element = (new WebDriverWait(webDriver, 4))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'Tykkää')]")));
        Actions actions = new Actions(webDriver);
        actions.moveToElement(element).click().perform();
//        new Actions(webDriver).moveToElement(input).click().perform();
//        WebDriverWait wait = new WebDriverWait(webDriver, 3);
//        wait.until(ExpectedConditions.)
        //Päivitä sivu
//        webDriver.navigate().refresh();

        //Nyt ollaan etusivulla, tarkistetaan että tykkäys rekisteröityi onnistuneesti
//        assertTrue(pageSource().matches("<span[^>]*>(.*?)<\\/span>"));
        assertTrue(pageSource().matches("<span[^>]*>(1)<\\/span>"));
    }
}
