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
 * Criteria class for the {@link fr.openent.moisson.domain.Licence} entity. This class is used
 * in {@link fr.openent.moisson.web.rest.LicenceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /licences?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LicenceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter valeur;

    private LongFilter offreId;

    private LongFilter lepId;

    public LicenceCriteria() {
    }

    public LicenceCriteria(LicenceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.valeur = other.valeur == null ? null : other.valeur.copy();
        this.offreId = other.offreId == null ? null : other.offreId.copy();
        this.lepId = other.lepId == null ? null : other.lepId.copy();
    }

    @Override
    public LicenceCriteria copy() {
        return new LicenceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getValeur() {
        return valeur;
    }

    public void setValeur(StringFilter valeur) {
        this.valeur = valeur;
    }

    public LongFilter getOffreId() {
        return offreId;
    }

    public void setOffreId(LongFilter offreId) {
        this.offreId = offreId;
    }

    public LongFilter getLepId() {
        return lepId;
    }

    public void setLepId(LongFilter lepId) {
        this.lepId = lepId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LicenceCriteria that = (LicenceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(valeur, that.valeur) &&
            Objects.equals(offreId, that.offreId) &&
            Objects.equals(lepId, that.lepId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        valeur,
        offreId,
        lepId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LicenceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (valeur != null ? "valeur=" + valeur + ", " : "") +
                (offreId != null ? "offreId=" + offreId + ", " : "") +
                (lepId != null ? "lepId=" + lepId + ", " : "") +
            "}";
    }

}
