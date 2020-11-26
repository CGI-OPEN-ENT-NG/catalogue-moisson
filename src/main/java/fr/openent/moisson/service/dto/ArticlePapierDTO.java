package fr.openent.moisson.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import fr.openent.moisson.domain.enumeration.Disponibilite;

/**
 * A DTO for the {@link fr.openent.moisson.domain.ArticlePapier} entity.
 */
public class ArticlePapierDTO implements Serializable {
    
    private Long id;

    @Size(min = 13, max = 13)
    private String ean;

    private String ark;

    private String titre;

    private String editeur;

    private String auteur;

    private String referenceEditeur;

    private String collection;

    private String distributeur;

    private String urlCouverture;

    private Disponibilite disponibilte;

    private Instant dateDisponibilte;

    private Instant dateParution;

    private Boolean commandable;

    private BigDecimal tva;

    private BigDecimal prixHT;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getArk() {
        return ark;
    }

    public void setArk(String ark) {
        this.ark = ark;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getReferenceEditeur() {
        return referenceEditeur;
    }

    public void setReferenceEditeur(String referenceEditeur) {
        this.referenceEditeur = referenceEditeur;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getDistributeur() {
        return distributeur;
    }

    public void setDistributeur(String distributeur) {
        this.distributeur = distributeur;
    }

    public String getUrlCouverture() {
        return urlCouverture;
    }

    public void setUrlCouverture(String urlCouverture) {
        this.urlCouverture = urlCouverture;
    }

    public Disponibilite getDisponibilte() {
        return disponibilte;
    }

    public void setDisponibilte(Disponibilite disponibilte) {
        this.disponibilte = disponibilte;
    }

    public Instant getDateDisponibilte() {
        return dateDisponibilte;
    }

    public void setDateDisponibilte(Instant dateDisponibilte) {
        this.dateDisponibilte = dateDisponibilte;
    }

    public Instant getDateParution() {
        return dateParution;
    }

    public void setDateParution(Instant dateParution) {
        this.dateParution = dateParution;
    }

    public Boolean isCommandable() {
        return commandable;
    }

    public void setCommandable(Boolean commandable) {
        this.commandable = commandable;
    }

    public BigDecimal getTva() {
        return tva;
    }

    public void setTva(BigDecimal tva) {
        this.tva = tva;
    }

    public BigDecimal getPrixHT() {
        return prixHT;
    }

    public void setPrixHT(BigDecimal prixHT) {
        this.prixHT = prixHT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticlePapierDTO)) {
            return false;
        }

        return id != null && id.equals(((ArticlePapierDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticlePapierDTO{" +
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
