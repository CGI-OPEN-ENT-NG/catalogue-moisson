package fr.openent.moisson.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * A DTO for the {@link fr.openent.moisson.domain.Offre} entity.
 */
public class OffreDTO implements Serializable {

    private Long id;

    @Size(min = 13, max = 13)
    private String eanLibraire;

    private String reference;

    private Integer duree;

    private Integer quantiteMinimaleAchat;

    private String licence;

    private Boolean prescripteur;

    private String libelle;

    private BigDecimal prixHT;

    private Boolean adoptant;

    private Long articleNumeriqueId;

    private Set<LepDTO> leps;

    private Set<TvaDTO> tavs;

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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public Integer getQuantiteMinimaleAchat() {
        return quantiteMinimaleAchat;
    }

    public void setQuantiteMinimaleAchat(Integer quantiteMinimaleAchat) {
        this.quantiteMinimaleAchat = quantiteMinimaleAchat;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
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

    public Long getArticleNumeriqueId() {
        return articleNumeriqueId;
    }

    public void setArticleNumeriqueId(Long articleNumeriqueId) {
        this.articleNumeriqueId = articleNumeriqueId;
    }

    public Set<LepDTO> getLeps() {
        return leps;
    }

    public void setLeps(Set<LepDTO> leps) {
        this.leps = leps;
    }

    public Set<TvaDTO> getTavs() {
        return tavs;
    }

    public void setTavs(Set<TvaDTO> tavs) {
        this.tavs = tavs;
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
            ", reference='" + getReference() + "'" +
            ", duree=" + getDuree() +
            ", quantiteMinimaleAchat=" + getQuantiteMinimaleAchat() +
            ", licence='" + getLicence() + "'" +
            ", prescripteur='" + isPrescripteur() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", prixHT=" + getPrixHT() +
            ", adoptant='" + isAdoptant() + "'" +
            ", articleNumeriqueId=" + getArticleNumeriqueId() +
            "}";
    }
}
