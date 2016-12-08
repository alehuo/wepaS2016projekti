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
package com.alehuo.wepas2016projekti.controller;

import com.alehuo.wepas2016projekti.domain.Image;
import com.alehuo.wepas2016projekti.domain.Role;
import com.alehuo.wepas2016projekti.domain.UserAccount;
import com.alehuo.wepas2016projekti.repository.CommentRepository;
import com.alehuo.wepas2016projekti.repository.ImageRepository;
import com.alehuo.wepas2016projekti.service.UserService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Oletuskontrolleri
 *
 * @author alehuo
 */
@Controller
public class DefaultController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private ImageRepository imageRepo;

    /**
     * Alustus
     */
    @PostConstruct
    public void init() {

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

    @RequestMapping("/")
    public String index(Model m) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        List<Image> images = imageRepo.findTop10ByOrderByIdDesc();
        UserAccount u = userService.getUserByUsername(username);
        m.addAttribute("user", u);
        m.addAttribute("images", images);
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        if (userService.getUserByUsername(username) == null) {
            userService.createNewUser(username, password, email, Role.USER);
        } else {
            return "redirect:/register?error";
        }
        return "redirect:/login";
    }
}
