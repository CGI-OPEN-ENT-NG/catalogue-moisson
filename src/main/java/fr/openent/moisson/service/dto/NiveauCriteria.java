package fr.openent.moisson.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link fr.openent.moisson.domain.Niveau} entity. This class is used
 * in {@link fr.openent.moisson.web.rest.NiveauResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /niveaus?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NiveauCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter libelle;

    private StringFilter terme;

    private StringFilter concept;

    private LongFilter articleNumeriqueId;

    public NiveauCriteria() {
    }

    public NiveauCriteria(NiveauCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.libelle = other.libelle == null ? null : other.libelle.copy();
        this.terme = other.terme == null ? null : other.terme.copy();
        this.concept = other.concept == null ? null : other.concept.copy();
        this.articleNumeriqueId = other.articleNumeriqueId == null ? null : other.articleNumeriqueId.copy();
    }

    @Override
    public NiveauCriteria copy() {
        return new NiveauCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLibelle() {
        return libelle;
    }

    public void setLibelle(StringFilter libelle) {
        this.libelle = libelle;
    }

    public StringFilter getTerme() {
        return terme;
    }

    public void setTerme(StringFilter terme) {
        this.terme = terme;
    }

    public StringFilter getConcept() {
        return concept;
    }

    public void setConcept(StringFilter concept) {
        this.concept = concept;
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
        final NiveauCriteria that = (NiveauCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(terme, that.terme) &&
            Objects.equals(concept, that.concept) &&
            Objects.equals(articleNumeriqueId, that.articleNumeriqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        libelle,
        terme,
        concept,
        articleNumeriqueId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NiveauCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (libelle != null ? "libelle=" + libelle + ", " : "") +
                (terme != null ? "terme=" + terme + ", " : "") +
                (concept != null ? "concept=" + concept + ", " : "") +
                (articleNumeriqueId != null ? "articleNumeriqueId=" + articleNumeriqueId + ", " : "") +
            "}";
    }

}
