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
package com.alehuo.wepas2016projekti.service;

import com.alehuo.wepas2016projekti.domain.Role;
import com.alehuo.wepas2016projekti.domain.UserAccount;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alehuo.wepas2016projekti.repository.UserAccountRepository;
import javax.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Palvelu, joka käsittelee käyttäjätunnuksia
 *
 * @author alehuo
 */
@Service
public class UserService {

    @Autowired
    private UserAccountRepository userRepo;

    @PostConstruct
    public void init() {
        System.out.println("Creating users");
        createNewUser("admin", "admin", "admin@localhost.com", Role.ADMINISTRATOR);
        createNewUser("user", "user", "user@localhost.com", Role.USER);
    }

    /**
     * Uuden käyttäjän luominen
     *
     * @param username Käyttäjätunnus
     * @param password Salasana
     * @param email Sähköpostiosoite
     * @return Luotu käyttäjätunnus
     */
    public UserAccount createNewUser(String username, String password, String email, Role role) {

        UserAccount u = new UserAccount();
        u.setUsername(username);
        u.setPassword(new BCryptPasswordEncoder().encode(password));
        u.setEmail(email);
        u.setRole(role);

        return saveUser(u);
    }

    public List<UserAccount> getAllUsers() {
        return userRepo.findAll();
    }

    public UserAccount getUserById(Long id) {
        return userRepo.findOne(id);
    }

    public UserAccount getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }
    
    public UserAccount saveUser(UserAccount u){
        return userRepo.save(u);
    }

}
