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

import com.alehuo.wepas2016projekti.domain.UserAccount;
import com.alehuo.wepas2016projekti.service.UserService;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * "Restful" rajapinta käyttäjien hakemiselle hakusanan perusteella.
 *
 * @author alehuo
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    @Autowired
    private UserService usrService;

    /**
     * Palauttaa käyttäjätunnuksia hakusanan perusteella
     *
     * @param searchTerm
     * @param res
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public List<UserAccount> getUsersMatching(@RequestParam String searchTerm, HttpServletResponse res) {
        res.setStatus(200);
        return usrService.getUsersByUsernameLike(searchTerm);
    }
}
