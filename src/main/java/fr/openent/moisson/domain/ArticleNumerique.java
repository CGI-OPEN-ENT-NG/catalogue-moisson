package fr.openent.moisson.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fr.openent.moisson.domain.enumeration.PublicCible;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A ArticleNumerique.
 */
@Entity
@Table(name = "article_numerique")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "articlenumerique")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ArticleNumerique implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @JsonIgnore
    @JsonProperty("id")
    private Long id;

    @NaturalId
    @Size(min = 13, max = 13)
    @Column(name = "ean", length = 13)
    @JsonProperty("EAN")
    private String ean;

    @Column(name = "ark")
    @JsonProperty("ARK")
    private String ark;

    @Column(name = "titre")
    @JsonProperty("TITRE")
    private String titre;

    @Column(name = "editeur")
    @JsonProperty("EDITEUR")
    private String editeur;

    @Column(name = "auteur", length = 1024)
    @JsonProperty("AUTEUR")
    private String auteur;

    @Column(name = "collection")
    @JsonProperty("COLLECTION")
    private String collection;

    @Column(name = "distributeur")
    @JsonProperty("DISTRIBUTEUR")
    private String distributeur;

    @Column(name = "url_couverture")
    @JsonProperty("URL_COUVERTURE")
    private String urlCouverture;

    @Column(name = "url_demo")
    @JsonProperty("URL_DEMO")
    private String urlDemo;

    @Column(name = "description", length = 65000)
    @JsonProperty("DESCRIPTION")
    private String description;

    @Column(name = "date_parution")
    @JsonProperty("DATE_PARUTION")
    private Instant dateParution;

    @Column(name = "compatible_gar")
    @JsonProperty("COMPATIBLE_GAR")
    private Boolean compatibleGAR;

    @Column(name = "accessible_ent")
    @JsonProperty("ACCESSIBLE_ENT")
    private Boolean accessibleENT;

    @Column(name = "ean_papier")
    @JsonProperty("EAN_PAPIER")
    private String eanPapier;

    @Enumerated(EnumType.STRING)
    @Column(name = "public_cible")
    @JsonProperty("PUBLIC")
    private PublicCible publicCible;

    @OneToMany(mappedBy = "articleNumerique", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    @JsonProperty("DISCIPLINE")
    private Set<Discipline> disciplines = new HashSet<>();

    @OneToMany(mappedBy = "articleNumerique",cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    @JsonProperty("NIVEAU")
    private Set<Niveau> niveaus = new HashSet<>();

    @OneToMany(mappedBy = "articleNumerique", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    @JsonProperty("OFFRES")
    private Set<Offre> offres = new HashSet<>();

    @OneToMany(mappedBy = "articleNumerique", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    @JsonProperty("TECHNO")
    private Set<Techno> technos = new HashSet<>();

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @JsonProperty("DISPONIBILITE")
    private Disponibilite disponibilite;

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

    public ArticleNumerique ean(String ean) {
        this.ean = ean;
        return this;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getArk() {
        return ark;
    }

    public ArticleNumerique ark(String ark) {
        this.ark = ark;
        return this;
    }

    public void setArk(String ark) {
        this.ark = ark;
    }

    public String getTitre() {
        return titre;
    }

    public ArticleNumerique titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getEditeur() {
        return editeur;
    }

    public ArticleNumerique editeur(String editeur) {
        this.editeur = editeur;
        return this;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public String getAuteur() {
        return auteur;
    }

    public ArticleNumerique auteur(String auteur) {
        this.auteur = auteur;
        return this;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getCollection() {
        return collection;
    }

    public ArticleNumerique collection(String collection) {
        this.collection = collection;
        return this;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getDistributeur() {
        return distributeur;
    }

    public ArticleNumerique distributeur(String distributeur) {
        this.distributeur = distributeur;
        return this;
    }

    public void setDistributeur(String distributeur) {
        this.distributeur = distributeur;
    }

    public String getUrlCouverture() {
        return urlCouverture;
    }

    public ArticleNumerique urlCouverture(String urlCouverture) {
        this.urlCouverture = urlCouverture;
        return this;
    }

    public void setUrlCouverture(String urlCouverture) {
        this.urlCouverture = urlCouverture;
    }

    public String getUrlDemo() {
        return urlDemo;
    }

    public ArticleNumerique urlDemo(String urlDemo) {
        this.urlDemo = urlDemo;
        return this;
    }

    public void setUrlDemo(String urlDemo) {
        this.urlDemo = urlDemo;
    }

    public Disponibilite getDisponibilite() {
        return disponibilite;
    }

    public ArticleNumerique disponibilite(Disponibilite disponibilite) {
        this.disponibilite = disponibilite;
        return this;
    }

    public void setDisponibilite(Disponibilite disponibilite) {
        this.disponibilite = disponibilite;
    }

    public Instant getDateParution() {
        return dateParution;
    }

    public ArticleNumerique dateParution(Instant dateParution) {
        this.dateParution = dateParution;
        return this;
    }

    public void setDateParution(Instant dateParution) {
        this.dateParution = dateParution;
    }

    public Boolean isCompatibleGAR() {
        return compatibleGAR;
    }

    public ArticleNumerique compatibleGAR(Boolean compatibleGAR) {
        this.compatibleGAR = compatibleGAR;
        return this;
    }

    public void setCompatibleGAR(Boolean compatibleGAR) {
        this.compatibleGAR = compatibleGAR;
    }

    public Boolean isAccessibleENT() {
        return accessibleENT;
    }

    public ArticleNumerique accessibleENT(Boolean accessibleENT) {
        this.accessibleENT = accessibleENT;
        return this;
    }

    public void setAccessibleENT(Boolean accessibleENT) {
        this.accessibleENT = accessibleENT;
    }

    public String getEanPapier() {
        return eanPapier;
    }

    public ArticleNumerique eanPapier(String eanPapier) {
        this.eanPapier = eanPapier;
        return this;
    }

    public void setEanPapier(String eanPapier) {
        this.eanPapier = eanPapier;
    }

    public String getDescription() {
        return description;
    }

    public ArticleNumerique description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PublicCible getPublicCible() {
        return publicCible;
    }

    public ArticleNumerique publicCible(PublicCible publicCible) {
        this.publicCible = publicCible;
        return this;
    }

    public void setPublicCible(PublicCible publicCible) {
        this.publicCible = publicCible;
    }

    public Set<Discipline> getDisciplines() {
        return disciplines;
    }

    public ArticleNumerique disciplines(Set<Discipline> disciplines) {
        this.disciplines = disciplines;
        return this;
    }

    public ArticleNumerique addDiscipline(Discipline discipline) {
        this.disciplines.add(discipline);
        discipline.setArticleNumerique(this);
        return this;
    }

    public ArticleNumerique removeDiscipline(Discipline discipline) {
        this.disciplines.remove(discipline);
        discipline.setArticleNumerique(null);
        return this;
    }

    public void setDisciplines(Set<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    public Set<Niveau> getNiveaus() {
        return niveaus;
    }

    public ArticleNumerique niveaus(Set<Niveau> niveaus) {
        this.niveaus = niveaus;
        return this;
    }

    public ArticleNumerique addNiveau(Niveau niveau) {
        this.niveaus.add(niveau);
        niveau.setArticleNumerique(this);
        return this;
    }

    public ArticleNumerique removeNiveau(Niveau niveau) {
        this.niveaus.remove(niveau);
        niveau.setArticleNumerique(null);
        return this;
    }

    public void setNiveaus(Set<Niveau> niveaus) {
        this.niveaus = niveaus;
    }

    public Set<Offre> getOffres() {
        return offres;
    }

    public ArticleNumerique offres(Set<Offre> offres) {
        this.offres = offres;
        return this;
    }

    public ArticleNumerique addOffre(Offre offre) {
        this.offres.add(offre);
        offre.setArticleNumerique(this);
        return this;
    }

    public ArticleNumerique removeOffre(Offre offre) {
        this.offres.remove(offre);
        offre.setArticleNumerique(null);
        return this;
    }

    public void setOffres(Set<Offre> offres) {
        this.offres = offres;
    }

    public Set<Techno> getTechnos() {
        return technos;
    }

    public ArticleNumerique technos(Set<Techno> technos) {
        this.technos = technos;
        return this;
    }

    public ArticleNumerique addTechno(Techno techno) {
        this.technos.add(techno);
        techno.setArticleNumerique(this);
        return this;
    }

    public ArticleNumerique removeTechno(Techno techno) {
        this.technos.remove(techno);
        techno.setArticleNumerique(null);
        return this;
    }

    public void setTechnos(Set<Techno> technos) {
        this.technos = technos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticleNumerique)) {
            return false;
        }
        return id != null && id.equals(((ArticleNumerique) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticleNumerique{" +
            "id=" + getId() +
            ", ean='" + getEan() + "'" +
            ", ark='" + getArk() + "'" +
            ", titre='" + getTitre() + "'" +
            ", editeur='" + getEditeur() + "'" +
            ", auteur='" + getAuteur() + "'" +
            ", collection='" + getCollection() + "'" +
            ", distributeur='" + getDistributeur() + "'" +
            ", urlCouverture='" + getUrlCouverture() + "'" +
            ", urlDemo='" + getUrlDemo() + "'" +
            ", dateParution='" + getDateParution() + "'" +
            ", compatibleGAR='" + isCompatibleGAR() + "'" +
            ", accessibleENT='" + isAccessibleENT() + "'" +
            ", eanPapier='" + getEanPapier() + "'" +
            ", description='" + getDescription() + "'" +
            ", publicCible='" + getPublicCible() + "'" +
            "}";
    }
}
