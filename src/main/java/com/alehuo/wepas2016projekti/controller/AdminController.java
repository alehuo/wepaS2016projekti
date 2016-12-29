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

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Admin -kontrolleri.
 *
 * - Kommenttien poisto - Kuvien poisto
 *
 * @author alehuo
 */
@Controller
@RequestMapping("admin/delete")
public class AdminController {

    /**
     * Kommentin poistaminen sen ID:n perusteella
     * @param a Autentikointi 
     * @param commentId Kommentin id
     */
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public void deleteComment(Authentication a, @RequestParam Long commentId) {

    }

    /**
     * Kuvan poistaminen sen ID:n perusteella
     * @param a Autentikointi
     * @param photoId Kuvan id
     */
    @RequestMapping(value = "/photo", method = RequestMethod.POST)
    public void deletePhoto(Authentication a, @RequestParam Long photoId) {

    }
}
