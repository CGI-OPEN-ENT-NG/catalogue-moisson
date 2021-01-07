package fr.openent.moisson.service.dto;

import fr.openent.moisson.domain.enumeration.DisponibiliteEnum;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link fr.openent.moisson.domain.Disponibilite} entity. This class is used
 * in {@link fr.openent.moisson.web.rest.DisponibiliteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /disponibilites?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DisponibiliteCriteria implements Serializable, Criteria {
    /**
     * Class for filtering DisponibiliteEnum
     */
    public static class DisponibiliteEnumFilter extends Filter<DisponibiliteEnum> {

        public DisponibiliteEnumFilter() {
        }

        public DisponibiliteEnumFilter(DisponibiliteEnumFilter filter) {
            super(filter);
        }

        @Override
        public DisponibiliteEnumFilter copy() {
            return new DisponibiliteEnumFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter commentaire;

    private InstantFilter dateDisponibilite;

    private BooleanFilter commandable;

    private DisponibiliteEnumFilter valeur;

    private LongFilter articlePapierId;

    private LongFilter articleNumeriqueId;

    public DisponibiliteCriteria() {
    }

    public DisponibiliteCriteria(DisponibiliteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.commentaire = other.commentaire == null ? null : other.commentaire.copy();
        this.dateDisponibilite = other.dateDisponibilite == null ? null : other.dateDisponibilite.copy();
        this.commandable = other.commandable == null ? null : other.commandable.copy();
        this.valeur = other.valeur == null ? null : other.valeur.copy();
        this.articlePapierId = other.articlePapierId == null ? null : other.articlePapierId.copy();
        this.articleNumeriqueId = other.articleNumeriqueId == null ? null : other.articleNumeriqueId.copy();
    }

    @Override
    public DisponibiliteCriteria copy() {
        return new DisponibiliteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(StringFilter commentaire) {
        this.commentaire = commentaire;
    }

    public InstantFilter getDateDisponibilite() {
        return dateDisponibilite;
    }

    public void setDateDisponibilite(InstantFilter dateDisponibilite) {
        this.dateDisponibilite = dateDisponibilite;
    }

    public BooleanFilter getCommandable() {
        return commandable;
    }

    public void setCommandable(BooleanFilter commandable) {
        this.commandable = commandable;
    }

    public DisponibiliteEnumFilter getValeur() {
        return valeur;
    }

    public void setValeur(DisponibiliteEnumFilter valeur) {
        this.valeur = valeur;
    }

    public LongFilter getArticlePapierId() {
        return articlePapierId;
    }

    public void setArticlePapierId(LongFilter articlePapierId) {
        this.articlePapierId = articlePapierId;
    }

    public LongFilter getArticleNumeriqueId() {
        return articleNumeriqueId;
    }

    public void setArticleNumeriqueId(LongFilter articleNumeriqueId) {
        this.articleNumeriqueId = articleNumeriqueId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DisponibiliteCriteria that = (DisponibiliteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(commentaire, that.commentaire) &&
            Objects.equals(dateDisponibilite, that.dateDisponibilite) &&
            Objects.equals(commandable, that.commandable) &&
            Objects.equals(valeur, that.valeur) &&
            Objects.equals(articlePapierId, that.articlePapierId) &&
            Objects.equals(articleNumeriqueId, that.articleNumeriqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        commentaire,
        dateDisponibilite,
        commandable,
        valeur,
        articlePapierId,
        articleNumeriqueId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DisponibiliteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (commentaire != null ? "commentaire=" + commentaire + ", " : "") +
                (dateDisponibilite != null ? "dateDisponibilite=" + dateDisponibilite + ", " : "") +
                (commandable != null ? "commandable=" + commandable + ", " : "") +
                (valeur != null ? "valeur=" + valeur + ", " : "") +
                (articlePapierId != null ? "articlePapierId=" + articlePapierId + ", " : "") +
                (articleNumeriqueId != null ? "articleNumeriqueId=" + articleNumeriqueId + ", " : "") +
            "}";
    }

}
