package fr.openent.moisson.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Tva.
 */
@Entity
@Table(name = "tva")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "tva")
public class Tva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "taux", precision = 21, scale = 2)
    private BigDecimal taux;

    @Column(name = "pourcent", precision = 21, scale = 2)
    private BigDecimal pourcent;

    @ManyToOne
    @JsonIgnoreProperties(value = "tvas", allowSetters = true)
    private Offre offre;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTaux() {
        return taux;
    }

    public Tva taux(BigDecimal taux) {
        this.taux = taux;
        return this;
    }

    public void setTaux(BigDecimal taux) {
        this.taux = taux;
    }

    public BigDecimal getPourcent() {
        return pourcent;
    }

    public Tva pourcent(BigDecimal pourcent) {
        this.pourcent = pourcent;
        return this;
    }

    public void setPourcent(BigDecimal pourcent) {
        this.pourcent = pourcent;
    }

    public Offre getOffre() {
        return offre;
    }

    public Tva offre(Offre offre) {
        this.offre = offre;
        return this;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tva)) {
            return false;
        }
        return id != null && id.equals(((Tva) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tva{" +
            "id=" + getId() +
            ", taux=" + getTaux() +
            ", pourcent=" + getPourcent() +
            "}";
    }
}
