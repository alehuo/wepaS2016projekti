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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    
    @Autowired
    private CommentService commentService;
    
    @RequestMapping(value = "/{uuid}", method = RequestMethod.POST)
    public String addComment(@PathVariable String uuid, @RequestParam String comment) {
        Image img = imageService.findOneImageByUuid(uuid);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserAccount u = userService.getUserByUsername(username);
        Comment comm = commentService.addComment(comment, u);
        img.addComment(comm);
        
        imageService.saveImage(img);
        return "redirect:/";
    }
}
