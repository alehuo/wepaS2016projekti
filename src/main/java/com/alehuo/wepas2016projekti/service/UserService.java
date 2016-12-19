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
import com.alehuo.wepas2016projekti.repository.UserAccountRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Palvelu, joka käsittelee käyttäjätunnuksia
 *
 * @author alehuo
 */
@Service("userService")
public class UserService {

    @Autowired
    private UserAccountRepository userRepo;

    /**
     * Uuden käyttäjän luominen
     *
     * @param username Käyttäjätunnus
     * @param password Salasana
     * @param email Sähköpostiosoite
     * @param role
     * @return Luotu käyttäjätunnus
     */
    @Transactional
    public UserAccount createNewUser(String username, String password, String email, Role role) {

        UserAccount u = new UserAccount();
        u.setUsername(username);
        u.setPassword(new BCryptPasswordEncoder().encode(password));
        u.setEmail(email);
        u.setRole(role);

        return saveUser(u);
    }

    /**
     *
     * @return
     */
    public List<UserAccount> getAllUsers() {
        return userRepo.findAll();
    }

    /**
     *
     * @param id
     * @return
     */
    public UserAccount getUserById(Long id) {
        return userRepo.findOne(id);
    }

    /**
     *
     * @param username
     * @return
     */
    public UserAccount getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    /**
     *
     * @param username
     * @return
     */
    public UserAccount getUserByUsernameIgnoreCase(String username) {
        return userRepo.findByUsernameIgnoreCase(username);
    }

    /**
     *
     * @param u
     * @return
     */
    public UserAccount saveUser(UserAccount u) {
        return userRepo.save(u);
    }

    /**
     *
     */
    public void deleteAllUsers() {
        userRepo.deleteAll();
    }

    /**
     *
     * @param username
     * @return
     */
    public List<UserAccount> getUsersByUsernameLike(String username) {
        return userRepo.findByUsernameIgnoreCaseContaining(username);
    }

}
