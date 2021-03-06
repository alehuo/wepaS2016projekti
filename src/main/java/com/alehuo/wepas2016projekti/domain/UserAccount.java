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
package com.alehuo.wepas2016projekti.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Käyttäjä -entiteetti
 *
 * @author alehuo
 */
@Entity
@Table(name = "Users")
public class UserAccount extends AbstractPersistable<Long> {

    /**
     * Käyttäjätunnus
     */
    @Column(name = "username", unique = true)
    @NotBlank
    @Length(min = 4, max = 25)
    private String username;

    /**
     * Salasana
     */
    @Column(name = "password",
            nullable = false)
    @NotBlank
    private String password;

    /**
     * Sähköpostiosoite
     */
    @Column(name = "email", unique = true, length = 255)
    @NotBlank
    @Email
    private String email;

    /**
     * Käyttäjätilin rooli
     */
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Kommentit (OneToMany: Käyttäjä voi omistaa monta kommenttia mutta yksi
     * kommentti vastaa yhtä käyttäjää)
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comment> comments;

    /**
     * Konstruktori
     */
    public UserAccount() {
        comments = new ArrayList();
    }

    /**
     * Palauttaa käyttäjätilin ID:n
     *
     * @return Käyttäjätilin ID
     */
    @JsonIgnore
    @Override
    public Long getId() {
        return super.getId();
    }

    /**
     * Palauttaa, onko resurssi juuri luotu
     * @return Luotu
     */
    @JsonIgnore
    @Override
    public boolean isNew() {
        return super.isNew();
    }

    /**
     * Palauttaa käyttäjänimen
     * @return Käyttäjänimi
     */
    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    /**
     * Asettaa käyttäjänimen
     * @param username Käyttäjänimi
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Palauttaa s.postiosoitteen
     * @return Sähköpostiosoite
     */
    @JsonIgnore
    public String getEmail() {
        return email;
    }

    /**
     * Asettaa s.postiosoitteen
     * @param email S.postiosoite
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Asettaa salasanan
     * @param password Salasana
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Palauttaa salasanan
     * @return Salasana
     */
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    /**
     * Asettaa käyttäjätilin roolin
     * @param role Rooli
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Palauttaa käyttäjätilin roolin
     * @return Rooli
     */
    @JsonIgnore
    public Role getRole() {
        return role;
    }

    /**
     * Palauttaa käyttäjän kirjoittamat kommentit
     * @return Kommentit
     */
    @JsonIgnore
    public List<Comment> getComments() {
        return comments;
    }

    /**
     * Asettaa kommentit
     * @param comments Kommentit
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * Lisää kommentin
     * @param c Kommentti
     */
    public void addComment(Comment c) {
        comments.add(c);
    }

    /**
     * HashCode
     * @return HashCode
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.username != null ? this.username.hashCode() : 0);
        hash = 89 * hash + (this.role != null ? this.role.hashCode() : 0);
        hash = 89 * hash + (this.email != null ? this.email.hashCode() : 0);
        return hash;
    }

    /**
     * Equals
     * @param obj UserAccount -entiteetti
     * @return true | false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserAccount other = (UserAccount) obj;
        if ((this.username == null) ? (other.username != null) : !this.username.equals(other.username)) {
            return false;
        }
        if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
            return false;
        }
        if (this.role != other.role) {
            return false;
        }
        return true;
    }

}
