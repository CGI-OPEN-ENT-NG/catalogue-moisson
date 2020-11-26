package fr.openent.moisson.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import fr.openent.moisson.domain.enumeration.Disponibilite;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link fr.openent.moisson.domain.ArticlePapier} entity. This class is used
 * in {@link fr.openent.moisson.web.rest.ArticlePapierResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /article-papiers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ArticlePapierCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Disponibilite
     */
    public static class DisponibiliteFilter extends Filter<Disponibilite> {

        public DisponibiliteFilter() {
        }

        public DisponibiliteFilter(DisponibiliteFilter filter) {
            super(filter);
        }

        @Override
        public DisponibiliteFilter copy() {
            return new DisponibiliteFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter ean;

    private StringFilter ark;

    private StringFilter titre;

    private StringFilter editeur;

    private StringFilter auteur;

    private StringFilter referenceEditeur;

    private StringFilter collection;

    private StringFilter distributeur;

    private StringFilter urlCouverture;

    private DisponibiliteFilter disponibilte;

    private InstantFilter dateDisponibilte;

    private InstantFilter dateParution;

    private BooleanFilter commandable;

    private BigDecimalFilter tva;

    private BigDecimalFilter prixHT;

    public ArticlePapierCriteria() {
    }

    public ArticlePapierCriteria(ArticlePapierCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.ean = other.ean == null ? null : other.ean.copy();
        this.ark = other.ark == null ? null : other.ark.copy();
        this.titre = other.titre == null ? null : other.titre.copy();
        this.editeur = other.editeur == null ? null : other.editeur.copy();
        this.auteur = other.auteur == null ? null : other.auteur.copy();
        this.referenceEditeur = other.referenceEditeur == null ? null : other.referenceEditeur.copy();
        this.collection = other.collection == null ? null : other.collection.copy();
        this.distributeur = other.distributeur == null ? null : other.distributeur.copy();
        this.urlCouverture = other.urlCouverture == null ? null : other.urlCouverture.copy();
        this.disponibilte = other.disponibilte == null ? null : other.disponibilte.copy();
        this.dateDisponibilte = other.dateDisponibilte == null ? null : other.dateDisponibilte.copy();
        this.dateParution = other.dateParution == null ? null : other.dateParution.copy();
        this.commandable = other.commandable == null ? null : other.commandable.copy();
        this.tva = other.tva == null ? null : other.tva.copy();
        this.prixHT = other.prixHT == null ? null : other.prixHT.copy();
    }

    @Override
    public ArticlePapierCriteria copy() {
        return new ArticlePapierCriteria(this);
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

    public StringFilter getReferenceEditeur() {
        return referenceEditeur;
    }

    public void setReferenceEditeur(StringFilter referenceEditeur) {
        this.referenceEditeur = referenceEditeur;
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

    public DisponibiliteFilter getDisponibilte() {
        return disponibilte;
    }

    public void setDisponibilte(DisponibiliteFilter disponibilte) {
        this.disponibilte = disponibilte;
    }

    public InstantFilter getDateDisponibilte() {
        return dateDisponibilte;
    }

    public void setDateDisponibilte(InstantFilter dateDisponibilte) {
        this.dateDisponibilte = dateDisponibilte;
    }

    public InstantFilter getDateParution() {
        return dateParution;
    }

    public void setDateParution(InstantFilter dateParution) {
        this.dateParution = dateParution;
    }

    public BooleanFilter getCommandable() {
        return commandable;
    }

    public void setCommandable(BooleanFilter commandable) {
        this.commandable = commandable;
    }

    public BigDecimalFilter getTva() {
        return tva;
    }

    public void setTva(BigDecimalFilter tva) {
        this.tva = tva;
    }

    public BigDecimalFilter getPrixHT() {
        return prixHT;
    }

    public void setPrixHT(BigDecimalFilter prixHT) {
        this.prixHT = prixHT;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ArticlePapierCriteria that = (ArticlePapierCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(ean, that.ean) &&
            Objects.equals(ark, that.ark) &&
            Objects.equals(titre, that.titre) &&
            Objects.equals(editeur, that.editeur) &&
            Objects.equals(auteur, that.auteur) &&
            Objects.equals(referenceEditeur, that.referenceEditeur) &&
            Objects.equals(collection, that.collection) &&
            Objects.equals(distributeur, that.distributeur) &&
            Objects.equals(urlCouverture, that.urlCouverture) &&
            Objects.equals(disponibilte, that.disponibilte) &&
            Objects.equals(dateDisponibilte, that.dateDisponibilte) &&
            Objects.equals(dateParution, that.dateParution) &&
            Objects.equals(commandable, that.commandable) &&
            Objects.equals(tva, that.tva) &&
            Objects.equals(prixHT, that.prixHT);
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
        referenceEditeur,
        collection,
        distributeur,
        urlCouverture,
        disponibilte,
        dateDisponibilte,
        dateParution,
        commandable,
        tva,
        prixHT
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticlePapierCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ean != null ? "ean=" + ean + ", " : "") +
                (ark != null ? "ark=" + ark + ", " : "") +
                (titre != null ? "titre=" + titre + ", " : "") +
                (editeur != null ? "editeur=" + editeur + ", " : "") +
                (auteur != null ? "auteur=" + auteur + ", " : "") +
                (referenceEditeur != null ? "referenceEditeur=" + referenceEditeur + ", " : "") +
                (collection != null ? "collection=" + collection + ", " : "") +
                (distributeur != null ? "distributeur=" + distributeur + ", " : "") +
                (urlCouverture != null ? "urlCouverture=" + urlCouverture + ", " : "") +
                (disponibilte != null ? "disponibilte=" + disponibilte + ", " : "") +
                (dateDisponibilte != null ? "dateDisponibilte=" + dateDisponibilte + ", " : "") +
                (dateParution != null ? "dateParution=" + dateParution + ", " : "") +
                (commandable != null ? "commandable=" + commandable + ", " : "") +
                (tva != null ? "tva=" + tva + ", " : "") +
                (prixHT != null ? "prixHT=" + prixHT + ", " : "") +
            "}";
    }

}