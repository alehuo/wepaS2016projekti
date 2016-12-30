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
 * Kuvakontrolleri
 *
 * @author alehuo
 */
@Controller
@RequestMapping("img")
public class ImageController {

    /**
     * Logger
     */
    private static final Logger LOG = Logger.getLogger(ImageController.class.getName());

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    /**
     * Hakee tietokannasta kuvan. Kuvan hakemisessa hyödynnetään ETag
     * -otsaketta.
     *
     * @param a Autentikointi
     * @param imageUuid Kuvan UUID
     * @param ifNoneMatch If-None-Match -headeri välimuistia varten
     * @return Kuva
     */
    @RequestMapping(value = "/{imageUuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> getImage(Authentication a, @PathVariable String imageUuid, @RequestHeader(required = false, value = "If-None-Match") String ifNoneMatch) {
        if (ifNoneMatch != null) {
//            LOG.log(Level.INFO, "Kuva ''{0}'' loytyy kayttajan selaimen valimuistista eika sita tarvitse ladata. Kuvaa pyysi kayttaja ''{1}''", new Object[]{imageUuid, a.getName()});
            //Jos If-None-Match -headeri löytyy, niin lähetä NOT MODIFIED vastaus
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        Image i = imageService.findOneImageByUuid(imageUuid);
        if (i != null && i.isVisible()) {
            //Luodaan ETag kuvalle
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(i.getContentType()));
            headers.setContentLength(i.getImageData().length);
            headers.setCacheControl("public");
            headers.setExpires(Long.MAX_VALUE);
            headers.setETag("\"" + imageUuid + "\"");
//            LOG.log(Level.INFO, "Kuva ''{0}'' loytyi tietokannasta, ja sita pyysi kayttaja ''{1}''", new Object[]{imageUuid, a.getName()});
            //Palautetaan kuva uutena resurssina
            return new ResponseEntity<>(i.getImageData(), headers, HttpStatus.CREATED);
        } else {
            //Jos kuvaa ei löydy tietokannasta
            LOG.log(Level.WARNING, "Kuvaa ''{0}'' ei loytynyt tietokannasta, ja sita pyysi kayttaja ''{1}''", new Object[]{imageUuid, a.getName()});
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Kuvasta tykkäys
     *
     * @param a Autentikointi
     * @param imageUuid Kuvan UUID
     * @param redirect Uudelleenohjaus
     * @param req HTTP Request
     * @return Onnistuiko pyyntö vai ei (sekä sen tyyppi; unlike vai like).
     */
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public ResponseEntity<String> likeImage(Authentication a, @RequestParam String imageUuid, @RequestParam int redirect, HttpServletRequest req) {
        //Käyttäjän autentikoiminen
        UserAccount u = userService.getUserByUsername(a.getName());
        final HttpHeaders h = new HttpHeaders();
        //Jos käyttäjätili ei ole tyhjä
        if (u != null) {
            //Haetaan kuva
            Image i = imageService.findOneImageByUuid(imageUuid);

            //Jos kuva ei ole tyhjä
            if (i != null && i.isVisible()) {
                //Lisää / poista tykkäys tilanteen mukaan
                if (i.getLikedBy().contains(u)) {
                    LOG.log(Level.INFO, "Kayttaja ''{0}'' poisti tykkayksen kuvasta ''{1}''", new Object[]{a.getName(), imageUuid});
                    i.removeLike(u);
                    imageService.saveImage(i);
                    h.add("LikeType", "unlike");
                } else {
                    i.addLike(u);
                    LOG.log(Level.INFO, "Kayttaja ''{0}'' tykkasi kuvasta ''{1}''", new Object[]{a.getName(), imageUuid});
                    imageService.saveImage(i);
                    h.add("LikeType", "like");
                }
                //Uudelleenohjaus
                if (redirect == 1) {
                    h.add("Location", req.getHeader("Referer"));
                    return new ResponseEntity<>(h, HttpStatus.FOUND);
                }
                return new ResponseEntity<>(h, HttpStatus.OK);
            } else {
                //Jos kuvaa ei ole olemassa mutta yritetään silti tykätä
                LOG.log(Level.WARNING, "Kayttaja ''{0}'' yritti tykata kuvaa, mita ei ole olemassa. ({1})", new Object[]{a.getName(), imageUuid});
                return new ResponseEntity<>(h, HttpStatus.BAD_REQUEST);
            }
        }
        //Jos ei olla kirjauduttu sisään ja yritetään tykätä kuvasta
        LOG.log(Level.WARNING, "Yritettiin tykata kuvaa kirjautumatta sisaan ({0})", a.getName());
        return new ResponseEntity<>(h, HttpStatus.UNAUTHORIZED);
    }
}
