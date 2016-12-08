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

import edu.emory.mathcs.backport.java.util.Collections;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
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

    @ManyToOne
    private UserAccount imageOwner;

    @ManyToMany
    private List<UserAccount> likedBy;

    @ManyToMany
    private List<Comment> comments;

    @ManyToMany
    private List<Comment> lastThreeComments;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] imageData;

    @NotBlank
    @Length(min = 4, max = 64)
    private String description;

    private String contentType;

    @Column(name = "timestamp")
    @Type(type = "timestamp")
    private Date creationDate;

    private int likes = 0;

    private String uuid = UUID.randomUUID().toString();

    public Image() {
        comments = new ArrayList<>();
        lastThreeComments = new ArrayList<>();
        likedBy = new ArrayList<>();
    }

    /**
     * Aseta kuvan timestamp luontihetkenä
     */
    @PrePersist
    protected void onCreate() {
        creationDate = new Date();
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
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
        if (comments.size() > 3) {
            this.lastThreeComments = comments.subList(comments.size() - 3, comments.size());
        } else {
            this.lastThreeComments = comments;
        }
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment c) {
        if (comments.size() >= 3) {
            this.lastThreeComments.remove(0);            
        }
        lastThreeComments.add(c);
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
        Collections.reverse(lastThreeComments);
        return lastThreeComments;
    }

    public void setLastThreeComments(List<Comment> lastThreeComments) {
        this.lastThreeComments = lastThreeComments;
    }
    
    

}
