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

import com.alehuo.wepas2016projekti.domain.Image;
import com.alehuo.wepas2016projekti.domain.Role;
import com.alehuo.wepas2016projekti.domain.UserAccount;
import com.alehuo.wepas2016projekti.repository.ImageRepository;
import com.alehuo.wepas2016projekti.service.InitService;
import com.alehuo.wepas2016projekti.service.UserService;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Oletuskontrolleri
 *
 * @author alehuo
 */
@Controller
public class DefaultController {
    private static final Logger LOG = Logger.getLogger(DefaultController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private ImageRepository imageRepo;

    @Autowired
    private InitService initService;

    @Autowired
    private MessageSource messageSource;

    private static final Logger LOG = Logger.getLogger(DefaultController.class.getName());

    /**
     * Alustus
     */
    @PostConstruct
    public void init() {
        initService.resetApplicationState();
    }

    /**
     *
     * @param a
     * @param m
     * @return
     */
    @RequestMapping("/")
    public String index(Authentication a, Model m, Locale l) {
        System.out.println(l);
        System.out.println(messageSource.getMessage("service.title", null, l));
        List<Image> images = imageRepo.findTop10ByOrderByIdDesc();
        UserAccount u = userService.getUserByUsername(a.getName());
        m.addAttribute("user", u);
        m.addAttribute("images", images);
        return "index";
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    /**
     *
     * @param a
     * @param m
     * @return
     */
    @RequestMapping(value = "/logout/confirm", method = RequestMethod.GET)
    public String logout(Authentication a, Model m, Locale l) {
        UserAccount u = userService.getUserByUsername(a.getName());
        m.addAttribute("user", u);
        return "logoutconfirm";
    }

    /**
     *
     * @param m
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model m) {
        m.addAttribute("userAccount", new UserAccount());
        return "register";
    }

    /**
     *
     * @param userAccount
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute("userAccount") UserAccount userAccount, BindingResult bindingResult) {
        String username = userAccount.getUsername();
        String email = userAccount.getEmail();
        String password = userAccount.getPassword();
        if (bindingResult.hasErrors()) {
            LOG.log(Level.WARNING, "Kayttaja ''{0}'' yritti rekisteroitya sivustolle, mutta syotteita ei validoitu onnistuneesti.", username);
            return "register";
        }
        if (userService.getUserByUsernameIgnoreCase(username) == null) {
            userService.createNewUser(username, password, email, Role.USER);
            LOG.log(Level.INFO, "Kayttaja ''{0}'' rekisteroityi onnistuneesti sivustolle.", username);
        } else {
            LOG.log(Level.WARNING, "Kayttaja ''{0}'' yritti rekisteroitya sivustolle, mutta kayttajatunnus oli jo kaytossa.", username);
            bindingResult.rejectValue("username", "error.username", "Käyttäjätunnus on jo käytössä.");
            return "register";
        }
        return "redirect:/login";
    }
}
