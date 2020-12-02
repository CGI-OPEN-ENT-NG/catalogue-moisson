package fr.openent.moisson.service.dto;

import java.math.BigDecimal;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.openent.moisson.domain.enumeration.Disponibilite;
import fr.openent.moisson.domain.enumeration.TypeArticle;

/**
 * A DTO for the {@link fr.openent.moisson.domain.ArticlePapier} entity.
 */
public class ArticlePapierDTO implements Serializable {

    @JsonIgnore
    private Long id;

    @Size(min = 13, max = 13)
    @JsonProperty("EAN")
    private String ean;

    @JsonProperty("ARK")
    private String ark;

    @JsonProperty("TITRE")
    private String titre;

    @JsonProperty("EDITEUR")
    private String editeur;

    @JsonProperty("AUTEUR")
    private String auteur;

    @JsonProperty("REF_EDITEUR")
    private String referenceEditeur;

    @JsonProperty("COLLECTION")
    private String collection;

    @JsonProperty("DISTRIBUTEUR")
    private String distributeur;

    @JsonProperty("URL_COUVERTURE")
    private String urlCouverture;

    @JsonProperty("DISPONIBILITE")
    private Disponibilite disponibilite;

    @JsonProperty("DATE_DISPONIBILTE")
    private Instant dateDisponibilite;

    @JsonProperty("DATE_PARUTION")
    private Instant dateParution;

    @JsonProperty("COMMANDABLE")
    private Boolean commandable;

    @JsonProperty("TYPE")
    private TypeArticle type;

    @JsonProperty("TVA")
    private Set<TvaDTO> tvas;

    @JsonProperty("PRIXHT")
    private BigDecimal prixHT;

    @JsonProperty("DESCRIPTION")
    private String description;


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

    public Disponibilite getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(Disponibilite disponibilite) {
        this.disponibilite = disponibilite;
    }

    public Instant getDateDisponibilite() {
        return dateDisponibilite;
    }

    public void setDateDisponibilite(Instant dateDisponibilite) {
        this.dateDisponibilite = dateDisponibilite;
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

    public TypeArticle getType() {
        return type;
    }

    public void setType(TypeArticle type) {
        this.type = type;
    }

    public Set<TvaDTO> getTvas() {
        return tvas;
    }

    public void setTvas(Set<TvaDTO> tvas) {
        this.tvas = tvas;
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
            ", disponibilite='" + getDisponibilite() + "'" +
            ", dateDisponibilite='" + getDateDisponibilite() + "'" +
            ", dateParution='" + getDateParution() + "'" +
            ", commandable='" + isCommandable() + "'" +
            ", type='" + getType() + "'" +
            ", prixHT=" + getPrixHT() +
            ", description='" + getDescription() + "'" +
            ", tvas=" + getTvas() +
            "}";
    }
}
