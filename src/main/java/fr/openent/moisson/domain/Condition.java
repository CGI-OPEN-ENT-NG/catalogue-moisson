package fr.openent.moisson.domain;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Condition.
 */
@Entity
@Table(name = "condition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
// @org.springframework.data.elasticsearch.annotations.Document(indexName = "condition")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Condition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "gratuite")
    @JsonProperty("GRATUITE")
   @Field
    private Integer gratuite;

    @Column(name = "condition_gratuite")
    @JsonProperty("CONDITIONGRATUITE")
   @Field
    private Integer conditionGratuite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "conditions", allowSetters = true)
    @JsonIgnore
    @JoinColumn(name = "lep_id", nullable = false)
    private Lep lep;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGratuite() {
        return gratuite;
    }

    public Condition gratuite(Integer gratuite) {
        this.gratuite = gratuite;
        return this;
    }

    public void setGratuite(Integer gratuite) {
        this.gratuite = gratuite;
    }

    public Integer getConditionGratuite() {
        return conditionGratuite;
    }

    public Condition conditionGratuite(Integer conditionGratuite) {
        this.conditionGratuite = conditionGratuite;
        return this;
    }

    public void setConditionGratuite(Integer conditionGratuite) {
        this.conditionGratuite = conditionGratuite;
    }

    public Lep getLep() {
        return lep;
    }

    public Condition lep(Lep lep) {
        this.lep = lep;
        return this;
    }

    public void setLep(Lep lep) {
        this.lep = lep;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Condition)) {
            return false;
        }
        return id != null && id.equals(((Condition) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Condition{" +
            "id=" + getId() +
            ", gratuite=" + getGratuite() +
            ", conditionGratuite=" + getConditionGratuite() +
            "}";
    }
}
