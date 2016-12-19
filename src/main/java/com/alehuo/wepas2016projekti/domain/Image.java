
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

    /**
     *
     */
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

    /**
     *
     * @return
     */
    public byte[] getImageData() {
        return file.getFile();
    }

    /**
     *
     * @param imageData
     */
    public void setImageData(byte[] imageData) {
        file.setFile(imageData);
    }

    /**
     *
     * @param imageOwner
     */
    public void setImageOwner(UserAccount imageOwner) {
        this.imageOwner = imageOwner;
    }

    /**
     *
     * @return
     */
    public UserAccount getImageOwner() {
        return imageOwner;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @param u
     */
    public void addLike(UserAccount u) {
        likedBy.add(u);
    }

    /**
     *
     * @param u
     */
    public void removeLike(UserAccount u) {
        if (likedBy.contains(u)) {
            likedBy.remove(u);
        }
    }

    /**
     *
     * @return
     */
    public List<UserAccount> getLikedBy() {
        return likedBy;
    }

    /**
     *
     * @param contentType
     */
    public void setImageContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     *
     * @return
     */
    public String getContentType() {
        return contentType;
    }

    /**
     *
     * @return
     */
    public int getLikes() {
        return likedBy.size();
    }

    /**
     *
     * @return
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     *
     * @param comments
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     *
     * @return
     */
    public List<Comment> getComments() {
        return comments;
    }

    /**
     *
     * @param c
     */
    public void addComment(Comment c) {
        comments.add(c);
    }

    /**
     *
     * @return
     */
    public String getUuid() {
        return uuid;
    }

    /**
     *
     * @param uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     *
     * @return
     */
    public int getCommentsAmount() {
        return comments.size();
    }

    /**
     *
     * @return
     */
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
        return Arrays.equals(this.getImageData(), other.getImageData());
    }

}
