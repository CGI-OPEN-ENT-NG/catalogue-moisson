package fr.openent.moisson.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

import fr.openent.moisson.domain.enumeration.DisponibiliteEnum;

/**
 * A Disponibilite.
 */
@Entity
@Table(name = "disponibilite")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "disponibilite")
public class Disponibilite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "commentaire")
    @JsonProperty("COMMENTAIRE")
    private String commentaire;

    @Column(name = "date_disponibilite")
    @JsonProperty("DATE_DISPO")
    private Instant dateDisponibilite;

    @Column(name = "commandable")
    @JsonProperty("COMMANDABLE")
    private Boolean commandable;

    @Enumerated(EnumType.STRING)
    @Column(name = "valeur")
    @JsonProperty("VALEUR")
    private DisponibiliteEnum valeur;

    @OneToOne(mappedBy = "disponibilite")
    @JsonIgnore
    @JsonBackReference
    private ArticlePapier articlePapier;

    @OneToOne(mappedBy = "disponibilite")
    @JsonIgnore
    @JsonBackReference
    private ArticleNumerique articleNumerique;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Disponibilite commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Instant getDateDisponibilite() {
        return dateDisponibilite;
    }

    public Disponibilite dateDisponibilite(Instant dateDisponibilite) {
        this.dateDisponibilite = dateDisponibilite;
        return this;
    }

    public void setDateDisponibilite(Instant dateDisponibilite) {
        this.dateDisponibilite = dateDisponibilite;
    }

    public Boolean isCommandable() {
        return commandable;
    }

    public Disponibilite commandable(Boolean commandable) {
        this.commandable = commandable;
        return this;
    }

    public void setCommandable(Boolean commandable) {
        this.commandable = commandable;
    }

    public DisponibiliteEnum getValeur() {
        return valeur;
    }

    public Disponibilite valeur(DisponibiliteEnum valeur) {
        this.valeur = valeur;
        return this;
    }

    public void setValeur(DisponibiliteEnum valeur) {
        this.valeur = valeur;
    }

    public ArticlePapier getArticlePapier() {
        return articlePapier;
    }

    public Disponibilite articlePapier(ArticlePapier articlePapier) {
        this.articlePapier = articlePapier;
        return this;
    }

    public void setArticlePapier(ArticlePapier articlePapier) {
        this.articlePapier = articlePapier;
    }

    public ArticleNumerique getArticleNumerique() {
        return articleNumerique;
    }

    public Disponibilite articleNumerique(ArticleNumerique articleNumerique) {
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
        if (!(o instanceof Disponibilite)) {
            return false;
        }
        return id != null && id.equals(((Disponibilite) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Disponibilite{" +
            "id=" + getId() +
            ", commentaire='" + getCommentaire() + "'" +
            ", dateDisponibilite='" + getDateDisponibilite() + "'" +
            ", commandable='" + isCommandable() + "'" +
            ", valeur='" + getValeur() + "'" +
            "}";
    }
}
