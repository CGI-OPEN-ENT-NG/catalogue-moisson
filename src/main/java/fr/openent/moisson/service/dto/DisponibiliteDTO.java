package fr.openent.moisson.service.dto;

import java.time.Instant;
import java.io.Serializable;
import fr.openent.moisson.domain.enumeration.DisponibiliteEnum;

/**
 * A DTO for the {@link fr.openent.moisson.domain.Disponibilite} entity.
 */
public class DisponibiliteDTO implements Serializable {
    
    private Long id;

    private String commentaire;

    private Instant dateDisponibilite;

    private Boolean commandable;

    private DisponibiliteEnum valeur;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Instant getDateDisponibilite() {
        return dateDisponibilite;
    }

    public void setDateDisponibilite(Instant dateDisponibilite) {
        this.dateDisponibilite = dateDisponibilite;
    }

    public Boolean isCommandable() {
        return commandable;
    }

    public void setCommandable(Boolean commandable) {
        this.commandable = commandable;
    }

    public DisponibiliteEnum getValeur() {
        return valeur;
    }

    public void setValeur(DisponibiliteEnum valeur) {
        this.valeur = valeur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DisponibiliteDTO)) {
            return false;
        }

        return id != null && id.equals(((DisponibiliteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DisponibiliteDTO{" +
            "id=" + getId() +
            ", commentaire='" + getCommentaire() + "'" +
            ", dateDisponibilite='" + getDateDisponibilite() + "'" +
            ", commandable='" + isCommandable() + "'" +
            ", valeur='" + getValeur() + "'" +
            "}";
    }
}
