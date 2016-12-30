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

import com.alehuo.wepas2016projekti.domain.Comment;
import com.alehuo.wepas2016projekti.domain.Image;
import com.alehuo.wepas2016projekti.repository.CommentRepository;
import com.alehuo.wepas2016projekti.repository.ImageRepository;
import com.alehuo.wepas2016projekti.repository.UserAccountRepository;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Admin -kontrolleri.
 *
 * Kommenttien ja kuvien poisto.

 *
 * @author alehuo
 */
@Controller
@RequestMapping("delete")
public class AdminController {

    /**
     * Logger
     */
    private static final Logger LOG = Logger.getLogger(AdminController.class.getName());

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

    /**
     * Käyttäjärepositorio
     */
    @Autowired
    private UserAccountRepository userRepo;

    /**
     * Kommentin poistaminen sen ID:n perusteella Asetetaan lippu "deleted" true
     * -arvoon jotta kommentti ei näy. Tällöin vältetään Data violation
     * exceptionit jne.
     *
     * @param a Autentikointi
     * @param commentId Kommentin id
     * @return Näkymä
     */
    @Transactional
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public String deleteComment(Authentication a, @RequestParam Long commentId) {
        Comment c = commentRepo.findOne(commentId);
        //Aseta pois näkyviltä
        c.setVisible(false);
        commentRepo.save(c);
        LOG.log(Level.INFO, "Paakayttaja ''{0}'' poisti kayttajan ''{1}'' kommentin viestin sisallolla \"{2}\"", new Object[]{a.getName(), c.getUser().getUsername(), c.getBody()});
        return "redirect:/";
    }

    /**
     * Kuvan poistaminen sen ID:n perusteella
     *
     * @param a Autentikointi
     * @param photoId Kuvan id
     * @return Näkymä
     */
    @Transactional
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public String deletePhoto(Authentication a, @RequestParam Long photoId) {
        Image i = imageRepo.findOne(photoId);
        //Aseta pois näkyviltä
        i.setVisible(false);
        //Tallenna
        imageRepo.save(i);
        //Logita
        LOG.log(Level.INFO, "Paakayttaja ''{0}'' poisti kuvan ''{1}''", new Object[]{a.getName(), i.getUuid()});
        return "redirect:/";
    }
}
