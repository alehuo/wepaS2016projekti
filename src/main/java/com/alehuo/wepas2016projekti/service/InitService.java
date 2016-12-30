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
package com.alehuo.wepas2016projekti.service;

import com.alehuo.wepas2016projekti.domain.Image;
import com.alehuo.wepas2016projekti.domain.Role;
import com.alehuo.wepas2016projekti.domain.UserAccount;
import com.alehuo.wepas2016projekti.repository.CommentRepository;
import com.alehuo.wepas2016projekti.repository.ImageRepository;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Käynnistyspalvelu
 *
 * @author alehuo
 */
@Service
public class InitService {

    /**
     * Käyttäjätilipalvelu
     */
    @Autowired
    private UserService userService;

    /**
     * Kommenttirepositorio
     */
    @Autowired
    private CommentRepository commentRepo;

    /**
     * Kuvarepositorio
     */
    @Autowired
    private ImageRepository imageRepo;

    //Kuvan leveys ja korkeus
    private final int widthHeight = 800;

    //Skaalataanko kuvat haluttuihin mittoihin?
    private final boolean scaleImages = false;

    /**
     * Tämä metodi nollaa sovelluksen tilan
     */
    @Transactional
    public void resetApplicationState() {

        //Poista kuvien kommenttiviittaukset
        for (Image i : imageRepo.findAll()) {
            i.getComments().clear();
        }

        //Poista käyttäjätilien kommenttiviittaukset
        for (UserAccount ua : userService.getAllUsers()) {
            ua.getComments().clear();
        }

        //Poista kommentit
        commentRepo.deleteAll();
        //Poista kuvat
        imageRepo.deleteAll();
        //Poista käyttäjätilit
        userService.deleteAllUsers();

        //Lisää "user" -käyttäjä
        if (userService.getUserByUsername("user") == null) {
            userService.createNewUser("user", "user", "user@localhost.com", Role.USER);
        }

        //Lisää "admin" -käyttäjä ja sille kuvia
        if (userService.getUserByUsername("admin") == null) {
            userService.createNewUser("admin", "admin", "admin@localhost.com", Role.ADMINISTRATOR);
            try {
                addImage("src/main/resources/kuvat/1.jpg", "image/jpg", "jpg", "Testikuva 1", "admin", scaleImages);
                addImage("src/main/resources/kuvat/2.jpg", "image/jpg", "jpg", "Testikuva 2", "admin", scaleImages);
                addImage("src/main/resources/kuvat/3.jpg", "image/jpg", "jpg", "Testikuva 3", "admin", scaleImages);
                addImage("src/main/resources/kuvat/4.jpg", "image/jpg", "jpg", "Testikuva 4", "admin", scaleImages);
                addImage("src/main/resources/kuvat/5.jpg", "image/jpg", "jpg", "Testikuva 5", "admin", scaleImages);
            } catch (IOException ex) {
                Logger.getLogger(InitService.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * Pienentää kuvan
     *
     * @param byteArray Kuvan data
     * @param type Kuvan tiedostotyyppi
     * @param widthHeight Leveys ja korkeus (Kuvat ovat neliön muotoisia)
     * @return Pienennetyn kuvan data
     */
    public byte[] resizeImage(byte[] byteArray, String type, int widthHeight) {
        BufferedImage resized;
        try {
            resized = Scalr.resize(ImageIO.read(new ByteArrayInputStream(byteArray)),
                    Scalr.Method.QUALITY,
                    Scalr.Mode.FIT_TO_HEIGHT,
                    widthHeight, widthHeight, Scalr.OP_ANTIALIAS);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resized, type, baos);
            return baos.toByteArray();
        } catch (IOException ex) {
            return null;
        }
    }

    /**
     * Lisää kuvan
     *
     * @param fPath Tiedoston sijainti
     * @param type Tyyppi
     * @param type2 Tyyppi 2
     * @param description Kuvaus
     * @param poster Lisääjä
     * @param resize Pienennetäänkö kuvaa vai ei?
     * @throws IOException
     */
    public void addImage(String fPath, String type, String type2, String description, String poster, boolean resize) throws IOException {
        Path path = Paths.get(fPath);
        Image i = new Image();
        i.setImageContentType(type);
        //Jos kuva halutaan pienentää
        if (resize) {
            i.setImageData(resizeImage(Files.readAllBytes(path), type2, widthHeight));
        } else {
            //Muuten, lisää kuva sellaisenaan
            i.setImageData(Files.readAllBytes(path));
        }
        i.setDescription(description);
        i.setImageOwner(userService.getUserByUsername(poster));
        imageRepo.save(i);
    }
}
