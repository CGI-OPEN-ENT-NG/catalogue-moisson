package fr.openent.moisson.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Licence.
 */
@Entity
@Table(name = "licence")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "licence")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Licence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "valeur")
    @JsonProperty("VALEUR")
    private String valeur;

    @OneToOne(mappedBy = "licence")
    @JsonIgnore
    private Offre offre;

    @OneToOne(mappedBy = "licence")
    @JsonIgnore
    private Lep lep;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValeur() {
        return valeur;
    }

    public Licence valeur(String valeur) {
        this.valeur = valeur;
        return this;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public Offre getOffre() {
        return offre;
    }

    public Licence offre(Offre offre) {
        this.offre = offre;
        return this;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public Lep getLep() {
        return lep;
    }

    public Licence lep(Lep lep) {
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
        if (!(o instanceof Licence)) {
            return false;
        }
        return id != null && id.equals(((Licence) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Licence{" +
            "id=" + getId() +
            ", valeur='" + getValeur() + "'" +
            "}";
    }
}
