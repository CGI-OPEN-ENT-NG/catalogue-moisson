package fr.openent.moisson.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link fr.openent.moisson.domain.Niveau} entity.
 */
public class NiveauDTO implements Serializable {
    
    private Long id;

    private String libelle;

    private String terme;

    private String concept;


    private Long articleNumeriqueId;
    
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NiveauDTO)) {
            return false;
        }

        return id != null && id.equals(((NiveauDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NiveauDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", terme='" + getTerme() + "'" +
            ", concept='" + getConcept() + "'" +
            ", articleNumeriqueId=" + getArticleNumeriqueId() +
            "}";
    }
}
