package fr.openent.moisson.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link fr.openent.moisson.domain.Classe} entity.
 */
public class ClasseDTO implements Serializable {
    
    private Long id;

    private String libelle;


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
        if (!(o instanceof ClasseDTO)) {
            return false;
        }

        return id != null && id.equals(((ClasseDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClasseDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", articleNumeriqueId=" + getArticleNumeriqueId() +
            "}";
    }
}
