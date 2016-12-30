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
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Kuva -entiteetti
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

    /**
     * Lista kommenteista (Yksi kommentti liittyy yhteen kuvaan mutta kuvalla
     * voi olla monta kommenttia)
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Comment> comments;

    /**
     * Kuvadata (One to one)
     */
    @OneToOne(cascade = ALL, fetch = EAGER)
    private File file;

    /**
     * Kuvan otsikko
     */
    @Length(max = 24)
    private String description;

    /**
     * Kuvan tyyppi
     */
    private String contentType;

    @Column(name = "timestamp")
    @Type(type = "timestamp")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creationDate;

    /**
     * Kuvan UUID
     */
    private String uuid = UUID.randomUUID().toString();

    /**
     * Onko kuva näkyvissä?
     */
    private boolean visible = true;

    /**
     * Konstruktori, joka asettaa muutaman listan
     */
    public Image() {
        comments = new ArrayList<>();
        likedBy = new ArrayList<>();
        file = new File();
    }

    /**
     * Palauttaa kuvan näkyvyyden
     *
     * @return Näkyvyys
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Aseta kuvan näkyvyystila
     *
     * @param visible Näkyvyys
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Aseta kuvan timestamp luontihetkenä
     */
    @PrePersist
    protected void onCreate() {
        creationDate = new Date();
    }

    /**
     * Palauttaa kuvan datan
     *
     * @return Kuvan data
     */
    public byte[] getImageData() {
        return file.getFile();
    }

    /**
     * Asettaa kuvan datan
     *
     * @param imageData Kuvan data
     */
    public void setImageData(byte[] imageData) {
        file.setFile(imageData);
    }

    /**
     * Asettaa kuvan omistajan
     *
     * @param imageOwner Kuvan omistaja
     */
    public void setImageOwner(UserAccount imageOwner) {
        this.imageOwner = imageOwner;
    }

    /**
     * Palauttaa kuvan omistajan
     *
     * @return Kuvan omistaja
     */
    public UserAccount getImageOwner() {
        return imageOwner;
    }

    /**
     * Palauttaa kuvan kuvaus
     *
     * @return Kuvan kuvaus
     */
    public String getDescription() {
        return description;
    }

    /**
     * Asettaa kuvan kuvauksen
     *
     * @param description Kuvan kuvaus
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Lisää kuvalle tykkäyksen
     *
     * @param u Käyttäjätili
     */
    public void addLike(UserAccount u) {
        likedBy.add(u);
    }

    /**
     * Poistaa kuvalta tykkäyksen
     *
     * @param u Käyttäjätili
     */
    public void removeLike(UserAccount u) {
        if (likedBy.contains(u)) {
            likedBy.remove(u);
        }
    }

    /**
     * Palauttaa kuvasta tykänneet henkilöt
     *
     * @return Lista henkilöistä
     */
    public List<UserAccount> getLikedBy() {
        return likedBy;
    }

    /**
     * Asettaa kuvan tiedostomuodon
     *
     * @param contentType Tiedostomuoto
     */
    public void setImageContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Palauttaa kuvan tiedostomuodon
     *
     * @return Kuvan tiedostomuoto
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Palauttaa tykkäyksien lukumäärän
     *
     * @return Tykkäyksien lukumäärä
     */
    public int getLikes() {
        return likedBy.size();
    }

    /**
     * Palauttaa kuvan luomisajan
     *
     * @return Luomisaika
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Asettaa kuvan kommentit
     *
     * @param comments Kuvan kommentit
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * Palauttaa kuvan kommentit suodattaen pois ne jotka pääkäyttäjä on
     * "poistanut"
     *
     * @return Lista kommenteista
     */
    public List<Comment> getComments() {
        List<Comment> tmpComments = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.isVisible()) {
                tmpComments.add(comment);
            }
        }
        return tmpComments;
    }

    /**
     * Lisää kuvalle kommentin
     *
     * @param c Kommentti
     */
    public void addComment(Comment c) {
        comments.add(c);
    }

    /**
     * Palauttaa kuvan UUID:n
     *
     * @return UUID
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Asettaa kuvan UUID:n
     *
     * @param uuid UUID
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Palauttaa kommenttien lukumäärän
     *
     * @return Kommenttien lukumäärä
     */
    public int getCommentsAmount() {
        return getComments().size();
    }

    /**
     * Palauttaa kolme viimeisintä kommenttia
     *
     * @return
     */
    public List<Comment> getLastThreeComments() {
        List<Comment> imageComments = getComments();
        //Jos kommentteja on >= 3 niin palautetaan sublist
        if (imageComments.size() >= 3) {
            return imageComments.subList(imageComments.size() - 3, imageComments.size());
        }
        //Jos näin ei ole, käydään kommenttilista läpi for -loopilla
        //Esim. jos koko on kaksi, käydään läpi indeksit 1 ja 0
        List<Comment> tmpComments = new ArrayList<>();
        for (int i = imageComments.size() - 1; i >= 0; i--) {
            tmpComments.add(imageComments.get(i));
        }
        return tmpComments;
    }

    /**
     * HashCode
     *
     * @return HashCode
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Arrays.hashCode(this.file.getFile());
        hash = 31 * hash + Objects.hashCode(this.contentType);
        return hash;
    }

    /**
     * Equals
     *
     * @param obj Kuva -entiteetti
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
        final Image other = (Image) obj;
        if (!Objects.equals(this.contentType, other.contentType)) {
            return false;
        }
        return Arrays.equals(this.getImageData(), other.getImageData());
    }

}
