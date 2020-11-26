package fr.openent.moisson.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import fr.openent.moisson.domain.enumeration.TypeLicence;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

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
    /**
     * Class for filtering TypeLicence
     */
    public static class TypeLicenceFilter extends Filter<TypeLicence> {

        public TypeLicenceFilter() {
        }

        public TypeLicenceFilter(TypeLicenceFilter filter) {
            super(filter);
        }

        @Override
        public TypeLicenceFilter copy() {
            return new TypeLicenceFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter ean;

    private StringFilter description;

    private TypeLicenceFilter typeLicence;

    private StringFilter titre;

    private StringFilter duree;

    private LongFilter conditionId;

    public LepCriteria() {
    }

    public LepCriteria(LepCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.ean = other.ean == null ? null : other.ean.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.typeLicence = other.typeLicence == null ? null : other.typeLicence.copy();
        this.titre = other.titre == null ? null : other.titre.copy();
        this.duree = other.duree == null ? null : other.duree.copy();
        this.conditionId = other.conditionId == null ? null : other.conditionId.copy();
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

    public TypeLicenceFilter getTypeLicence() {
        return typeLicence;
    }

    public void setTypeLicence(TypeLicenceFilter typeLicence) {
        this.typeLicence = typeLicence;
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
            Objects.equals(typeLicence, that.typeLicence) &&
            Objects.equals(titre, that.titre) &&
            Objects.equals(duree, that.duree) &&
            Objects.equals(conditionId, that.conditionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        ean,
        description,
        typeLicence,
        titre,
        duree,
        conditionId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LepCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ean != null ? "ean=" + ean + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (typeLicence != null ? "typeLicence=" + typeLicence + ", " : "") +
                (titre != null ? "titre=" + titre + ", " : "") +
                (duree != null ? "duree=" + duree + ", " : "") +
                (conditionId != null ? "conditionId=" + conditionId + ", " : "") +
            "}";
    }

}
