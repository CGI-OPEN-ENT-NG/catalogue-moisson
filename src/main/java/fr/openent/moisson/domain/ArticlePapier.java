package fr.openent.moisson.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import fr.openent.moisson.domain.enumeration.Disponibilite;

/**
 * A ArticlePapier.
 */
@Entity
@Table(name = "article_papier")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "articlepapier")
public class ArticlePapier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(min = 13, max = 13)
    @Column(name = "ean", length = 13)
    private String ean;

    @Column(name = "ark")
    private String ark;

    @Column(name = "titre")
    private String titre;

    @Column(name = "editeur")
    private String editeur;

    @Column(name = "auteur")
    private String auteur;

    @Column(name = "reference_editeur")
    private String referenceEditeur;

    @Column(name = "collection")
    private String collection;

    @Column(name = "distributeur")
    private String distributeur;

    @Column(name = "url_couverture")
    private String urlCouverture;

    @Enumerated(EnumType.STRING)
    @Column(name = "disponibilte")
    private Disponibilite disponibilte;

    @Column(name = "date_disponibilte")
    private Instant dateDisponibilte;

    @Column(name = "date_parution")
    private Instant dateParution;

    @Column(name = "commandable")
    private Boolean commandable;

    @Column(name = "tva", precision = 21, scale = 2)
    private BigDecimal tva;

    @Column(name = "prix_ht", precision = 21, scale = 2)
    private BigDecimal prixHT;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEan() {
        return ean;
    }

    public ArticlePapier ean(String ean) {
        this.ean = ean;
        return this;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getArk() {
        return ark;
    }

    public ArticlePapier ark(String ark) {
        this.ark = ark;
        return this;
    }

    public void setArk(String ark) {
        this.ark = ark;
    }

    public String getTitre() {
        return titre;
    }

    public ArticlePapier titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getEditeur() {
        return editeur;
    }

    public ArticlePapier editeur(String editeur) {
        this.editeur = editeur;
        return this;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public String getAuteur() {
        return auteur;
    }

    public ArticlePapier auteur(String auteur) {
        this.auteur = auteur;
        return this;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getReferenceEditeur() {
        return referenceEditeur;
    }

    public ArticlePapier referenceEditeur(String referenceEditeur) {
        this.referenceEditeur = referenceEditeur;
        return this;
    }

    public void setReferenceEditeur(String referenceEditeur) {
        this.referenceEditeur = referenceEditeur;
    }

    public String getCollection() {
        return collection;
    }

    public ArticlePapier collection(String collection) {
        this.collection = collection;
        return this;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getDistributeur() {
        return distributeur;
    }

    public ArticlePapier distributeur(String distributeur) {
        this.distributeur = distributeur;
        return this;
    }

    public void setDistributeur(String distributeur) {
        this.distributeur = distributeur;
    }

    public String getUrlCouverture() {
        return urlCouverture;
    }

    public ArticlePapier urlCouverture(String urlCouverture) {
        this.urlCouverture = urlCouverture;
        return this;
    }

    public void setUrlCouverture(String urlCouverture) {
        this.urlCouverture = urlCouverture;
    }

    public Disponibilite getDisponibilte() {
        return disponibilte;
    }

    public ArticlePapier disponibilte(Disponibilite disponibilte) {
        this.disponibilte = disponibilte;
        return this;
    }

    public void setDisponibilte(Disponibilite disponibilte) {
        this.disponibilte = disponibilte;
    }

    public Instant getDateDisponibilte() {
        return dateDisponibilte;
    }

    public ArticlePapier dateDisponibilte(Instant dateDisponibilte) {
        this.dateDisponibilte = dateDisponibilte;
        return this;
    }

    public void setDateDisponibilte(Instant dateDisponibilte) {
        this.dateDisponibilte = dateDisponibilte;
    }

    public Instant getDateParution() {
        return dateParution;
    }

    public ArticlePapier dateParution(Instant dateParution) {
        this.dateParution = dateParution;
        return this;
    }

    public void setDateParution(Instant dateParution) {
        this.dateParution = dateParution;
    }

    public Boolean isCommandable() {
        return commandable;
    }

    public ArticlePapier commandable(Boolean commandable) {
        this.commandable = commandable;
        return this;
    }

    public void setCommandable(Boolean commandable) {
        this.commandable = commandable;
    }

    public BigDecimal getTva() {
        return tva;
    }

    public ArticlePapier tva(BigDecimal tva) {
        this.tva = tva;
        return this;
    }

    public void setTva(BigDecimal tva) {
        this.tva = tva;
    }

    public BigDecimal getPrixHT() {
        return prixHT;
    }

    public ArticlePapier prixHT(BigDecimal prixHT) {
        this.prixHT = prixHT;
        return this;
    }

    public void setPrixHT(BigDecimal prixHT) {
        this.prixHT = prixHT;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticlePapier)) {
            return false;
        }
        return id != null && id.equals(((ArticlePapier) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticlePapier{" +
            "id=" + getId() +
            ", ean='" + getEan() + "'" +
            ", ark='" + getArk() + "'" +
            ", titre='" + getTitre() + "'" +
            ", editeur='" + getEditeur() + "'" +
            ", auteur='" + getAuteur() + "'" +
            ", referenceEditeur='" + getReferenceEditeur() + "'" +
            ", collection='" + getCollection() + "'" +
            ", distributeur='" + getDistributeur() + "'" +
            ", urlCouverture='" + getUrlCouverture() + "'" +
            ", disponibilte='" + getDisponibilte() + "'" +
            ", dateDisponibilte='" + getDateDisponibilte() + "'" +
            ", dateParution='" + getDateParution() + "'" +
            ", commandable='" + isCommandable() + "'" +
            ", tva=" + getTva() +
            ", prixHT=" + getPrixHT() +
            "}";
    }
}
