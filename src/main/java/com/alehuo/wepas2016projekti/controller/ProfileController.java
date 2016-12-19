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
import com.alehuo.wepas2016projekti.domain.UserAccount;
import com.alehuo.wepas2016projekti.service.ImageService;
import com.alehuo.wepas2016projekti.service.UserService;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author alehuo
 */
@Controller
public class ProfileController {


    private static final Logger LOG = Logger.getLogger(ProfileController.class.getName());
    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    /**
     *
     * @param a
     * @param username
     * @param m
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/profile/{username}")
    public String viewProfile(Authentication a, @PathVariable String username, Model m) throws UnsupportedEncodingException {
        UserAccount loggedInUser = userService.getUserByUsername(a.getName());
        m.addAttribute("user", loggedInUser);
        String profileUsername = URLDecoder.decode(username, "UTF-8");
        UserAccount u = userService.getUserByUsername(profileUsername);
        m.addAttribute("userProfile", u);
        if (u != null) {
            List<Image> images = imageService.findAllByUserAccount(u);
            m.addAttribute("userImages", images);
        }

        LOG.log(Level.INFO, "Kayttaja ''{0}'' selasi kayttajan ''{1}'' profiilia.", new Object[]{a.getName(), username});
        return "profile";
    }

    /**
     *
     * @param a
     * @param uuid
     * @param m
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/photo/{uuid}")
    public String viewPhoto(Authentication a, @PathVariable String uuid, Model m) throws UnsupportedEncodingException {
        UserAccount loggedInUser = userService.getUserByUsername(a.getName());
        m.addAttribute("user", loggedInUser);
        m.addAttribute("image", imageService.findOneImageByUuid(uuid));

        LOG.log(Level.INFO, "Kayttaja ''{0}'' selasi kuvaa ''{1}''.", new Object[]{a.getName(), uuid});
        return "image";
    }

}
