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
import com.alehuo.wepas2016projekti.service.CommentService;
import com.alehuo.wepas2016projekti.service.ImageService;
import com.alehuo.wepas2016projekti.service.UserService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Jusaa
 */
@Controller
@RequestMapping("comment")
public class CommentController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ImageService imageService;
    
    private static final Logger LOG = Logger.getLogger(CommentController.class.getName());
    
    @Autowired
    private CommentService commentService;
    
    @RequestMapping(value = "/{uuid}", method = RequestMethod.POST)
    public String addComment(Authentication a, @PathVariable String uuid, @RequestParam String comment, HttpServletRequest request) {
        Image img = imageService.findOneImageByUuid(uuid);
        UserAccount u = userService.getUserByUsername(a.getName());
        Comment comm = commentService.addComment(comment, u);
        img.addComment(comm);
        u.addComment(comm);
        LOG.log(Level.INFO, "Kayttaja {0} kommentoi kuvaan {1} viestin sisallolla \"{2}\"", new Object[]{a.getName(), uuid, comment});
        userService.saveUser(u);
        imageService.saveImage(img);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
