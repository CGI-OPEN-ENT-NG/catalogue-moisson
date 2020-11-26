package fr.openent.moisson.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import fr.openent.moisson.domain.enumeration.TypeLicence;

/**
 * A DTO for the {@link fr.openent.moisson.domain.Lep} entity.
 */
public class LepDTO implements Serializable {
    
    private Long id;

    @Size(min = 13, max = 13)
    private String ean;

    private String description;

    private TypeLicence typeLicence;

    private String titre;

    private String duree;


    private Long offreId;
    
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeLicence getTypeLicence() {
        return typeLicence;
    }

    public void setTypeLicence(TypeLicence typeLicence) {
        this.typeLicence = typeLicence;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public Long getOffreId() {
        return offreId;
    }

    public void setOffreId(Long offreId) {
        this.offreId = offreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LepDTO)) {
            return false;
        }

        return id != null && id.equals(((LepDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LepDTO{" +
            "id=" + getId() +
            ", ean='" + getEan() + "'" +
            ", description='" + getDescription() + "'" +
            ", typeLicence='" + getTypeLicence() + "'" +
            ", titre='" + getTitre() + "'" +
            ", duree='" + getDuree() + "'" +
            ", offreId=" + getOffreId() +
            "}";
    }
}
