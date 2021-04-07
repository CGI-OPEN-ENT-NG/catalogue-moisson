package fr.openent.moisson.domain;

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
 * A Classe.
 */
@Entity
@Table(name = "classe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@org.springframework.data.elasticsearch.annotations.Document(indexName = "classe")
@Setting(settingPath = "/settings/settings.json")
public class Classe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle")
    @JsonProperty("LIBELLE")
    @Field(type = FieldType.Keyword, normalizer = "lower_normalizer")
    private String libelle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "classes", allowSetters = true)
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

    public Classe libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public ArticleNumerique getArticleNumerique() {
        return articleNumerique;
    }

    public Classe articleNumerique(ArticleNumerique articleNumerique) {
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
        if (!(o instanceof Classe)) {
            return false;
        }
        return id != null && id.equals(((Classe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Classe{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
