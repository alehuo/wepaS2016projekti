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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
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
    private static final Logger LOG = Logger.getLogger(ImageController.class.getName());

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;


    /**
     * Hakee tietokannasta kuvan
     *
     * @param a
     * @param imageUuid Kuvan UUID
     * @param ifNoneMatch
     * @return Kuva
     */
    @RequestMapping(value = "/{imageUuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> getImage(Authentication a, @PathVariable String imageUuid, @RequestHeader(required = false, value = "If-None-Match") String ifNoneMatch) {
        if (ifNoneMatch != null) {
//            LOG.log(Level.INFO, "Kuva ''{0}'' loytyy kayttajan selaimen valimuistista eika sita tarvitse ladata. Kuvaa pyysi kayttaja ''{1}''", new Object[]{imageUuid, a.getName()});
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
//            LOG.log(Level.INFO, "Kuva ''{0}'' loytyi tietokannasta, ja sita pyysi kayttaja ''{1}''", new Object[]{imageUuid, a.getName()});
            return new ResponseEntity<>(i.getImageData(), headers, HttpStatus.CREATED);
        } else {
            LOG.log(Level.WARNING, "Kuvaa ''{0}'' ei loytynyt tietokannasta, ja sita pyysi kayttaja ''{1}''", new Object[]{imageUuid, a.getName()});
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Kuvasta tykkäys
     *
     * @param a Authentication
     * @param imageUuid Kuvan UUID
     * @param redirect
     * @param req
     * @return Onnistuiko pyyntö vai ei (sekä sen tyyppi; unlike vai like).
     */
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public ResponseEntity<String> likeImage(Authentication a, @RequestParam String imageUuid, @RequestParam int redirect, HttpServletRequest req) {
        //Käyttäjän autentikoiminen
        UserAccount u = userService.getUserByUsername(a.getName());
        final HttpHeaders h = new HttpHeaders();
        if (u != null) {
            Image i = imageService.findOneImageByUuid(imageUuid);
            if (i != null) {
                if (i.getLikedBy().contains(u)) {
                    LOG.log(Level.INFO, "Kayttaja ''{0}'' poisti tykkayksen kuvasta ''{1}''", new Object[]{a.getName(), imageUuid});
                    i.removeLike(u);
                    imageService.saveImage(i);
                    h.add("LikeType", "unlike");
                    if (redirect == 1) {
                        String referer = req.getHeader("Referer");
                        h.add("Location", req.getHeader("Referer"));
                        return new ResponseEntity<>(h, HttpStatus.FOUND);
                    }
                    return new ResponseEntity<>(h, HttpStatus.OK);
                } else {
                    i.addLike(u);
                    LOG.log(Level.INFO, "Kayttaja ''{0}'' tykkasi kuvasta ''{1}''", new Object[]{a.getName(), imageUuid});
                    imageService.saveImage(i);
                    h.add("LikeType", "like");
                    if (redirect == 1) {
                        String referer = req.getHeader("Referer");
                        h.add("Location", req.getHeader("Referer"));
                        return new ResponseEntity<>(h, HttpStatus.FOUND);
                    }
                    return new ResponseEntity<>(h, HttpStatus.OK);
                }
            } else {
                LOG.log(Level.WARNING, "Kayttaja ''{0}'' yritti tykata kuvaa, mita ei ole olemassa. ({1})", new Object[]{a.getName(), imageUuid});
                return new ResponseEntity<>(h, HttpStatus.BAD_REQUEST);
            }
        }
        LOG.log(Level.WARNING, "Yritettiin tykata kuvaa kirjautumatta sisaan ({0})", a.getName());
        return new ResponseEntity<>(h, HttpStatus.UNAUTHORIZED);
    }
}
