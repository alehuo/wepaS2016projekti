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
import com.alehuo.wepas2016projekti.domain.UserAccount;
import com.alehuo.wepas2016projekti.repository.CommentRepository;
import com.alehuo.wepas2016projekti.repository.ImageRepository;
import com.alehuo.wepas2016projekti.repository.UserAccountRepository;
import java.util.List;
import javax.persistence.PreRemove;
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
 * - Kommenttien poisto 
 * - Kuvien poisto
 *
 *TYÖN ALLA
 * 
 * @author alehuo
 */
@Controller
@RequestMapping("delete")
public class AdminController {

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private ImageRepository imageRepo;

    @Autowired
    private UserAccountRepository userRepo;

    /**
     * Kommentin poistaminen sen ID:n perusteella
     *
     * @param a Autentikointi
     * @param commentId Kommentin id
     */
    @Transactional
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public String deleteComment(Authentication a, @RequestParam Long commentId) {
//        Comment c = commentRepo.findOne(commentId);
//        UserAccount ua = c.getUser();
////        ua.removeComment(c);
//        userRepo.save(ua);
//        /*TODO*/
//        commentRepo.delete(commentId);
        return "redirect:/";
    }

    /**
     * Kuvan poistaminen sen ID:n perusteella
     *
     * @param a Autentikointi
     * @param photoId Kuvan id
     */
    @Transactional
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public String deletePhoto(Authentication a, @RequestParam Long photoId) {
//        Image i = imageRepo.findOne(photoId);
//        if (!i.getComments().isEmpty()) {
//            for (Comment c : i.getComments()) {
//                UserAccount u = userRepo.findOne(c.getUser().getId());
//                u.removeComment(c);
//            }
//        }
////        imageRepo.save(i);
//        commentRepo.deleteInBatch(i.getComments());
//        imageRepo.delete(photoId);
        return "redirect:/";
    }
}
