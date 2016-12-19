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
 *
 * @author alehuo
 */
@Controller
@RequestMapping("search")
public class SearchController {
    
    private static final Logger LOG = Logger.getLogger(SearchController.class.getName());
    
    @Autowired
    private UserService userService;
    
    /**
     *
     * @param a
     * @param m
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String search(Authentication a, Model m) {
        m.addAttribute("user", userService.getUserByUsername(a.getName()));
        return "search";
    }
    
    /**
     *
     * @param a
     * @param m
     * @param username
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String doSearch(Authentication a, Model m, @RequestParam String username) {
        LOG.log(Level.INFO, "Kayttaja ''{0}'' haki hakusanalla ''{1}''.", new Object[]{a.getName(), username});
        m.addAttribute("user", userService.getUserByUsername(a.getName()));
        m.addAttribute("userlist", userService.getUsersByUsernameLike(username));
        return "search";
    }
}
