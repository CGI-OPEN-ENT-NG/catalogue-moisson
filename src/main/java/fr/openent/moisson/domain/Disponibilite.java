package fr.openent.moisson.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.openent.moisson.domain.enumeration.DisponibiliteEnum;
import fr.openent.moisson.service.mapper.json.MoissonCustomInstantDeserializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Disponibilite.
 */
@Entity
@Table(name = "disponibilite")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
// @org.springframework.data.elasticsearch.annotations.Document(indexName = "disponibilite")
public class Disponibilite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "commentaire")
    @JsonProperty("COMMENTAIRE")
    @Field
    private String commentaire;

    @Column(name = "date_disponibilite")
    @JsonProperty("DATE_DISPO")
    @JsonDeserialize(using = MoissonCustomInstantDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSX || uuuu-MM-dd'T'HH:mm:ss.SSSXX || uuuu-MM-dd'T'HH:mm:ss.SSSXXX || uuuu-MM-dd'T'HH:mm:ss.SSSXXXX || uuuu-MM-dd'T'HH:mm:ss.SSSXXXXX")
    private Instant dateDisponibilite;

    @Column(name = "commandable")
    @JsonProperty("COMMANDABLE")
    @Field
    private Boolean commandable;

    // @Enumerated(EnumType.STRING)
    @Column(name = "valeur", columnDefinition = "VARCHAR(255) default 'false'")
    @JsonProperty("VALEUR")
    @Field
    private String valeur;

    @OneToOne(mappedBy = "disponibilite")
    @JsonIgnore
    private ArticlePapier articlePapier;

    @OneToOne(mappedBy = "disponibilite")
    @JsonIgnore
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

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Disponibilite commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public Instant getDateDisponibilite() {
        return dateDisponibilite;
    }

    public void setDateDisponibilite(Instant dateDisponibilite) {
        this.dateDisponibilite = dateDisponibilite;
    }

    public Disponibilite dateDisponibilite(Instant dateDisponibilite) {
        this.dateDisponibilite = dateDisponibilite;
        return this;
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

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public Disponibilite valeur(String valeur) {
        this.valeur = valeur;
        return this;
    }

    public ArticlePapier getArticlePapier() {
        return articlePapier;
    }

    public void setArticlePapier(ArticlePapier articlePapier) {
        this.articlePapier = articlePapier;
    }

    public Disponibilite articlePapier(ArticlePapier articlePapier) {
        this.articlePapier = articlePapier;
        return this;
    }

    public ArticleNumerique getArticleNumerique() {
        return articleNumerique;
    }

    public void setArticleNumerique(ArticleNumerique articleNumerique) {
        this.articleNumerique = articleNumerique;
    }

    public Disponibilite articleNumerique(ArticleNumerique articleNumerique) {
        this.articleNumerique = articleNumerique;
        return this;
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
