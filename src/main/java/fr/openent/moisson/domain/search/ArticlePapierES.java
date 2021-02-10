package fr.openent.moisson.domain.search;

import fr.openent.moisson.domain.Discipline;
import fr.openent.moisson.domain.Disponibilite;
import fr.openent.moisson.domain.Niveau;
import fr.openent.moisson.domain.Tva;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Document(indexName = "articlepapier")
public class ArticlePapierES {

    @Id
    private Long id;

    @Field
    private String ean;

    @Field
    private String ark;

    @Field
    private String titre;

    @Field
    private String editeur;

    @Field
    private String auteur;

    @Field
    private String referenceEditeur;

    @Field
    private String collection;

    @Field
    private String distributeur;

    @Field
    private String urlCouverture;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSX || uuuu-MM-dd'T'HH:mm:ss.SSSXX || uuuu-MM-dd'T'HH:mm:ss.SSSXXX || uuuu-MM-dd'T'HH:mm:ss.SSSXXXX || uuuu-MM-dd'T'HH:mm:ss.SSSXXXXX")
    private Instant dateParution;

    @Field(type = FieldType.Scaled_Float, scalingFactor = 100)
    private BigDecimal prixHT;

    @Field
    private String description;

    @Field(type = FieldType.Nested)
    private Set<Tva> tvas = new HashSet<>();

    @Field(type = FieldType.Nested)
    private Set<Discipline> disciplines = new HashSet<>();

    @Field(type = FieldType.Nested)
    private Set<Niveau> niveaus = new HashSet<>();

    @Field(type = FieldType.Nested )
    private Disponibilite disponibilite;

    @Field
    private BigDecimal prixTTC = BigDecimal.ZERO;

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

    public String getArk() {
        return ark;
    }

    public void setArk(String ark) {
        this.ark = ark;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getReferenceEditeur() {
        return referenceEditeur;
    }

    public void setReferenceEditeur(String referenceEditeur) {
        this.referenceEditeur = referenceEditeur;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getDistributeur() {
        return distributeur;
    }

    public void setDistributeur(String distributeur) {
        this.distributeur = distributeur;
    }

    public String getUrlCouverture() {
        return urlCouverture;
    }

    public void setUrlCouverture(String urlCouverture) {
        this.urlCouverture = urlCouverture;
    }

    public Instant getDateParution() {
        return dateParution;
    }

    public void setDateParution(Instant dateParution) {
        this.dateParution = dateParution;
    }

    public BigDecimal getPrixHT() {
        return prixHT;
    }

    public void setPrixHT(BigDecimal prixHT) {
        this.prixHT = prixHT;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Tva> getTvas() {
        return tvas;
    }

    public void setTvas(Set<Tva> tvas) {
        this.tvas = tvas;
    }

    public Set<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(Set<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    public Set<Niveau> getNiveaus() {
        return niveaus;
    }

    public void setNiveaus(Set<Niveau> niveaus) {
        this.niveaus = niveaus;
    }

    public Disponibilite getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(Disponibilite disponibilite) {
        this.disponibilite = disponibilite;
    }

    public BigDecimal getPrixTTC() {
        return prixTTC;
    }

    public void setPrixTTC(BigDecimal prixTTC) {
        this.prixTTC = prixTTC;
    }
}
