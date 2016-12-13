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
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
     * @param imageUuid Kuvan UUID
     * @return Kuva
     */
    @RequestMapping(value = "/{imageUuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable String imageUuid, @RequestHeader(required = false, value = "If-None-Match") String ifNoneMatch) {
        if (ifNoneMatch != null) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        Image i = imageService.findOneImageByUuid(imageUuid);
        if (i != null) {
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(i.getContentType()));
            headers.setContentLength(i.getImageData().length);
            headers.setCacheControl("public");
            headers.setExpires(Long.MAX_VALUE);
            headers.setETag("\"" + imageUuid + "\"");
            
            return new ResponseEntity<>(i.getImageData(), headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Kuvasta tykkäys
     *
     * @param a Authentication
     * @param imageUuid Kuvan UUID
     * @return Onnistuiko pyyntö vai ei (sekä sen tyyppi; unlike vai like).
     */
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> likeImage(Authentication a, @RequestParam String imageUuid) {
        //Käyttäjän autentikoiminen
        UserAccount u = userService.getUserByUsername(a.getName());
        if (u != null) {
            Image i = imageService.findOneImageByUuid(imageUuid);
            if (i != null) {
                if (i.getLikedBy().contains(u)) {
                    i.removeLike(u);
                    imageService.saveImage(i);
                    return new ResponseEntity("unlike", HttpStatus.OK);
                } else {
                    i.addLike(u);
                    imageService.saveImage(i);
                    return new ResponseEntity("like", HttpStatus.OK);
                }
            } else {
                return new ResponseEntity("", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity("", HttpStatus.BAD_REQUEST);
    }
}
