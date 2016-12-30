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

import com.alehuo.wepas2016projekti.service.UserService;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Hakukontrolleri
 *
 * @author alehuo
 */
@Controller
@RequestMapping("search")
public class SearchController {

    /**
     * Logger
     */
    private static final Logger LOG = Logger.getLogger(SearchController.class.getName());

    /**
     * Käyttäjätilipalvelu
     */
    @Autowired
    private UserService userService;

    /**
     * Hakusivu
     *
     * @param a Autentikointi
     * @param m Malli
     * @param l
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String search(Authentication a, Model m, Locale l) {
        m.addAttribute("user", userService.getUserByUsername(a.getName()));
        return "search";
    }

    /**
     * Haun käsittely
     *
     * @param a Autentikointi
     * @param m Malli
     * @param searchTerm Hakutermi
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String doSearch(Authentication a, Model m, @RequestParam String searchTerm) {
        LOG.log(Level.INFO, "Kayttaja ''{0}'' haki hakusanalla ''{1}''.", new Object[]{a.getName(), searchTerm});

        m.addAttribute("user", userService.getUserByUsername(a.getName()));
        if (!searchTerm.trim().isEmpty()) {
            //Lista käyttäjätileistä
            m.addAttribute("userlist", userService.getUsersByUsernameLike(searchTerm));
        }

        return "search";
    }
}
