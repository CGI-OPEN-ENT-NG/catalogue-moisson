package fr.openent.moisson.service.dto;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

/**
 * A DTO for the {@link fr.openent.moisson.domain.ArticlePapier} entity.
 */
public class ArticlePapierDTO implements Serializable {

    private Long id;

    //@Size(min = 13, max = 13)
    private String ean;

    private String ark;

    private String titre;

    private String editeur;

    private String auteur;

    private String referenceEditeur;

    private String collection;

    private String distributeur;

    private String urlCouverture;

    private Instant dateParution;

    private BigDecimal prixHT;

    private String description;

    private String type;

    private Set<TvaDTO> tvas;

    private Long disponibiliteId;

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

    public Instant getDateParution() {
        return dateParution;
    }

    public void setDateParution(Instant dateParution) {
        this.dateParution = dateParution;
    }

    public BigDecimal getPrixHT() {
        return prixHT;
    }

    public void setPrixHT(BigDecimal prixHT) {
        this.prixHT = prixHT;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public Long getDisponibiliteId() {
        return disponibiliteId;
    }

    public void setDisponibiliteId(Long disponibiliteId) {
        this.disponibiliteId = disponibiliteId;
    }

    public Set<TvaDTO> getTvas() {
        return tvas;
    }

    public void setTvas(Set<TvaDTO> tvas) {
        this.tvas = tvas;
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
            ", dateParution='" + getDateParution() + "'" +
            ", prixHT=" + getPrixHT() +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", disponibiliteId=" + getDisponibiliteId() +
            "}";
    }
}
