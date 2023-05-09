package fr.openent.moisson.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Discipline.
 */
@Entity
@Table(name = "discipline")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
// @org.springframework.data.elasticsearch.annotations.Document(indexName = "discipline")
@Setting(settingPath = "/settings/settings.json")
public class Discipline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle")
    @JsonProperty("LIBELLE")
    @Field(type = FieldType.Keyword, normalizer = "lower_normalizer")
    private String libelle;

    @Column(name = "terme")
    @JsonIgnore
    @Field
    private String terme;

    @Column(name = "concept")
    @JsonIgnore
    @Field
    private String concept;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "disciplines", allowSetters = true)
    @JoinColumn(name = "article_numerique_id")
    private ArticleNumerique articleNumerique;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "disciplines", allowSetters = true)
    @JoinColumn(name = "article_papier_id")
    private ArticlePapier articlePapier;

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

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Discipline libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public String getTerme() {
        return terme;
    }

    public void setTerme(String terme) {
        this.terme = terme;
    }

    public Discipline terme(String terme) {
        this.terme = terme;
        return this;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public Discipline concept(String concept) {
        this.concept = concept;
        return this;
    }

    public ArticleNumerique getArticleNumerique() {
        return articleNumerique;
    }

    public void setArticleNumerique(ArticleNumerique articleNumerique) {
        this.articleNumerique = articleNumerique;
    }

    public Discipline articleNumerique(ArticleNumerique articleNumerique) {
        this.articleNumerique = articleNumerique;
        return this;
    }

    public ArticlePapier getArticlePapier() {
        return articlePapier;
    }

    public void setArticlePapier(ArticlePapier articlePapier) {
        this.articlePapier = articlePapier;
    }

    public Discipline articlePapier(ArticlePapier articlePapier) {
        this.articlePapier = articlePapier;
        return this;
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
