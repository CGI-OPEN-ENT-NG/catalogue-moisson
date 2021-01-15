package fr.openent.moisson.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A Offre.
 */
@Entity
@Table(name = "offre")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "offre")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Offre implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;

    @Size(min = 13, max = 13)
    @Column(name = "ean_libraire", length = 13)
    @JsonProperty("EANLDE")
    @Field
    private String eanLibraire;

    @Column(name = "quantite_minimale_achat")
    @JsonProperty("QTE_MINI")
    @Field
    private Integer quantiteMinimaleAchat;

    @Column(name = "prescripteur")
    @JsonProperty("PRESCRIPTEUR")
    @Field
    private Boolean prescripteur;

    @Column(name = "libelle")
    @JsonProperty("LIBELLE")
    @Field
    private String libelle;

    @Column(name = "prix_ht", precision = 21, scale = 2)
    @JsonProperty("PXHT")
    @Field(type = FieldType.Scaled_Float, scalingFactor = 100)
    private BigDecimal prixHT;

    @Column(name = "adoptant")
    @JsonProperty("ADOPTANT")
    @Field
    private Boolean adoptant;

    @Column(name = "duree")
    @JsonProperty("DUREE")
    @Field
    private String duree;

    @Column(name = "reference_editeur")
    @JsonProperty("REF_EDITEUR")
    @Field
    private String referenceEditeur;

    @OneToMany(mappedBy = "offre", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    @JsonProperty("TVA")
    @Field(type = FieldType.Nested)
    private Set<Tva> tvas = new HashSet<>();

    @OneToMany(mappedBy = "offre", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    @JsonProperty("LEP")
    @Field(type = FieldType.Nested)
    private Set<Lep> leps = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "offres", allowSetters = true)
    @JoinColumn(name = "article_numerique_id", nullable = false)
    private ArticleNumerique articleNumerique;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @JsonProperty("LICENCE")
    @Field(type = FieldType.Nested)
    private Licence licence;

    @Transient
    private BigDecimal prixTTC = BigDecimal.ZERO;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEanLibraire() {
        return eanLibraire;
    }

    public void setEanLibraire(String eanLibraire) {
        this.eanLibraire = eanLibraire;
    }

    public Offre eanLibraire(String eanLibraire) {
        this.eanLibraire = eanLibraire;
        return this;
    }

    public Integer getQuantiteMinimaleAchat() {
        return quantiteMinimaleAchat;
    }

    public void setQuantiteMinimaleAchat(Integer quantiteMinimaleAchat) {
        this.quantiteMinimaleAchat = quantiteMinimaleAchat;
    }

    public Offre quantiteMinimaleAchat(Integer quantiteMinimaleAchat) {
        this.quantiteMinimaleAchat = quantiteMinimaleAchat;
        return this;
    }

    public Boolean isPrescripteur() {
        return prescripteur;
    }

    public Offre prescripteur(Boolean prescripteur) {
        this.prescripteur = prescripteur;
        return this;
    }

    public void setPrescripteur(Boolean prescripteur) {
        this.prescripteur = prescripteur;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Offre libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public BigDecimal getPrixHT() {
        return prixHT;
    }

    public void setPrixHT(BigDecimal prixHT) {
        this.prixHT = prixHT;
    }

    public Offre prixHT(BigDecimal prixHT) {
        this.prixHT = prixHT;
        return this;
    }

    public Boolean isAdoptant() {
        return adoptant;
    }

    public Offre adoptant(Boolean adoptant) {
        this.adoptant = adoptant;
        return this;
    }

    public void setAdoptant(Boolean adoptant) {
        this.adoptant = adoptant;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public Offre duree(String duree) {
        this.duree = duree;
        return this;
    }

    public String getReferenceEditeur() {
        return referenceEditeur;
    }

    public void setReferenceEditeur(String referenceEditeur) {
        this.referenceEditeur = referenceEditeur;
    }

    public Offre referenceEditeur(String referenceEditeur) {
        this.referenceEditeur = referenceEditeur;
        return this;
    }

    public Set<Tva> getTvas() {
        return tvas;
    }

    public void setTvas(Set<Tva> tvas) {
        this.tvas = tvas;
    }

    public Offre tvas(Set<Tva> tvas) {
        this.tvas = tvas;
        return this;
    }

    public Offre addTva(Tva tva) {
        this.tvas.add(tva);
        tva.setOffre(this);
        return this;
    }

    public Offre removeTva(Tva tva) {
        this.tvas.remove(tva);
        tva.setOffre(null);
        return this;
    }

    public Set<Lep> getLeps() {
        return leps;
    }

    public void setLeps(Set<Lep> leps) {
        this.leps = leps;
    }

    public Offre leps(Set<Lep> leps) {
        this.leps = leps;
        return this;
    }

    public Offre addLep(Lep lep) {
        this.leps.add(lep);
        lep.setOffre(this);
        return this;
    }

    public Offre removeLep(Lep lep) {
        this.leps.remove(lep);
        lep.setOffre(null);
        return this;
    }

    public ArticleNumerique getArticleNumerique() {
        return articleNumerique;
    }

    public void setArticleNumerique(ArticleNumerique articleNumerique) {
        this.articleNumerique = articleNumerique;
    }

    public Offre articleNumerique(ArticleNumerique articleNumerique) {
        this.articleNumerique = articleNumerique;
        return this;
    }

    public Licence getLicence() {
        return licence;
    }

    public void setLicence(Licence licence) {
        this.licence = licence;
    }

    public Offre licence(Licence licence) {
        this.licence = licence;
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

    public Offre prixTTC(BigDecimal prixTTC) {
        this.prixTTC = prixTTC;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Offre)) {
            return false;
        }
        return id != null && id.equals(((Offre) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Offre{" +
            "id=" + getId() +
            ", eanLibraire='" + getEanLibraire() + "'" +
            ", quantiteMinimaleAchat=" + getQuantiteMinimaleAchat() +
            ", prescripteur='" + isPrescripteur() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", prixHT=" + getPrixHT() +
            ", adoptant='" + isAdoptant() + "'" +
            ", duree='" + getDuree() + "'" +
            ", referenceEditeur='" + getReferenceEditeur() + "'" +
            "}";
    }
}
