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
import com.alehuo.wepas2016projekti.domain.form.ImageUploadFormData;
import com.alehuo.wepas2016projekti.service.ImageService;
import com.alehuo.wepas2016projekti.service.UserService;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

/**
 * Kuvan lataukseen käytettävä kontrolleri
 * @author alehuo
 */
@Controller
@RequestMapping("upload")
public class UploadController {

    private static final Logger LOG = Logger.getLogger(UploadController.class.getName());
    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    /**
     * Kuvan lataussivu
     * @param a Autentikointi
     * @param m Malli
     * @param l Locale
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String upload(Authentication a, Model m, Locale l) {
        UserAccount u = userService.getUserByUsername(a.getName());
        m.addAttribute("user", u);
        m.addAttribute("imageUploadFormData", new ImageUploadFormData());
        return "upload";
    }

    /**
     * Kuvan latauksen käsittely
     * @param a Autentikointi
     * @param m Malli
     * @param formData Lomakkeen data
     * @param bs BindingResult
     * @param l Locale
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String processUpload(Authentication a, Model m, @Valid @ModelAttribute ImageUploadFormData formData, BindingResult bs, Locale l) {
        //Hae autentikointi
        UserAccount u = userService.getUserByUsername(a.getName());
        m.addAttribute("user", u);
        if (bs.hasErrors()) {
            LOG.log(Level.WARNING, "Kayttaja ''{0}'' yritti ladata kuvaa, mutta syotteita ei validoitu. Onko otsikko tyhja?", a.getName());
            return "upload";
        }
        MultipartFile file = formData.getFile();
        String description = formData.getDescription();

        //Tiedostomuodon tarkistus
        if (!(file.getContentType().equals("image/jpg") || file.getContentType().equals("image/png") || file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/bmp") || file.getContentType().equals("image/gif"))) {
            if (l.toString().equals("fi")) {
                bs.rejectValue("file", "error.file", "Tiedostomuotoa ei sallita.");
            } else {
                bs.rejectValue("file", "error.file", "File type not permitted.");
            }

            if (bs.hasErrors()) {
                LOG.log(Level.WARNING, "Kayttaja ''{0}'' yritti ladata kuvaa, mutta tiedostomuotoa ''{1}'' ei sallita.", new Object[]{a.getName(), file.getContentType()});
                return "upload";
            }
        } else {
            //Tallenna kuva
            Image i;
            try {
                i = imageService.addImage(u, file.getBytes(), file.getContentType(), description);
                LOG.log(Level.INFO, "Kayttaja ''{0}'' latasi uuden kuvan palveluun. Kuvan tunniste: ''{1}''", new Object[]{a.getName(), i.getUuid()});
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
                LOG.log(Level.SEVERE, "Kayttaja ''{0}'' yritti ladata kuvaa palveluun, mutta tapahtui palvelinvirhe.", a.getName());
                if (l.toString().equals("fi")) {
                    bs.rejectValue("file", "error.file", "Kuvan lähetys epäonnistui.");
                } else {
                    bs.rejectValue("file", "error.file", "Image upload failed.");
                }

                return "upload";
            }

        }

        return "redirect:/";
    }
}