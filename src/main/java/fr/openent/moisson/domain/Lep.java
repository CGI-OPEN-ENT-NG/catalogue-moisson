package fr.openent.moisson.domain;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.NaturalId;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import fr.openent.moisson.domain.enumeration.TypeLicence;

/**
 * A Lep.
 */
@Entity
@Table(name = "lep")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "lep")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Lep implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NaturalId
    @Size(min = 13, max = 13)
    @Column(name = "ean", length = 13)
    @JsonProperty("EAN")
    private String ean;

    @Column(name = "description", length = 65000)
    @JsonProperty("DESCRIPTION")
    private String description;

    @Column(name = "titre")
    @JsonProperty("TITRE")
    private String titre;

    @Column(name = "duree")
    @JsonProperty("DUREE")
    private String duree;

    @OneToMany(mappedBy = "lep", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    @JsonProperty("CONDITIONS")
    private Set<Condition> conditions = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "offre_id", nullable = false)
    @JsonIgnoreProperties(value = "leps", allowSetters = true)
    @JsonIgnore
    private Offre offre;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @JsonProperty("TYPELICENCE")
    private Licence licence;


    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEan() {
        return ean;
    }

    public Lep ean(String ean) {
        this.ean = ean;
        return this;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getDescription() {
        return description;
    }

    public Lep description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitre() {
        return titre;
    }

    public Lep titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDuree() {
        return duree;
    }

    public Lep duree(String duree) {
        this.duree = duree;
        return this;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public Set<Condition> getConditions() {
        return conditions;
    }

    public Lep conditions(Set<Condition> conditions) {
        this.conditions = conditions;
        return this;
    }

    public Lep addCondition(Condition condition) {
        this.conditions.add(condition);
        condition.setLep(this);
        return this;
    }

    public Lep removeCondition(Condition condition) {
        this.conditions.remove(condition);
        condition.setLep(null);
        return this;
    }

    public void setConditions(Set<Condition> conditions) {
        this.conditions = conditions;
    }

    public Offre getOffre() {
        return offre;
    }

    public Lep offre(Offre offre) {
        this.offre = offre;
        return this;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public Licence getLicence() {
        return licence;
    }

    public Lep licence(Licence licence) {
        this.licence = licence;
        return this;
    }

    public void setLicence(Licence licence) {
        this.licence = licence;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lep)) {
            return false;
        }
        return id != null && id.equals(((Lep) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lep{" +
            "id=" + getId() +
            ", ean='" + getEan() + "'" +
            ", description='" + getDescription() + "'" +
            ", titre='" + getTitre() + "'" +
            ", duree='" + getDuree() + "'" +
            "}";
    }
}
