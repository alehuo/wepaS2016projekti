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

import com.alehuo.wepas2016projekti.controller.DefaultController;
import com.alehuo.wepas2016projekti.domain.Image;
import com.alehuo.wepas2016projekti.domain.Role;
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
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author alehuo
 */
@Service
public class InitService {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private ImageRepository imageRepo;

    //Kuvan leveys ja korkeus
    private int widthHeight = 800;

    private boolean scaleImages = false;

    public void resetApplicationState() {
        commentRepo.deleteAll();
        imageRepo.deleteAll();
        userService.deleteAllUsers();

        userService.createNewUser("admin", "admin", "admin@localhost.com", Role.ADMINISTRATOR);
        userService.createNewUser("user", "user", "user@localhost.com", Role.USER);
        try {
            if (imageRepo.findAllByImageOwnerOrderByIdDesc(userService.getUserByUsername("admin")).isEmpty()) {
                addImage("src/main/resources/kuvat/1.jpg", "image/jpg", "Testikuva 1", "admin", scaleImages);
                addImage("src/main/resources/kuvat/2.jpg", "image/jpg", "Testikuva 2", "admin", scaleImages);
                addImage("src/main/resources/kuvat/3.jpg", "image/jpg", "Testikuva 3", "admin", scaleImages);
                addImage("src/main/resources/kuvat/4.jpg", "image/jpg", "Testikuva 4", "admin", scaleImages);
                addImage("src/main/resources/kuvat/5.jpg", "image/jpg", "Testikuva 5", "admin", scaleImages);
            }

        } catch (IOException ex) {
            Logger.getLogger(DefaultController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public byte[] resizeImage(byte[] byteArray, String type, int widthHeight) {
        BufferedImage resized;
        try {
            resized = Scalr.resize(ImageIO.read(new ByteArrayInputStream(byteArray)),
                    Scalr.Method.QUALITY,
                    Scalr.Mode.FIT_TO_HEIGHT,
                    widthHeight, widthHeight, Scalr.OP_ANTIALIAS);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resized, "jpg", baos);
            return baos.toByteArray();
        } catch (IOException ex) {
            return null;
        }
    }

    public void addImage(String fPath, String type, String description, String poster, boolean resize) throws IOException {
        Path path = Paths.get(fPath);
        Image i = new Image();
        i.setImageContentType(type);
        if (resize) {
            i.setImageData(resizeImage(Files.readAllBytes(path), type, widthHeight));
        } else {
            i.setImageData(Files.readAllBytes(path));
        }
        i.setDescription("Testikuva 1");
        i.setImageOwner(userService.getUserByUsername("admin"));
        imageRepo.save(i);
    }
}
