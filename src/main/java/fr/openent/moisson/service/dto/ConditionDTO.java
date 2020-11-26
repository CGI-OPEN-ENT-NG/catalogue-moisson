package fr.openent.moisson.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link fr.openent.moisson.domain.Condition} entity.
 */
public class ConditionDTO implements Serializable {
    
    private Long id;

    private Integer gratuite;

    private Integer conditionGratuite;


    private Long lepId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGratuite() {
        return gratuite;
    }

    public void setGratuite(Integer gratuite) {
        this.gratuite = gratuite;
    }

    public Integer getConditionGratuite() {
        return conditionGratuite;
    }

    public void setConditionGratuite(Integer conditionGratuite) {
        this.conditionGratuite = conditionGratuite;
    }

    public Long getLepId() {
        return lepId;
    }

    public void setLepId(Long lepId) {
        this.lepId = lepId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConditionDTO)) {
            return false;
        }

        return id != null && id.equals(((ConditionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConditionDTO{" +
            "id=" + getId() +
            ", gratuite=" + getGratuite() +
            ", conditionGratuite=" + getConditionGratuite() +
            ", lepId=" + getLepId() +
            "}";
    }
}
