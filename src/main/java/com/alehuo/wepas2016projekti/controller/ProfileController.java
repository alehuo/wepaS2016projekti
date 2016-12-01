/*
 * Copyright (C) 2016 alehuo
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

import com.alehuo.wepas2016projekti.domain.UserAccount;
import com.alehuo.wepas2016projekti.service.UserService;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author alehuo
 */
@Controller
@RequestMapping("profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @RequestMapping("/{username}")
    public String viewProfile(@PathVariable String username, Model m) throws UnsupportedEncodingException {
        username = URLDecoder.decode(username, "UTF-8");
        UserAccount u = userService.getUserByUsername(username);
        m.addAttribute("user", u);
        return "profile";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public String search(@RequestParam String username) {
        return "profile results for " + username;
    }
}
