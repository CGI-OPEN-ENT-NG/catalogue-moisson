package fr.openent.moisson.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link fr.openent.moisson.domain.Lep} entity. This class is used
 * in {@link fr.openent.moisson.web.rest.LepResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leps?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LepCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter ean;

    private StringFilter description;

    private StringFilter titre;

    private StringFilter duree;

    private LongFilter conditionId;

    private LongFilter offreId;

    private LongFilter licenceId;

    public LepCriteria() {
    }

    public LepCriteria(LepCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.ean = other.ean == null ? null : other.ean.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.titre = other.titre == null ? null : other.titre.copy();
        this.duree = other.duree == null ? null : other.duree.copy();
        this.conditionId = other.conditionId == null ? null : other.conditionId.copy();
        this.offreId = other.offreId == null ? null : other.offreId.copy();
        this.licenceId = other.licenceId == null ? null : other.licenceId.copy();
    }

    @Override
    public LepCriteria copy() {
        return new LepCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEan() {
        return ean;
    }

    public void setEan(StringFilter ean) {
        this.ean = ean;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getTitre() {
        return titre;
    }

    public void setTitre(StringFilter titre) {
        this.titre = titre;
    }

    public StringFilter getDuree() {
        return duree;
    }

    public void setDuree(StringFilter duree) {
        this.duree = duree;
    }

    public LongFilter getConditionId() {
        return conditionId;
    }

    public void setConditionId(LongFilter conditionId) {
        this.conditionId = conditionId;
    }

    public LongFilter getOffreId() {
        return offreId;
    }

    public void setOffreId(LongFilter offreId) {
        this.offreId = offreId;
    }

    public LongFilter getLicenceId() {
        return licenceId;
    }

    public void setLicenceId(LongFilter licenceId) {
        this.licenceId = licenceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LepCriteria that = (LepCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(ean, that.ean) &&
            Objects.equals(description, that.description) &&
            Objects.equals(titre, that.titre) &&
            Objects.equals(duree, that.duree) &&
            Objects.equals(conditionId, that.conditionId) &&
            Objects.equals(offreId, that.offreId) &&
            Objects.equals(licenceId, that.licenceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        ean,
        description,
        titre,
        duree,
        conditionId,
        offreId,
        licenceId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LepCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ean != null ? "ean=" + ean + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (titre != null ? "titre=" + titre + ", " : "") +
                (duree != null ? "duree=" + duree + ", " : "") +
                (conditionId != null ? "conditionId=" + conditionId + ", " : "") +
                (offreId != null ? "offreId=" + offreId + ", " : "") +
                (licenceId != null ? "licenceId=" + licenceId + ", " : "") +
            "}";
    }

}
