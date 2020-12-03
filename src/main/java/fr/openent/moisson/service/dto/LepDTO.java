package fr.openent.moisson.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link fr.openent.moisson.domain.Lep} entity.
 */
public class LepDTO implements Serializable {

    private Long id;

    @Size(min = 13, max = 13)
    private String ean;

    private String description;

    private String titre;

    private String duree;

    private Long offreId;

    private Long licenceId;

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
            ", titre='" + getTitre() + "'" +
            ", duree='" + getDuree() + "'" +
            ", offreId=" + getOffreId() +
            ", licenceId=" + getLicenceId() +
            "}";
    }
}
