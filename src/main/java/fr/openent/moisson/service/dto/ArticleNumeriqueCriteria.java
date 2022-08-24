package fr.openent.moisson.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import fr.openent.moisson.domain.enumeration.PublicCible;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link fr.openent.moisson.domain.ArticleNumerique} entity. This class is used
 * in {@link fr.openent.moisson.web.rest.ArticleNumeriqueResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /article-numeriques?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ArticleNumeriqueCriteria implements Serializable, Criteria {
    /**
     * Class for filtering PublicCible
     */
    public static class PublicCibleFilter extends Filter<PublicCible> {

        public PublicCibleFilter() {
        }

        public PublicCibleFilter(PublicCibleFilter filter) {
            super(filter);
        }

        @Override
        public PublicCibleFilter copy() {
            return new PublicCibleFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter ean;

    private StringFilter ark;

    private StringFilter titre;

    private StringFilter editeur;

    private StringFilter auteur;

    private StringFilter collection;

    private StringFilter distributeur;

    private StringFilter urlCouverture;

    private StringFilter urlDemo;

    private InstantFilter dateParution;

    private BooleanFilter compatibleGAR;

    private BooleanFilter accessibleENT;

    private StringFilter description;

    private StringFilter publiccible;

    private StringFilter eanPapier;

    private LongFilter disciplineId;

    private LongFilter niveauId;

    private LongFilter offreId;

    private LongFilter technoId;

    private LongFilter disponibiliteId;

    private LongFilter classeId;

    public ArticleNumeriqueCriteria() {
    }

    public ArticleNumeriqueCriteria(ArticleNumeriqueCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.ean = other.ean == null ? null : other.ean.copy();
        this.ark = other.ark == null ? null : other.ark.copy();
        this.titre = other.titre == null ? null : other.titre.copy();
        this.editeur = other.editeur == null ? null : other.editeur.copy();
        this.auteur = other.auteur == null ? null : other.auteur.copy();
        this.collection = other.collection == null ? null : other.collection.copy();
        this.distributeur = other.distributeur == null ? null : other.distributeur.copy();
        this.urlCouverture = other.urlCouverture == null ? null : other.urlCouverture.copy();
        this.urlDemo = other.urlDemo == null ? null : other.urlDemo.copy();
        this.dateParution = other.dateParution == null ? null : other.dateParution.copy();
        this.compatibleGAR = other.compatibleGAR == null ? null : other.compatibleGAR.copy();
        this.accessibleENT = other.accessibleENT == null ? null : other.accessibleENT.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.publiccible = other.publiccible == null ? null : other.publiccible.copy();
        this.eanPapier = other.eanPapier == null ? null : other.eanPapier.copy();
        this.disciplineId = other.disciplineId == null ? null : other.disciplineId.copy();
        this.niveauId = other.niveauId == null ? null : other.niveauId.copy();
        this.offreId = other.offreId == null ? null : other.offreId.copy();
        this.technoId = other.technoId == null ? null : other.technoId.copy();
        this.disponibiliteId = other.disponibiliteId == null ? null : other.disponibiliteId.copy();
        this.classeId = other.classeId == null ? null : other.classeId.copy();
    }

    @Override
    public ArticleNumeriqueCriteria copy() {
        return new ArticleNumeriqueCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEan() {
        return ean;
    }

    public void setEan(StringFilter ean) {
        this.ean = ean;
    }

    public StringFilter getArk() {
        return ark;
    }

    public void setArk(StringFilter ark) {
        this.ark = ark;
    }

    public StringFilter getTitre() {
        return titre;
    }

    public void setTitre(StringFilter titre) {
        this.titre = titre;
    }

    public StringFilter getEditeur() {
        return editeur;
    }

    public void setEditeur(StringFilter editeur) {
        this.editeur = editeur;
    }

    public StringFilter getAuteur() {
        return auteur;
    }

    public void setAuteur(StringFilter auteur) {
        this.auteur = auteur;
    }

    public StringFilter getCollection() {
        return collection;
    }

    public void setCollection(StringFilter collection) {
        this.collection = collection;
    }

    public StringFilter getDistributeur() {
        return distributeur;
    }

    public void setDistributeur(StringFilter distributeur) {
        this.distributeur = distributeur;
    }

    public StringFilter getUrlCouverture() {
        return urlCouverture;
    }

    public void setUrlCouverture(StringFilter urlCouverture) {
        this.urlCouverture = urlCouverture;
    }

    public StringFilter getUrlDemo() {
        return urlDemo;
    }

    public void setUrlDemo(StringFilter urlDemo) {
        this.urlDemo = urlDemo;
    }

    public InstantFilter getDateParution() {
        return dateParution;
    }

    public void setDateParution(InstantFilter dateParution) {
        this.dateParution = dateParution;
    }

    public BooleanFilter getCompatibleGAR() {
        return compatibleGAR;
    }

    public void setCompatibleGAR(BooleanFilter compatibleGAR) {
        this.compatibleGAR = compatibleGAR;
    }

    public BooleanFilter getAccessibleENT() {
        return accessibleENT;
    }

    public void setAccessibleENT(BooleanFilter accessibleENT) {
        this.accessibleENT = accessibleENT;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getPubliccible() {
        return publiccible;
    }

    public void setPubliccible(StringFilter publiccible) {
        this.publiccible = publiccible;
    }

    public StringFilter getEanPapier() {
        return eanPapier;
    }

    public void setEanPapier(StringFilter eanPapier) {
        this.eanPapier = eanPapier;
    }

    public LongFilter getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(LongFilter disciplineId) {
        this.disciplineId = disciplineId;
    }

    public LongFilter getNiveauId() {
        return niveauId;
    }

    public void setNiveauId(LongFilter niveauId) {
        this.niveauId = niveauId;
    }

    public LongFilter getOffreId() {
        return offreId;
    }

    public void setOffreId(LongFilter offreId) {
        this.offreId = offreId;
    }

    public LongFilter getTechnoId() {
        return technoId;
    }

    public void setTechnoId(LongFilter technoId) {
        this.technoId = technoId;
    }

    public LongFilter getDisponibiliteId() {
        return disponibiliteId;
    }

    public void setDisponibiliteId(LongFilter disponibiliteId) {
        this.disponibiliteId = disponibiliteId;
    }

    public LongFilter getClasseId() {
        return classeId;
    }

    public void setClasseId(LongFilter classeId) {
        this.classeId = classeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ArticleNumeriqueCriteria that = (ArticleNumeriqueCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(ean, that.ean) &&
            Objects.equals(ark, that.ark) &&
            Objects.equals(titre, that.titre) &&
            Objects.equals(editeur, that.editeur) &&
            Objects.equals(auteur, that.auteur) &&
            Objects.equals(collection, that.collection) &&
            Objects.equals(distributeur, that.distributeur) &&
            Objects.equals(urlCouverture, that.urlCouverture) &&
            Objects.equals(urlDemo, that.urlDemo) &&
            Objects.equals(dateParution, that.dateParution) &&
            Objects.equals(compatibleGAR, that.compatibleGAR) &&
            Objects.equals(accessibleENT, that.accessibleENT) &&
            Objects.equals(description, that.description) &&
            Objects.equals(publiccible, that.publiccible) &&
            Objects.equals(eanPapier, that.eanPapier) &&
            Objects.equals(disciplineId, that.disciplineId) &&
            Objects.equals(niveauId, that.niveauId) &&
            Objects.equals(offreId, that.offreId) &&
            Objects.equals(technoId, that.technoId) &&
            Objects.equals(disponibiliteId, that.disponibiliteId) &&
            Objects.equals(classeId, that.classeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        ean,
        ark,
        titre,
        editeur,
        auteur,
        collection,
        distributeur,
        urlCouverture,
        urlDemo,
        dateParution,
        compatibleGAR,
        accessibleENT,
        description,
        publiccible,
        eanPapier,
        disciplineId,
        niveauId,
        offreId,
        technoId,
        disponibiliteId,
        classeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticleNumeriqueCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ean != null ? "ean=" + ean + ", " : "") +
                (ark != null ? "ark=" + ark + ", " : "") +
                (titre != null ? "titre=" + titre + ", " : "") +
                (editeur != null ? "editeur=" + editeur + ", " : "") +
                (auteur != null ? "auteur=" + auteur + ", " : "") +
                (collection != null ? "collection=" + collection + ", " : "") +
                (distributeur != null ? "distributeur=" + distributeur + ", " : "") +
                (urlCouverture != null ? "urlCouverture=" + urlCouverture + ", " : "") +
                (urlDemo != null ? "urlDemo=" + urlDemo + ", " : "") +
                (dateParution != null ? "dateParution=" + dateParution + ", " : "") +
                (compatibleGAR != null ? "compatibleGAR=" + compatibleGAR + ", " : "") +
                (accessibleENT != null ? "accessibleENT=" + accessibleENT + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (publiccible != null ? "publiccible=" + publiccible + ", " : "") +
                (eanPapier != null ? "eanPapier=" + eanPapier + ", " : "") +
                (disciplineId != null ? "disciplineId=" + disciplineId + ", " : "") +
                (niveauId != null ? "niveauId=" + niveauId + ", " : "") +
                (offreId != null ? "offreId=" + offreId + ", " : "") +
                (technoId != null ? "technoId=" + technoId + ", " : "") +
                (disponibiliteId != null ? "disponibiliteId=" + disponibiliteId + ", " : "") +
                (classeId != null ? "classeId=" + classeId + ", " : "") +
            "}";
    }

}
