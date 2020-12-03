package fr.openent.moisson.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Discipline.
 */
@Entity
@Table(name = "discipline")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "discipline")
public class Discipline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle")
    @JsonProperty("DISCIPLINE")
    private String libelle;

    @Column(name = "terme")
    @JsonIgnore
    private String terme;

    @Column(name = "concept")
    @JsonIgnore
    private String concept;

    @ManyToOne
    @JsonIgnoreProperties(value = "disciplines", allowSetters = true)
    @JsonBackReference
    @JoinColumn(name = "article_numerique_id", nullable = false)
    private ArticleNumerique articleNumerique;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Discipline libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getTerme() {
        return terme;
    }

    public Discipline terme(String terme) {
        this.terme = terme;
        return this;
    }

    public void setTerme(String terme) {
        this.terme = terme;
    }

    public String getConcept() {
        return concept;
    }

    public Discipline concept(String concept) {
        this.concept = concept;
        return this;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public ArticleNumerique getArticleNumerique() {
        return articleNumerique;
    }

    public Discipline articleNumerique(ArticleNumerique articleNumerique) {
        this.articleNumerique = articleNumerique;
        return this;
    }

    public void setArticleNumerique(ArticleNumerique articleNumerique) {
        this.articleNumerique = articleNumerique;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Discipline)) {
            return false;
        }
        return id != null && id.equals(((Discipline) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Discipline{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", terme='" + getTerme() + "'" +
            ", concept='" + getConcept() + "'" +
            "}";
    }
}
