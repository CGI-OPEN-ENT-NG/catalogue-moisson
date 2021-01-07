package fr.openent.moisson.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link fr.openent.moisson.domain.Tva} entity. This class is used
 * in {@link fr.openent.moisson.web.rest.TvaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tvas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TvaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter taux;

    private BigDecimalFilter pourcent;

    private LongFilter offreId;

    private LongFilter articlePapierId;

    public TvaCriteria() {
    }

    public TvaCriteria(TvaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.taux = other.taux == null ? null : other.taux.copy();
        this.pourcent = other.pourcent == null ? null : other.pourcent.copy();
        this.offreId = other.offreId == null ? null : other.offreId.copy();
        this.articlePapierId = other.articlePapierId == null ? null : other.articlePapierId.copy();
    }

    @Override
    public TvaCriteria copy() {
        return new TvaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getTaux() {
        return taux;
    }

    public void setTaux(BigDecimalFilter taux) {
        this.taux = taux;
    }

    public BigDecimalFilter getPourcent() {
        return pourcent;
    }

    public void setPourcent(BigDecimalFilter pourcent) {
        this.pourcent = pourcent;
    }

    public LongFilter getOffreId() {
        return offreId;
    }

    public void setOffreId(LongFilter offreId) {
        this.offreId = offreId;
    }

    public LongFilter getArticlePapierId() {
        return articlePapierId;
    }

    public void setArticlePapierId(LongFilter articlePapierId) {
        this.articlePapierId = articlePapierId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TvaCriteria that = (TvaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(taux, that.taux) &&
            Objects.equals(pourcent, that.pourcent) &&
            Objects.equals(offreId, that.offreId) &&
            Objects.equals(articlePapierId, that.articlePapierId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        taux,
        pourcent,
        offreId,
        articlePapierId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TvaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (taux != null ? "taux=" + taux + ", " : "") +
                (pourcent != null ? "pourcent=" + pourcent + ", " : "") +
                (offreId != null ? "offreId=" + offreId + ", " : "") +
                (articlePapierId != null ? "articlePapierId=" + articlePapierId + ", " : "") +
            "}";
    }

}
