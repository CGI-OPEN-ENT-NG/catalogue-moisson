package fr.openent.moisson.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link fr.openent.moisson.domain.Tva} entity.
 */
public class TvaDTO implements Serializable {
    
    private Long id;

    private BigDecimal taux;

    private BigDecimal pourcent;


    private Long offreId;

    private Long articlePapierId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTaux() {
        return taux;
    }

    public void setTaux(BigDecimal taux) {
        this.taux = taux;
    }

    public BigDecimal getPourcent() {
        return pourcent;
    }

    public void setPourcent(BigDecimal pourcent) {
        this.pourcent = pourcent;
    }

    public Long getOffreId() {
        return offreId;
    }

    public void setOffreId(Long offreId) {
        this.offreId = offreId;
    }

    public Long getArticlePapierId() {
        return articlePapierId;
    }

    public void setArticlePapierId(Long articlePapierId) {
        this.articlePapierId = articlePapierId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TvaDTO)) {
            return false;
        }

        return id != null && id.equals(((TvaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TvaDTO{" +
            "id=" + getId() +
            ", taux=" + getTaux() +
            ", pourcent=" + getPourcent() +
            ", offreId=" + getOffreId() +
            ", articlePapierId=" + getArticlePapierId() +
            "}";
    }
}
