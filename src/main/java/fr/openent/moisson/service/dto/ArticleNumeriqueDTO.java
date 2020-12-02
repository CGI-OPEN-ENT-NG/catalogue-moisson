package fr.openent.moisson.service.dto;

import java.time.Instant;
import javax.persistence.OneToMany;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import fr.openent.moisson.domain.Discipline;
import fr.openent.moisson.domain.Niveau;
import fr.openent.moisson.domain.Offre;
import fr.openent.moisson.domain.Techno;
import fr.openent.moisson.domain.enumeration.Disponibilite;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DTO for the {@link fr.openent.moisson.domain.ArticleNumerique} entity.
 */
public class ArticleNumeriqueDTO implements Serializable {

    private Long id;

    @Size(min = 13, max = 13)
    private String ean;

    private String ark;

    private String titre;

    private String editeur;

    private String auteur;

    private String collection;

    private String distributeur;

    private String urlCouverture;

    private String urlDemo;

    private Disponibilite disponibilte;

    private Instant dateDisponibilte;

    private Instant dateParution;

    private Boolean compatibleGAR;

    private Boolean accessibleENT;

    @Size(min = 13, max = 13)
    private String eanPapier;

    private Set<Discipline> disciplines;

    private Set<Niveau> niveaus;

    private Set<Offre> offres;

    private Set<Techno> technos;

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

    public String getUrlDemo() {
        return urlDemo;
    }

    public void setUrlDemo(String urlDemo) {
        this.urlDemo = urlDemo;
    }

    public Disponibilite getDisponibilte() {
        return disponibilte;
    }

    public void setDisponibilte(Disponibilite disponibilte) {
        this.disponibilte = disponibilte;
    }

    public Instant getDateDisponibilte() {
        return dateDisponibilte;
    }

    public void setDateDisponibilte(Instant dateDisponibilte) {
        this.dateDisponibilte = dateDisponibilte;
    }

    public Instant getDateParution() {
        return dateParution;
    }

    public void setDateParution(Instant dateParution) {
        this.dateParution = dateParution;
    }

    public Boolean isCompatibleGAR() {
        return compatibleGAR;
    }

    public void setCompatibleGAR(Boolean compatibleGAR) {
        this.compatibleGAR = compatibleGAR;
    }

    public Boolean isAccessibleENT() {
        return accessibleENT;
    }

    public void setAccessibleENT(Boolean accessibleENT) {
        this.accessibleENT = accessibleENT;
    }

    public String getEanPapier() {
        return eanPapier;
    }

    public void setEanPapier(String eanPapier) {
        this.eanPapier = eanPapier;
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

    public Set<Offre> getOffres() {
        return offres;
    }

    public void setOffres(Set<Offre> offres) {
        this.offres = offres;
    }

    public Set<Techno> getTechnos() {
        return technos;
    }

    public void setTechnos(Set<Techno> technos) {
        this.technos = technos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticleNumeriqueDTO)) {
            return false;
        }

        return id != null && id.equals(((ArticleNumeriqueDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticleNumeriqueDTO{" +
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
