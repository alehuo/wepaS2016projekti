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
package com.alehuo.wepas2016projekti.controller;

import com.alehuo.wepas2016projekti.domain.Image;
import com.alehuo.wepas2016projekti.domain.UserAccount;
import com.alehuo.wepas2016projekti.service.ImageService;
import com.alehuo.wepas2016projekti.service.UserService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author alehuo
 */
@Controller
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

//    @RequestMapping(method = RequestMethod.GET)
//    public String upload(Model m) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = auth.getName();
//        UserAccount u = userService.getUserByUsername(username);
//        m.addAttribute("user", u);
//        return "upload";
//    }
    @RequestMapping(method = RequestMethod.POST)
    public String processUpload(@RequestParam("imageFile") MultipartFile file, @RequestParam String description) {
        //Hae autentikointi
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserAccount u = userService.getUserByUsername(username);
        try {
            //Tiedostomuodon tarkistus. Tarkista että onko oikea toteutustapa..
            if (!(file.getContentType().equals("image/jpg") || file.getContentType().equals("image/png") || file.getContentType().equals("image/jpeg"))) {
                return "redirect:/";
            }
            //Tallenna kuva
            imageService.addImage(u, file.getBytes(), description);

        } catch (IOException ex) {
            Logger.getLogger(UploadController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/";
    }
}
