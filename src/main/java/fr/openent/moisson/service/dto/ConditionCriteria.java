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
 * Criteria class for the {@link fr.openent.moisson.domain.Condition} entity. This class is used
 * in {@link fr.openent.moisson.web.rest.ConditionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /conditions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ConditionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter gratuite;

    private IntegerFilter conditionGratuite;

    private LongFilter lepId;

    public ConditionCriteria() {
    }

    public ConditionCriteria(ConditionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.gratuite = other.gratuite == null ? null : other.gratuite.copy();
        this.conditionGratuite = other.conditionGratuite == null ? null : other.conditionGratuite.copy();
        this.lepId = other.lepId == null ? null : other.lepId.copy();
    }

    @Override
    public ConditionCriteria copy() {
        return new ConditionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getGratuite() {
        return gratuite;
    }

    public void setGratuite(IntegerFilter gratuite) {
        this.gratuite = gratuite;
    }

    public IntegerFilter getConditionGratuite() {
        return conditionGratuite;
    }

    public void setConditionGratuite(IntegerFilter conditionGratuite) {
        this.conditionGratuite = conditionGratuite;
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
        final ConditionCriteria that = (ConditionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(gratuite, that.gratuite) &&
            Objects.equals(conditionGratuite, that.conditionGratuite) &&
            Objects.equals(lepId, that.lepId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        gratuite,
        conditionGratuite,
        lepId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConditionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (gratuite != null ? "gratuite=" + gratuite + ", " : "") +
                (conditionGratuite != null ? "conditionGratuite=" + conditionGratuite + ", " : "") +
                (lepId != null ? "lepId=" + lepId + ", " : "") +
            "}";
    }

}
