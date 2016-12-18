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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.CascadeType;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author alehuo
 */
@Entity
@Table(name = "Images")
public class Image extends AbstractPersistable<Long> {

    /**
     * Kuvan omistaja (Monta kuvaa yhdellä käyttäjällä mutta yksi kuva vastaa
     * yhtä käyttäjää)
     */
    @ManyToOne
    private UserAccount imageOwner;

    /**
     * Tykkäykset (Kuvalla monta tykkäystä ja käyttäjä voi tykätä monta kuvaa)
     */
    @ManyToMany
    private List<UserAccount> likedBy;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Comment> comments;

    /**
     * Kuvadata (One to one)
     */
    @OneToOne(cascade = ALL, fetch = EAGER)
    private File file;

    /**
     * Kuvan otsikko
     */
    @NotBlank
    @Length(min = 4, max = 64)
    private String description;

    /**
     * Kuvan tyyppi
     */
    private String contentType;

    @Column(name = "timestamp")
    @Type(type = "timestamp")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creationDate;

    private String uuid = UUID.randomUUID().toString();

    public Image() {
        comments = new ArrayList<>();
        likedBy = new ArrayList<>();
        file = new File();
    }

    /**
     * Aseta kuvan timestamp luontihetkenä
     */
    @PrePersist
    protected void onCreate() {
        creationDate = new Date();
    }

    public byte[] getImageData() {
        return file.getFile();
    }

    public void setImageData(byte[] imageData) {
        file.setFile(imageData);
    }

    public void setImageOwner(UserAccount imageOwner) {
        this.imageOwner = imageOwner;
    }

    public UserAccount getImageOwner() {
        return imageOwner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addLike(UserAccount u) {
        likedBy.add(u);
    }

    public void removeLike(UserAccount u) {
        if (likedBy.contains(u)) {
            likedBy.remove(u);
        }
    }

    public List<UserAccount> getLikedBy() {
        return likedBy;
    }

    public void setImageContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public int getLikes() {
        return likedBy.size();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment c) {
        comments.add(c);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getCommentsAmount() {
        return comments.size();
    }

    public List<Comment> getLastThreeComments() {
        //Jos kommentteja on >= 3 niin palautetaan sublist
        if (comments.size() >= 3) {
            return comments.subList(comments.size() - 3, comments.size());
        }
        //Jos näin ei ole, käydään kommenttilista läpi for -loopilla
        //Esim. jos koko on kaksi, käydään läpi indeksit 1 ja 0
        List<Comment> tmpComments = new ArrayList<>();
        for (int i = comments.size() - 1; i >= 0; i--) {
            tmpComments.add(comments.get(i));
        }
        return tmpComments;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Arrays.hashCode(this.file.getFile());
        hash = 31 * hash + Objects.hashCode(this.contentType);
        return hash;
    }

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
        final Image other = (Image) obj;
        if (!Objects.equals(this.contentType, other.contentType)) {
            return false;
        }
        if (!Arrays.equals(this.getImageData(), other.getImageData())) {
            return false;
        }
        return true;
    }

}
