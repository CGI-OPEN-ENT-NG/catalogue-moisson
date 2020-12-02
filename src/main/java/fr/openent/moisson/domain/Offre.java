package fr.openent.moisson.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.NaturalId;
import org.springframework.data.elasticsearch.annotations.FieldType;
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
public class Offre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NaturalId
    @Size(min = 13, max = 13)
    @Column(name = "ean_libraire", length = 13)
    private String eanLibraire;

    @Column(name = "reference")
    private String reference;

    @Column(name = "duree")
    private Integer duree;

    @Column(name = "quantite_minimale_achat")
    private Integer quantiteMinimaleAchat;

    @Column(name = "licence")
    private String licence;

    @Column(name = "prescripteur")
    private Boolean prescripteur;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "prix_ht", precision = 21, scale = 2)
    private BigDecimal prixHT;

    @Column(name = "adoptant")
    private Boolean adoptant;

    @OneToMany(mappedBy = "offre")
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    private Set<Tva> tvas = new HashSet<>();

    @OneToMany(mappedBy = "offre")
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    private Set<Lep> leps = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "offres", allowSetters = true)
    private ArticleNumerique articleNumerique;

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

    public Offre eanLibraire(String eanLibraire) {
        this.eanLibraire = eanLibraire;
        return this;
    }

    public void setEanLibraire(String eanLibraire) {
        this.eanLibraire = eanLibraire;
    }

    public String getReference() {
        return reference;
    }

    public Offre reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getDuree() {
        return duree;
    }

    public Offre duree(Integer duree) {
        this.duree = duree;
        return this;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public Integer getQuantiteMinimaleAchat() {
        return quantiteMinimaleAchat;
    }

    public Offre quantiteMinimaleAchat(Integer quantiteMinimaleAchat) {
        this.quantiteMinimaleAchat = quantiteMinimaleAchat;
        return this;
    }

    public void setQuantiteMinimaleAchat(Integer quantiteMinimaleAchat) {
        this.quantiteMinimaleAchat = quantiteMinimaleAchat;
    }

    public String getLicence() {
        return licence;
    }

    public Offre licence(String licence) {
        this.licence = licence;
        return this;
    }

    public void setLicence(String licence) {
        this.licence = licence;
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

    public Offre libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public BigDecimal getPrixHT() {
        return prixHT;
    }

    public Offre prixHT(BigDecimal prixHT) {
        this.prixHT = prixHT;
        return this;
    }

    public void setPrixHT(BigDecimal prixHT) {
        this.prixHT = prixHT;
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

    public Set<Tva> getTvas() {
        return tvas;
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

    public void setTvas(Set<Tva> tvas) {
        this.tvas = tvas;
    }

    public Set<Lep> getLeps() {
        return leps;
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

    public void setLeps(Set<Lep> leps) {
        this.leps = leps;
    }

    public ArticleNumerique getArticleNumerique() {
        return articleNumerique;
    }

    public Offre articleNumerique(ArticleNumerique articleNumerique) {
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
            ", reference='" + getReference() + "'" +
            ", duree=" + getDuree() +
            ", quantiteMinimaleAchat=" + getQuantiteMinimaleAchat() +
            ", licence='" + getLicence() + "'" +
            ", prescripteur='" + isPrescripteur() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", prixHT=" + getPrixHT() +
            ", adoptant='" + isAdoptant() + "'" +
            "}";
    }
}
