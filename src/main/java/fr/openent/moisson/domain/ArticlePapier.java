package fr.openent.moisson.domain;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import fr.openent.moisson.service.mapper.json.MoissonCustomInstantDeserializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;


/**
 * A ArticlePapier.
 */
@Entity
@Table(name = "article_papier")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "articlepapier")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Setting(settingPath = "/settings/settings.json")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticlePapier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @JsonIgnore
    @JsonProperty("id")
    @Field
    private Long id;

    @Size(min = 13, max = 13)
    @Column(name = "ean", length = 13)
    @NaturalId
    @JsonProperty("EAN")
    @Field
    private String ean;

    @Column(name = "ark")
    @JsonProperty("ARK")
    @Field(type = FieldType.Keyword, normalizer = "lower_normalizer")
    private String ark;

    @Column(name = "titre", length = 1024)
    @JsonProperty("TITRE")
    @Field(type = FieldType.Keyword, normalizer = "lower_normalizer")
    private String titre;

    @Column(name = "editeur")
    @JsonProperty("EDITEUR")
    @Field(type = FieldType.Keyword, normalizer = "lower_normalizer")
    private String editeur;

    @Column(name = "auteur", length = 1024)
    @JsonProperty("AUTEUR")
    @Field(type = FieldType.Keyword, normalizer = "lower_normalizer")
    private String auteur;

    @Column(name = "reference_editeur")
    @JsonProperty("REF_EDITEUR")
    @Field
    private String referenceEditeur;

    @Column(name = "collection")
    @JsonProperty("COLLECTION")
    @Field
    private String collection;

    @Column(name = "distributeur")
    @JsonProperty("DISTRIBUTEUR")
    @Field(type = FieldType.Keyword, normalizer = "lower_normalizer")
    private String distributeur;

    @Column(name = "url_couverture", length = 1024)
    @JsonProperty("URL_COUVERTURE")
    @Field
    private String urlCouverture;

    @Column(name = "date_parution")
    @JsonProperty("DATE_PARUTION")
    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = MoissonCustomInstantDeserializer.class)
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSX || uuuu-MM-dd'T'HH:mm:ss.SSSXX || uuuu-MM-dd'T'HH:mm:ss.SSSXXX || uuuu-MM-dd'T'HH:mm:ss.SSSXXXX || uuuu-MM-dd'T'HH:mm:ss.SSSXXXXX")
    private Instant dateParution;

    @Column(name = "prix_ht", precision = 21, scale = 2)
    @JsonProperty("PXHT")
    @Field(type = FieldType.Scaled_Float, scalingFactor = 100)
    private BigDecimal prixHT;

    @Column(name = "description", length = 65000)
    @JsonProperty("DESCRIPTION")
    @Field
    private String description;

    @Column(name = "type")
    @JsonProperty("TYPE")
    @Field(type = FieldType.Keyword, normalizer = "lower_normalizer")
    private String type;

    @OneToMany(mappedBy = "articlePapier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    @JsonProperty("TVA")
    @Field(type = FieldType.Nested)
    // @JsonManagedReference // @JsonManagedReference sur la collection et @JsonBackReference sur la référence (Tva)
    private Set<Tva> tvas = new HashSet<>();

    @OneToMany(mappedBy = "articlePapier",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    @JsonProperty("DISCIPLINE")
    @Field(type = FieldType.Nested)
    private Set<Discipline> disciplines = new HashSet<>();

    @OneToMany(mappedBy = "articlePapier",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    @JsonProperty("NIVEAU")
    @Field(type = FieldType.Nested, name = "niveaux")
    private Set<Niveau> niveaus = new HashSet<>();

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @JsonProperty("DISPONIBILITE")
    @Field(type = FieldType.Nested )
    private Disponibilite disponibilite;

    @Transient
    private BigDecimal prixTTC = BigDecimal.ZERO;

    @Column(name = "bookseller")
    @Field(type = FieldType.Keyword, normalizer = "lower_normalizer")
    private String bookseller;

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

    public void setEan(String ean) {
        this.ean = ean;
    }

    public ArticlePapier ean(String ean) {
        this.ean = ean;
        return this;
    }

    public String getArk() {
        return ark;
    }

    public void setArk(String ark) {
        this.ark = ark;
    }

    public ArticlePapier ark(String ark) {
        this.ark = ark;
        return this;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public ArticlePapier titre(String titre) {
        this.titre = titre;
        return this;
    }

    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public ArticlePapier editeur(String editeur) {
        this.editeur = editeur;
        return this;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public ArticlePapier auteur(String auteur) {
        this.auteur = auteur;
        return this;
    }

    public String getReferenceEditeur() {
        return referenceEditeur;
    }

    public void setReferenceEditeur(String referenceEditeur) {
        this.referenceEditeur = referenceEditeur;
    }

    public ArticlePapier referenceEditeur(String referenceEditeur) {
        this.referenceEditeur = referenceEditeur;
        return this;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public ArticlePapier collection(String collection) {
        this.collection = collection;
        return this;
    }

    public String getDistributeur() {
        return distributeur;
    }

    public void setDistributeur(String distributeur) {
        this.distributeur = distributeur;
    }

    public ArticlePapier distributeur(String distributeur) {
        this.distributeur = distributeur;
        return this;
    }

    public String getUrlCouverture() {
        return urlCouverture;
    }

    public void setUrlCouverture(String urlCouverture) {
        this.urlCouverture = urlCouverture;
    }

    public ArticlePapier urlCouverture(String urlCouverture) {
        this.urlCouverture = urlCouverture;
        return this;
    }

    public Instant getDateParution() {
        return dateParution;
    }

    public void setDateParution(Instant dateParution) {
        this.dateParution = dateParution;
    }

    public ArticlePapier dateParution(Instant dateParution) {
        this.dateParution = dateParution;
        return this;
    }

    public BigDecimal getPrixHT() {
        return prixHT;
    }

    public void setPrixHT(BigDecimal prixHT) {
        this.prixHT = prixHT;
    }

    public ArticlePapier prixHT(BigDecimal prixHT) {
        this.prixHT = prixHT;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArticlePapier description(String description) {
        this.description = description;
        return this;
    }

    public String getType() {  return type; }

    public void setType(String type) { this.type = type; }

    public ArticlePapier type(String type) {
        this.type = type;
        return this;
    }

    public Set<Tva> getTvas() {
        return tvas;
    }

    public void setTvas(Set<Tva> tvas) {
        this.tvas = tvas;
    }

    public ArticlePapier tvas(Set<Tva> tvas) {
        this.tvas = tvas;
        return this;
    }

    public ArticlePapier addTva(Tva tva) {
        this.tvas.add(tva);
        tva.setArticlePapier(this);
        return this;
    }

    public ArticlePapier removeTva(Tva tva) {
        this.tvas.remove(tva);
        tva.setArticlePapier(null);
        return this;
    }

    public void removeTvas() {
        Iterator<Tva> iterator = this.tvas.iterator();
        while (iterator.hasNext()) {
            Tva tva = iterator.next();
            tva.setArticlePapier(null);
            iterator.remove();
        }
    }

    public Disponibilite getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(Disponibilite disponibilite) {
        this.disponibilite = disponibilite;
    }

    public ArticlePapier disponibilite(Disponibilite disponibilite) {
        this.disponibilite = disponibilite;
        return this;
    }

    @PostLoad
    private void postLoad() {
        BigDecimal prixHT = this.getPrixHT();
        BigDecimal pxTTC = this.getPrixHT();
        for (Tva tva : this.getTvas()) {
            var innerTva = prixHT.multiply(tva.getTaux().multiply(tva.getPourcent())).divide(new BigDecimal("10000"));
            pxTTC = pxTTC.add(innerTva);
        }
        this.prixTTC = pxTTC;
    }

    public BigDecimal getPrixTTC() {
        return prixTTC;
    }

    public void setPrixTTC(BigDecimal prixTTC) {
        this.prixTTC = prixTTC;
    }

    public ArticlePapier prixTTC(BigDecimal prixTTC) {
        this.prixTTC = prixTTC;
        return this;
    }

    public Set<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(Set<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    public ArticlePapier disciplines(Set<Discipline> disciplines) {
        this.disciplines = disciplines;
        return this;
    }

    public ArticlePapier addDiscipline(Discipline discipline) {
        this.disciplines.add(discipline);
        discipline.setArticlePapier(this);
        return this;
    }
    public ArticlePapier removeDiscipline(Discipline discipline) {
        this.disciplines.remove(discipline);
        discipline.setArticlePapier(null);
        return this;
    }

    public Set<Niveau> getNiveaus() {
        return niveaus;
    }

    public void setNiveaus(Set<Niveau> niveaus) {
        this.niveaus = niveaus;
    }

    public ArticlePapier niveaus(Set<Niveau> niveaus) {
        this.niveaus = niveaus;
        return this;
    }

    public ArticlePapier addNiveau(Niveau niveau) {
        this.niveaus.add(niveau);
        niveau.setArticlePapier(this);
        return this;
    }

    public ArticlePapier removeNiveau(Niveau niveau) {
        this.niveaus.remove(niveau);
        niveau.setArticlePapier(null);
        return this;
    }

    public String getBookseller() {
        return bookseller;
    }

    public ArticlePapier setBookseller(String library) {
        this.bookseller = library;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticlePapier)) {
            return false;
        }
        return id != null && id.equals(((ArticlePapier) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticlePapier{" +
            "id=" + getId() +
            ", ean='" + getEan() + "'" +
            ", ark='" + getArk() + "'" +
            ", titre='" + getTitre() + "'" +
            ", editeur='" + getEditeur() + "'" +
            ", auteur='" + getAuteur() + "'" +
            ", referenceEditeur='" + getReferenceEditeur() + "'" +
            ", collection='" + getCollection() + "'" +
            ", distributeur='" + getDistributeur() + "'" +
            ", urlCouverture='" + getUrlCouverture() + "'" +
            ", dateParution='" + getDateParution() + "'" +
            ", prixHT=" + getPrixHT() +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
