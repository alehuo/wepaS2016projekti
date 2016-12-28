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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author alehuo
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UiTest extends FluentTest {

    /**
     *
     */
    public WebDriver webDriver = new CustomHtmlUnitDriver(BrowserVersion.getDefault(), true);

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    @Autowired
    private InitService initService;

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @LocalServerPort
    private Integer port;

    /**
     *
     * @return
     */
    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    /**
     *
     */
    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
        initService.resetApplicationState();
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void kirjautuminenSisaanJaUlosToimii() throws Exception {

        goTo("http://localhost:" + port);

        assertTrue("\nError: ei löydy 'Kirjaudu sisään' -tekstiä\n" + pageSource() + "\n", pageSource().contains("Kirjaudu sisään"));

        //Nuku vähän aikaa
        Thread.sleep(800);

        //        fill(find("#username")).with("admin");
//        fill(find("#password")).with("admin");
        webDriver.findElement(By.id("username")).sendKeys("admin");
        webDriver.findElement(By.id("password")).sendKeys("admin");

        System.out.println("USERNAME VALUE: " + find("#username").getValue());
        System.out.println("PASSWORD VALUE: " + find("#password").getValue());

        submit(find("#loginForm"));

        //Nuku vähän aikaa
        Thread.sleep(500);

        assertFalse("Sovellus ei kirjaudu sisään / ohjaa oikein etusivulle", webDriver.getCurrentUrl().contains("/login"));

        assertTrue("\nError: ei löydy 'syöte' -tekstiä" + "\n" + pageSource() + "\n", pageSource().contains("Syöte"));

        webDriver.findElement(By.id("logout")).click();

        assertTrue("\nError: ei löydy 'Kirjaudu sisään' -tekstiä\n" + pageSource() + "\n", pageSource().contains("Kirjaudu sisään"));

        fill(find("#username")).with("vaaratunnus");
        fill(find("#password")).with("vaaratunnus");
        submit(find("form").first());

        assertTrue("\nError: ei löydy 'Kirjaudu sisään' -tekstiä\n" + pageSource() + "\n", pageSource().contains("Kirjaudu sisään"));
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void rekisteroityminenToimii() throws Exception {
        goTo("http://localhost:" + port);

        webDriver.findElement(By.name("register")).click();

        assertTrue("\nError: ei löydy 'Rekisteröidy' -tekstiä\n" + pageSource() + "\n", pageSource().contains("Rekisteröidy"));

        fill(find("#username")).with("matti");
        fill(find("#password")).with("meikalainen");
        fill(find("#email")).with("matti.meikalainen@localhost.fi");
        submit(find("#registerForm"));

        Thread.sleep(500);

        assertTrue("\nError: ei löydy 'Kirjaudu sisään' -tekstiä\n" + pageSource() + "\n", pageSource().contains("Kirjaudu sisään"));

        webDriver.findElement(By.name("register")).click();

        assertTrue("\nError: ei löydy 'Rekisteröidy' -tekstiä\n" + pageSource() + "\n", pageSource().contains("Rekisteröidy"));

        fill(find("#username")).with("admin");
        fill(find("#password")).with("meikalainen");
        fill(find("#email")).with("matti.meikalainen@localhost.fi");
        submit(find("#registerForm"));

        Thread.sleep(500);

        assertTrue("\nError: ei löydy 'Käyttäjätunnus on joko varattu tai virheellisen kokoinen, sen on oltava 4-25 merkkiä pitkä.' -tekstiä\n" + pageSource() + "\n", pageSource().contains("Käyttäjätunnus on joko varattu tai virheellisen kokoinen, sen on oltava 4-25 merkkiä pitkä."));

        goTo("http://localhost:" + port);

        fill(find("#username")).with("matti");
        fill(find("#password")).with("meikalainen");
        submit(find("#loginForm"));

        Thread.sleep(500);

        assertTrue("\nError: ei löydy 'Syöte' -tekstiä\n" + pageSource() + "\n", pageSource().contains("Syöte"));

    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void profiiliSivunSelaaminenToimii1() throws Exception {

        //Varmistetaan että profiilia ei pääse selaamaan ilman että on kirjautunut sisään
        goTo("http://localhost:" + port + "/profile/user");

        assertTrue(pageSource().contains("Kirjaudu sisään"));

        //Aiempi testi testaa jo siirtymisen kirjautumissivulle
        goTo("http://localhost:" + port);

        assertTrue(pageSource().contains("Kirjaudu sisään"));

        fill(find("#username")).with("admin");
        fill(find("#password")).with("admin");
        submit(find("#loginForm"));

        //Nuku vähän aikaa
        Thread.sleep(500);
//        System.out.println("\n\n\n\n\n\n\n\n" + pageSource() + "\n\n\n\n\n\n\n\n");
        assertTrue(pageSource().contains("Syöte"));

        goTo("http://localhost:" + port + "/profile/user");

        String parsedPageSource = Jsoup.parse(pageSource()).text();
        assertTrue(parsedPageSource.contains("Käyttäjän user jakamat kuvat"));

        goTo("http://localhost:" + port + "/profile/user2");
        assertTrue(pageSource().contains("Profiilia ei löydy"));

        webDriver.findElement(By.id("profiili")).click();
        parsedPageSource = Jsoup.parse(pageSource()).text();
        assertTrue(parsedPageSource.contains("Käyttäjän admin jakamat kuvat"));
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void hakuToimii() throws Exception {
        goTo("http://localhost:" + port);

        fill(find("#username")).with("admin");
        fill(find("#password")).with("admin");
        submit(find("#loginForm"));

        webDriver.findElement(By.id("haku")).click();
        assertTrue("\nError: ei löydy 'Hae käyttäjiä' -tekstiä \n" + pageSource() + "\n", pageSource().contains("Hae käyttäjiä"));

        fill(find("#username")).with("user");
        submit(find("#searchForm"));
//        System.out.println(pageSource());
        assertTrue("\nError: ei löydy 'href=\"/profile/user\"' -tekstiä \n" + pageSource() + "\n", pageSource().contains("href=\"/profile/user\""));

        fill(find("#username")).with("eiuser");
        submit(find("#searchForm"));

        assertFalse("\nError: löytyy 'href=\"/profile/user\"' -teksti \n" + pageSource() + "\n", pageSource().contains("href=\"/profile/user\""));
    }

    /**
     *
     */
    @Test
    public void uudenKuvanJakaminenToimii() {

        goTo("http://localhost:" + port);

        assertTrue("\nError: ei löydy 'Kirjaudu sisään' -tekstiä\n" + pageSource() + "\n", pageSource().contains("Kirjaudu sisään"));

        //admin -tunnuksilla sisään
        fill(find("#username")).with("admin");
        fill(find("#password")).with("admin");
//        webDriver.findElement(By.id("username")).sendKeys("admin");
//        webDriver.findElement(By.id("password")).sendKeys("admin");

        System.out.println("USERNAME VALUE: " + find("#username").getValue());
        System.out.println("PASSWORD VALUE: " + find("#password").getValue());

        //Lähetä lomake
        submit(find("#loginForm"));

        //Nyt ollaan etusivulla
        System.out.println("\n\n\n\n\n\n\n\n" + pageSource() + "\n\n\n\n\n\n\n\n");
        assertTrue("\nError: ei löydy 'Syöte' -tekstiä \n" + pageSource() + "\n", pageSource().contains("Syöte"));

        //Klikkaa "plus" -nappia
        click(find("#uploadBtn").first());

        //Nyt ollaan Upload -sivulla
        assertTrue("\nError: ei löydy 'Jaa kuva' -tekstiä \n" + pageSource() + "\n", pageSource().contains("Jaa kuva"));

        //Lisää kuvaus
        String description = UUID.randomUUID().toString().substring(0, 8);
        fill(find("#imageDesc")).with(description);

        //Kuvatiedosto
        WebElement upload = webDriver.findElement(By.id("imgUploadInput"));

        upload.sendKeys("testi.jpg");

        //Klikkaa submit
        webDriver.findElement(By.id("uploadSubmitBtn")).click();

        //Nyt ollaan etusivulla, tarkistetaan että kuva lisättiin onnistuneesti
        assertTrue("\nError: ei löydy ladatun kuvan kuvausta \n" + pageSource() + "\n", pageSource().contains(description));

        assertEquals("\nError: kuvia ei ole listassa kuusi \n" + pageSource() + "\n", 6, imageService.findAllImages().size());
    }

    /**
     *
     * @throws InterruptedException
     */
    @Test
    public void kuvanTykkaaminenJaKommentointiToimii() throws InterruptedException {

        //Etusivu
        goTo("http://localhost:" + port);

        assertTrue("\nError: ei löydy 'Kirjaudu sisään' -tekstiä\n" + pageSource() + "\n", pageSource().contains("Kirjaudu sisään"));

        //admin -tunnuksilla sisään
        fill(find("#username")).with("admin");
        fill(find("#password")).with("admin");
        //Lähetä lomake
        submit(find("#loginForm"));

        //Nuku vähän aikaa
        Thread.sleep(500);

        //Nyt ollaan etusivulla
        assertTrue("\nError: ei löydy 'Syöte' tekstiä\n" + pageSource() + "\n", pageSource().contains("Syöte"));

        //Hae käyttäjätili ja sen kuvat
        UserAccount u = userService.getUserByUsername("admin");
        List<Image> images = imageService.findAllByUserAccount(u);

//        System.out.println(images.size());
        assertTrue("\nError: kuvia ei ole listassa tarpeeksi\n", images.size() == 5);

        //Suorita JavaScript -funktio jolla tykätään kuvasta
        ((JavascriptExecutor) webDriver).executeScript("likeImage('" + images.get(0).getUuid() + "')");

        //Nuku vähän aikaa
        Thread.sleep(500);

        //Päivitä sivu varmuuden vuoksi (Tykkäyksen tulisi säilyä päivityksen yli)
        webDriver.navigate().refresh();

        //Nyt ollaan etusivulla, tarkistetaan että tykkäys rekisteröityi onnistuneesti
        //Käytetään Jsoup -kirjastoa jotta saadaan pelkkä teksti sivulta.
        String parsedPageSource = Jsoup.parse(pageSource()).text();
        assertTrue("\nError: kuvalle ei lisätty tykkäystä\n" + pageSource() + "\n", parsedPageSource.contains("1 tykkäystä"));

        //Suorita JavaScript -funktio jolla tykätään kuvasta
        ((JavascriptExecutor) webDriver).executeScript("likeImage('" + images.get(0).getUuid() + "')");

        //Nuku vähän aikaa
        Thread.sleep(500);

        //Päivitä sivu varmuuden vuoksi (Tykkäyksen tulisi säilyä päivityksen yli)
        webDriver.navigate().refresh();

        //Nyt ollaan etusivulla, tarkistetaan että tykkäys rekisteröityi onnistuneesti
        //Käytetään Jsoup -kirjastoa jotta saadaan pelkkä teksti sivulta.
        parsedPageSource = Jsoup.parse(pageSource()).text();
        assertTrue("\nError: kuvalle ei lisätty tykkäystä\n" + pageSource() + "\n", parsedPageSource.contains("0 tykkäystä"));

        for (int i = 1; i < 6; i++) {

            //Suorita JavaScript -funktio jolla avataan kommentointi-ikkuna
            ((JavascriptExecutor) webDriver).executeScript("createCommentModal('" + images.get(0).getUuid() + "')");

            //Nuku vähän aikaa
            Thread.sleep(500);

            ((JavascriptExecutor) webDriver).executeScript("$('#commentModal_" + images.get(0).getUuid() + "').modal('open');");

            //Nuku vähän aikaa
            Thread.sleep(500);

            //Etsi textarea
            assertTrue("\nError: Ei löydetty commentModalTextarea_" + images.get(0).getUuid() + "\n" + pageSource() + "\n", webDriver.findElement(By.id("commentModalTextarea_" + images.get(0).getUuid())).isDisplayed());

            //Kirjoita tekstiä
            webDriver.findElement(By.id("commentModalTextarea_" + images.get(0).getUuid())).sendKeys("HelloWorldTestiKommentti");

            //Lähetä kommentti
            webDriver.findElement(By.id("commentModalSubmitBtn_" + images.get(0).getUuid())).click();

            Thread.sleep(500);
            webDriver.navigate().refresh();

            assertTrue("\nError: Ei löydetty 'HelloWorldTestiKommentti' -tekstiä\n" + pageSource() + "\n",
                    pageSource().contains("HelloWorldTestiKommentti")
            );

            parsedPageSource = Jsoup.parse(pageSource()).text();

            assertTrue("\nError: kuvalle ei lisätty kommenttia\n" + pageSource() + "\n", parsedPageSource.contains(i + " kommenttia"));

        }
        Image i = imageService.findOneImageByUuid(images.get(0).getUuid());

        assertEquals("\nError: Kommentteja ei ole tasan viittä\n" + pageSource() + "\n", 5, i.getComments().size());
        assertEquals("\nError: kommenttia ei löytynyt sivulta\n" + pageSource() + "\n", "HelloWorldTestiKommentti", i.getComments().get(0).getBody());

    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void kuvanHakuToimii() throws Exception {

        //Etusivu
        goTo("http://localhost:" + port);

        assertTrue("\nError: ei löydy 'Kirjaudu sisään' -tekstiä\n" + pageSource() + "\n", pageSource().contains("Kirjaudu sisään"));

        //admin -tunnuksilla sisään
        fill(find("#username")).with("admin");
        fill(find("#password")).with("admin");
        //Lähetä lomake
        submit(find("#loginForm"));

        //Nuku vähän aikaa
        Thread.sleep(500);

        //Nyt ollaan etusivulla
        assertTrue("\nError: ei löydy 'Syöte' tekstiä\n" + pageSource() + "\n", pageSource().contains("Syöte"));

        List<Image> kuvat = imageService.findAllImages();

        for (Image image : kuvat) {
            assertEquals("ImageServicestä ei löydy kuvaa sen Id'n perusteella", image, imageService.findOneImageById(image.getId()));
            String imageUuid = image.getUuid();
            MvcResult res = mockMvc.perform(get("/img/" + imageUuid)).andReturn();
            assertEquals("Kuvan tyyppi on väärä: " + res.getResponse().getHeader("Content-Type"), image.getContentType(), res.getResponse().getHeader("Content-Type"));
            assertEquals("Kuvan koko on väärä: " + res.getResponse().getHeader("Content-Length"), image.getImageData().length, Integer.parseInt(res.getResponse().getHeader("Content-Length")));
            assertEquals("Kuvan ETag on väärä: " + res.getResponse().getHeader("ETag"), "\"" + imageUuid + "\"", res.getResponse().getHeader("ETag"));
        }
        
        //Poistetaan kaikki palvelun kuvat
        imageService.deleteAllImages();
        kuvat = imageService.findAllImages();
        assertEquals("Kuvia on vielä jäljellä poiston jälkeen", kuvat.size(), 0);
    }

}
// mm. mockMvc:n get- ja post-metodit
