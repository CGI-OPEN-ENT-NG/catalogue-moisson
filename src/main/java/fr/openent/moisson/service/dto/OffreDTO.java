package fr.openent.moisson.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link fr.openent.moisson.domain.Offre} entity.
 */
public class OffreDTO implements Serializable {
    
    private Long id;

    @Size(min = 13, max = 13)
    private String eanLibraire;

    private Integer quantiteMinimaleAchat;

    private Boolean prescripteur;

    private String libelle;

    private BigDecimal prixHT;

    private Boolean adoptant;

    private String duree;

    private String referenceEditeur;


    private Long articleNumeriqueId;

    private Long licenceId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEanLibraire() {
        return eanLibraire;
    }

    public void setEanLibraire(String eanLibraire) {
        this.eanLibraire = eanLibraire;
    }

    public Integer getQuantiteMinimaleAchat() {
        return quantiteMinimaleAchat;
    }

    public void setQuantiteMinimaleAchat(Integer quantiteMinimaleAchat) {
        this.quantiteMinimaleAchat = quantiteMinimaleAchat;
    }

    public Boolean isPrescripteur() {
        return prescripteur;
    }

    public void setPrescripteur(Boolean prescripteur) {
        this.prescripteur = prescripteur;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public BigDecimal getPrixHT() {
        return prixHT;
    }

    public void setPrixHT(BigDecimal prixHT) {
        this.prixHT = prixHT;
    }

    public Boolean isAdoptant() {
        return adoptant;
    }

    public void setAdoptant(Boolean adoptant) {
        this.adoptant = adoptant;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getReferenceEditeur() {
        return referenceEditeur;
    }

    public void setReferenceEditeur(String referenceEditeur) {
        this.referenceEditeur = referenceEditeur;
    }

    public Long getArticleNumeriqueId() {
        return articleNumeriqueId;
    }

    public void setArticleNumeriqueId(Long articleNumeriqueId) {
        this.articleNumeriqueId = articleNumeriqueId;
    }

    public Long getLicenceId() {
        return licenceId;
    }

    public void setLicenceId(Long licenceId) {
        this.licenceId = licenceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OffreDTO)) {
            return false;
        }

        return id != null && id.equals(((OffreDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OffreDTO{" +
            "id=" + getId() +
            ", eanLibraire='" + getEanLibraire() + "'" +
            ", quantiteMinimaleAchat=" + getQuantiteMinimaleAchat() +
            ", prescripteur='" + isPrescripteur() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", prixHT=" + getPrixHT() +
            ", adoptant='" + isAdoptant() + "'" +
            ", duree='" + getDuree() + "'" +
            ", referenceEditeur='" + getReferenceEditeur() + "'" +
            ", articleNumeriqueId=" + getArticleNumeriqueId() +
            ", licenceId=" + getLicenceId() +
            "}";
    }
}
