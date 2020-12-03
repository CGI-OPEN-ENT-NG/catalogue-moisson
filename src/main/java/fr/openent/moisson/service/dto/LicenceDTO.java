package fr.openent.moisson.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link fr.openent.moisson.domain.Licence} entity.
 */
public class LicenceDTO implements Serializable {
    
    private Long id;

    private String valeur;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LicenceDTO)) {
            return false;
        }

        return id != null && id.equals(((LicenceDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LicenceDTO{" +
            "id=" + getId() +
            ", valeur='" + getValeur() + "'" +
            "}";
    }
}
