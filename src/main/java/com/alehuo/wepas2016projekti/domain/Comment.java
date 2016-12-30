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

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Kommentti -entiteetti
 * @author alehuo
 */
@Entity
@Table(name = "Comments")
public class Comment extends AbstractPersistable<Long> {

    /**
     * Kommentin luoja/omistaja
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private UserAccount user;

    /**
     * Viestin sisältö
     */
    @NotNull
    @Length(min = 1, max = 40)
    private String body;

    /**
     * Viestin luomisaikaleima
     */
    @Column(name = "timestamp")
    @Type(type = "timestamp")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creationDate;

    /**
     * Onko viesti näkyvissä?
     */
    @Column(name = "visible")
    private boolean visible = true;

    /**
     * Palauttaa, onko viesti näkyvissä
     * @return Viestin näkyvyys
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Asettaa viestin näkyvyystilan
     * @param visible Näkyvyys
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Aseta kommentin timestamp luontihetkenä
     */
    @PrePersist
    protected void onCreate() {
        creationDate = new Date();
    }

    /**
     * Palauttaa kommentin luojan
     * @return Kommentin luoja
     */
    public UserAccount getUser() {
        return user;
    }

    /**
     * Asettaa kommentin luojan
     * @param user Kommentin luoja
     */
    public void setUser(UserAccount user) {
        this.user = user;
    }

    /**
     * Palauttaa viestin sisällön
     * @return Viestin sisältö
     */
    public String getBody() {
        return body;
    }

    /**
     * Asettaa viestin sisällön
     * @param body Viestin sisältö
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Palauttaa viestin luomisajan
     * @return Viestin luomisaika
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Asettaa viestin luomisajan
     * @param creationDate Viestin luomisaika
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}
