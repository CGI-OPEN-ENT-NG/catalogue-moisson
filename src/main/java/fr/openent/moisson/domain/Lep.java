package fr.openent.moisson.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

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
public class Lep implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(min = 13, max = 13)
    @Column(name = "ean", length = 13)
    private String ean;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_licence")
    private TypeLicence typeLicence;

    @Column(name = "titre")
    private String titre;

    @Column(name = "duree")
    private String duree;

    @OneToMany(mappedBy = "lep")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Condition> conditions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "leps", allowSetters = true)
    private Offre offre;

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

    public TypeLicence getTypeLicence() {
        return typeLicence;
    }

    public Lep typeLicence(TypeLicence typeLicence) {
        this.typeLicence = typeLicence;
        return this;
    }

    public void setTypeLicence(TypeLicence typeLicence) {
        this.typeLicence = typeLicence;
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
            ", typeLicence='" + getTypeLicence() + "'" +
            ", titre='" + getTitre() + "'" +
            ", duree='" + getDuree() + "'" +
            "}";
    }
}
