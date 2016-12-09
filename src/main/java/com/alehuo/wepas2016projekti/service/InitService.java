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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public void resetApplicationState() {
        commentRepo.deleteAll();
        imageRepo.deleteAll();
        userService.deleteAllUsers();

        userService.createNewUser("admin", "admin", "admin@localhost.com", Role.ADMINISTRATOR);
        userService.createNewUser("user", "user", "user@localhost.com", Role.USER);

        try {
            if (imageRepo.findAllByImageOwnerOrderByIdDesc(userService.getUserByUsername("admin")).size() == 0) {
                Path path = Paths.get("src/main/resources/kuvat/1.jpg");
                Image i = new Image();
                i.setImageContentType("image/jpg");
                i.setImageData(Files.readAllBytes(path));
                i.setDescription("Testikuva 1");
                i.setImageOwner(userService.getUserByUsername("admin"));
                imageRepo.save(i);

                path = Paths.get("src/main/resources/kuvat/2.jpg");
                i = new Image();
                i.setImageContentType("image/jpg");
                i.setImageData(Files.readAllBytes(path));
                i.setDescription("Testikuva 2");
                i.setImageOwner(userService.getUserByUsername("admin"));
                imageRepo.save(i);

                path = Paths.get("src/main/resources/kuvat/3.jpg");
                i = new Image();
                i.setImageContentType("image/jpg");
                i.setImageData(Files.readAllBytes(path));
                i.setDescription("Testikuva 3");
                i.setImageOwner(userService.getUserByUsername("admin"));
                imageRepo.save(i);

                path = Paths.get("src/main/resources/kuvat/4.jpg");
                i = new Image();
                i.setImageContentType("image/jpg");
                i.setImageData(Files.readAllBytes(path));
                i.setDescription("Testikuva 4");
                i.setImageOwner(userService.getUserByUsername("admin"));
                imageRepo.save(i);

                path = Paths.get("src/main/resources/kuvat/5.jpg");
                i = new Image();
                i.setImageContentType("image/jpg");
                i.setImageData(Files.readAllBytes(path));
                i.setDescription("Testikuva 5");
                i.setImageOwner(userService.getUserByUsername("admin"));
                imageRepo.save(i);
            }

        } catch (IOException ex) {
            Logger.getLogger(DefaultController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
