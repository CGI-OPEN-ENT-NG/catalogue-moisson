package fr.openent.moisson.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link fr.openent.moisson.domain.Discipline} entity.
 */
public class DisciplineDTO implements Serializable {
    
    private Long id;

    private String libelle;

    private String terme;

    private String concept;


    private Long articleNumeriqueId;

    private Long articlePapierId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getTerme() {
        return terme;
    }

    public void setTerme(String terme) {
        this.terme = terme;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public Long getArticleNumeriqueId() {
        return articleNumeriqueId;
    }

    public void setArticleNumeriqueId(Long articleNumeriqueId) {
        this.articleNumeriqueId = articleNumeriqueId;
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
        if (!(o instanceof DisciplineDTO)) {
            return false;
        }

        return id != null && id.equals(((DisciplineDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DisciplineDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", terme='" + getTerme() + "'" +
            ", concept='" + getConcept() + "'" +
            ", articleNumeriqueId=" + getArticleNumeriqueId() +
            ", articlePapierId=" + getArticlePapierId() +
            "}";
    }
}
