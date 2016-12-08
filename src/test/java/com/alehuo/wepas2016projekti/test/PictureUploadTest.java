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

import com.alehuo.wepas2016projekti.CustomHtmlUnitDriver;
import com.alehuo.wepas2016projekti.domain.Image;
import com.alehuo.wepas2016projekti.domain.UserAccount;
import com.alehuo.wepas2016projekti.service.ImageService;
import com.alehuo.wepas2016projekti.service.InitService;
import com.alehuo.wepas2016projekti.service.UserService;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import java.util.List;
import java.util.UUID;
import org.fluentlenium.adapter.FluentTest;
import org.jsoup.Jsoup;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author alehuo
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PictureUploadTest extends FluentTest {

    public HtmlUnitDriver webDriver = new CustomHtmlUnitDriver(BrowserVersion.BEST_SUPPORTED, true);

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    @Autowired
    private InitService initService;

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @LocalServerPort
    private Integer port;

    @Test
//    @Ignore
    public void uudenKuvanJakaminenToimii() {

//        initService.resetApplicationState();

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
    public void kuvanTykkaaminenToimii() throws InterruptedException {

        //Nollaa tila
        initService.resetApplicationState();

        //Etusivu
        goTo("http://localhost:" + port);

        assertTrue(pageSource().contains("Kirjaudu sisään"));

        //admin -tunnuksilla sisään
        fill(find("#username")).with("admin");
        fill(find("#passwd")).with("admin");
        //Lähetä lomake
        submit(find("form").first());

        //Nuku vähän aikaa
        Thread.sleep(500);

        //Nyt ollaan etusivulla
        assertTrue(pageSource().contains("Syöte"));


        //Hae käyttäjätili ja sen kuvat
        UserAccount u = userService.getUserByUsername("admin");
        List<Image> images = imageService.findAllByUserAccount(u);

        System.out.println(images.size());
        assertTrue(images.size() == 5);

        System.out.println("likeImage('" + images.get(0).getUuid() + "')");

        //Suorita JavaScript -funktio jolla tykätään kuvasta
        ((JavascriptExecutor) webDriver).executeScript("likeImage('" + images.get(0).getUuid() + "')");

        //Nuku vähän aikaa
        Thread.sleep(500);

        //Päivitä sivu varmuuden vuoksi (Tykkäyksen tulisi säilyä päivityksen yli)
        webDriver.navigate().refresh();

        //Nyt ollaan etusivulla, tarkistetaan että tykkäys rekisteröityi onnistuneesti
        //Käytetään Jsoup -kirjastoa jotta saadaan pelkkä teksti sivulta.
        String parsedPageSource = Jsoup.parse(pageSource()).text();
        assertTrue(parsedPageSource.contains("1 tykkäystä"));
    }
}
