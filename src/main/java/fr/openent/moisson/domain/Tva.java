package fr.openent.moisson.domain;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Tva.
 */
@Entity
@Table(name = "tva")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "tva")
// Pour les gérer références cycliques au lieu de  @JsonManagedReference et @JsonBackReference
// Il faut ajouter  @JsonProperty("id") au niveau de l'id
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Tva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @JsonIgnore
    @JsonProperty("id")
    private Long id;

    @Column(name = "taux", precision = 21, scale = 2)
    @JsonProperty("TAUX")
    private BigDecimal taux;

    @Column(name = "pourcent", precision = 21, scale = 2)
    @JsonProperty("POURCENT")
    private BigDecimal pourcent;

    // Eager par défaut mettre donc lazy pour éviter récupération offre
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "tvas", allowSetters = true)
    @JoinColumn(name = "offre_id", nullable = false)
    private Offre offre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_papier_id", nullable = false)
    @JsonIgnoreProperties(value = "tvas", allowSetters = true)
    private ArticlePapier articlePapier;

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

    public ArticlePapier getArticlePapier() {
        return articlePapier;
    }

    public Tva articlePapier(ArticlePapier articlePapier) {
        this.articlePapier = articlePapier;
        return this;
    }

    public void setArticlePapier(ArticlePapier articlePapier) {
        this.articlePapier = articlePapier;
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
