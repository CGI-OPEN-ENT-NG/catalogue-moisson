package fr.openent.moisson.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import fr.openent.moisson.domain.enumeration.Disponibilite;

/**
 * A ArticleNumerique.
 */
@Entity
@Table(name = "article_numerique")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "articlenumerique")
public class ArticleNumerique implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(min = 13, max = 13)
    @Column(name = "ean", length = 13)
    private String ean;

    @Column(name = "ark")
    private String ark;

    @Column(name = "titre")
    private String titre;

    @Column(name = "editeur")
    private String editeur;

    @Column(name = "auteur")
    private String auteur;

    @Column(name = "collection")
    private String collection;

    @Column(name = "distributeur")
    private String distributeur;

    @Column(name = "url_couverture")
    private String urlCouverture;

    @Column(name = "url_demo")
    private String urlDemo;

    @Enumerated(EnumType.STRING)
    @Column(name = "disponibilte")
    private Disponibilite disponibilte;

    @Column(name = "date_disponibilte")
    private Instant dateDisponibilte;

    @Column(name = "date_parution")
    private Instant dateParution;

    @Column(name = "compatible_gar")
    private Boolean compatibleGAR;

    @Column(name = "accessible_ent")
    private Boolean accessibleENT;

    @Size(min = 13, max = 13)
    @Column(name = "ean_papier", length = 13)
    private String eanPapier;

    @OneToMany(mappedBy = "articleNumerique")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Discipline> disciplines = new HashSet<>();

    @OneToMany(mappedBy = "articleNumerique")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Niveau> niveaus = new HashSet<>();

    @OneToMany(mappedBy = "articleNumerique")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Offre> offres = new HashSet<>();

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

    public Disponibilite getDisponibilte() {
        return disponibilte;
    }

    public ArticleNumerique disponibilte(Disponibilite disponibilte) {
        this.disponibilte = disponibilte;
        return this;
    }

    public void setDisponibilte(Disponibilite disponibilte) {
        this.disponibilte = disponibilte;
    }

    public Instant getDateDisponibilte() {
        return dateDisponibilte;
    }

    public ArticleNumerique dateDisponibilte(Instant dateDisponibilte) {
        this.dateDisponibilte = dateDisponibilte;
        return this;
    }

    public void setDateDisponibilte(Instant dateDisponibilte) {
        this.dateDisponibilte = dateDisponibilte;
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
            ", disponibilte='" + getDisponibilte() + "'" +
            ", dateDisponibilte='" + getDateDisponibilte() + "'" +
            ", dateParution='" + getDateParution() + "'" +
            ", compatibleGAR='" + isCompatibleGAR() + "'" +
            ", accessibleENT='" + isAccessibleENT() + "'" +
            ", eanPapier='" + getEanPapier() + "'" +
            "}";
    }
}
