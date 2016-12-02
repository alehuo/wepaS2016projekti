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
import com.alehuo.wepas2016projekti.repository.ImageRepository;
import com.alehuo.wepas2016projekti.service.ImageService;
import com.alehuo.wepas2016projekti.service.UserService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author alehuo
 */
@Controller
@RequestMapping("img")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    /**
     * Hakee tietokannasta kuvan
     *
     * @param imageId Kuvan ID
     * @param response HttpServletResponse
     * @return Kuva
     * @throws IOException
     */
    @RequestMapping(value = "/{imageId}", method = RequestMethod.GET)
    @ResponseBody
    public String getImage(@PathVariable Long imageId, HttpServletResponse response) throws IOException {
        Image i = imageService.findOneImage(imageId);
        response.setContentType(i.getContentType());
        ByteArrayInputStream in = new ByteArrayInputStream(i.getImageData());
        IOUtils.copy(in, response.getOutputStream());
        return "";
    }

    /**
     * Kuvasta tykkäys
     *
     * @param imageId
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/like/{imageId}", method = RequestMethod.POST)
    @ResponseBody
    public String likeImage(@PathVariable Long imageId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserAccount u = userService.getUserByUsername(username);
        if (u != null) {
            Image i = imageService.findOneImage(imageId);
            if (i.getLikedBy().contains(u)) {
                i.removeLike(u);
                imageService.saveImage(i);
                return "unlike";
            } else {
                i.addLike(u);
                imageService.saveImage(i);
                return "like";
            }

        }
        return "fail";
    }
}
