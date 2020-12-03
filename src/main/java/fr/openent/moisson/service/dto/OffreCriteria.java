package fr.openent.moisson.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;

/**
 * Criteria class for the {@link fr.openent.moisson.domain.Offre} entity. This class is used
 * in {@link fr.openent.moisson.web.rest.OffreResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /offres?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OffreCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter eanLibraire;

    private IntegerFilter quantiteMinimaleAchat;

    private BooleanFilter prescripteur;

    private StringFilter libelle;

    private BigDecimalFilter prixHT;

    private BooleanFilter adoptant;

    private StringFilter duree;

    private StringFilter referenceEditeur;

    private LongFilter tvaId;

    private LongFilter lepId;

    private LongFilter articleNumeriqueId;

    private LongFilter licenceId;

    public OffreCriteria() {
    }

    public OffreCriteria(OffreCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.eanLibraire = other.eanLibraire == null ? null : other.eanLibraire.copy();
        this.quantiteMinimaleAchat = other.quantiteMinimaleAchat == null ? null : other.quantiteMinimaleAchat.copy();
        this.prescripteur = other.prescripteur == null ? null : other.prescripteur.copy();
        this.libelle = other.libelle == null ? null : other.libelle.copy();
        this.prixHT = other.prixHT == null ? null : other.prixHT.copy();
        this.adoptant = other.adoptant == null ? null : other.adoptant.copy();
        this.duree = other.duree == null ? null : other.duree.copy();
        this.referenceEditeur = other.referenceEditeur == null ? null : other.referenceEditeur.copy();
        this.tvaId = other.tvaId == null ? null : other.tvaId.copy();
        this.lepId = other.lepId == null ? null : other.lepId.copy();
        this.articleNumeriqueId = other.articleNumeriqueId == null ? null : other.articleNumeriqueId.copy();
        this.licenceId = other.licenceId == null ? null : other.licenceId.copy();
    }

    @Override
    public OffreCriteria copy() {
        return new OffreCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEanLibraire() {
        return eanLibraire;
    }

    public void setEanLibraire(StringFilter eanLibraire) {
        this.eanLibraire = eanLibraire;
    }

    public IntegerFilter getQuantiteMinimaleAchat() {
        return quantiteMinimaleAchat;
    }

    public void setQuantiteMinimaleAchat(IntegerFilter quantiteMinimaleAchat) {
        this.quantiteMinimaleAchat = quantiteMinimaleAchat;
    }

    public BooleanFilter getPrescripteur() {
        return prescripteur;
    }

    public void setPrescripteur(BooleanFilter prescripteur) {
        this.prescripteur = prescripteur;
    }

    public StringFilter getLibelle() {
        return libelle;
    }

    public void setLibelle(StringFilter libelle) {
        this.libelle = libelle;
    }

    public BigDecimalFilter getPrixHT() {
        return prixHT;
    }

    public void setPrixHT(BigDecimalFilter prixHT) {
        this.prixHT = prixHT;
    }

    public BooleanFilter getAdoptant() {
        return adoptant;
    }

    public void setAdoptant(BooleanFilter adoptant) {
        this.adoptant = adoptant;
    }

    public StringFilter getDuree() {
        return duree;
    }

    public void setDuree(StringFilter duree) {
        this.duree = duree;
    }

    public StringFilter getReferenceEditeur() {
        return referenceEditeur;
    }

    public void setReferenceEditeur(StringFilter referenceEditeur) {
        this.referenceEditeur = referenceEditeur;
    }

    public LongFilter getTvaId() {
        return tvaId;
    }

    public void setTvaId(LongFilter tvaId) {
        this.tvaId = tvaId;
    }

    public LongFilter getLepId() {
        return lepId;
    }

    public void setLepId(LongFilter lepId) {
        this.lepId = lepId;
    }

    public LongFilter getArticleNumeriqueId() {
        return articleNumeriqueId;
    }

    public void setArticleNumeriqueId(LongFilter articleNumeriqueId) {
        this.articleNumeriqueId = articleNumeriqueId;
    }

    public LongFilter getLicenceId() {
        return licenceId;
    }

    public void setLicenceId(LongFilter licenceId) {
        this.licenceId = licenceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OffreCriteria that = (OffreCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(eanLibraire, that.eanLibraire) &&
            Objects.equals(quantiteMinimaleAchat, that.quantiteMinimaleAchat) &&
            Objects.equals(prescripteur, that.prescripteur) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(prixHT, that.prixHT) &&
            Objects.equals(adoptant, that.adoptant) &&
            Objects.equals(duree, that.duree) &&
            Objects.equals(referenceEditeur, that.referenceEditeur) &&
            Objects.equals(tvaId, that.tvaId) &&
            Objects.equals(lepId, that.lepId) &&
            Objects.equals(articleNumeriqueId, that.articleNumeriqueId) &&
            Objects.equals(licenceId, that.licenceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        eanLibraire,
        quantiteMinimaleAchat,
        prescripteur,
        libelle,
        prixHT,
        adoptant,
        duree,
        referenceEditeur,
        tvaId,
        lepId,
        articleNumeriqueId,
        licenceId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OffreCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (eanLibraire != null ? "eanLibraire=" + eanLibraire + ", " : "") +
                (quantiteMinimaleAchat != null ? "quantiteMinimaleAchat=" + quantiteMinimaleAchat + ", " : "") +
                (prescripteur != null ? "prescripteur=" + prescripteur + ", " : "") +
                (libelle != null ? "libelle=" + libelle + ", " : "") +
                (prixHT != null ? "prixHT=" + prixHT + ", " : "") +
                (adoptant != null ? "adoptant=" + adoptant + ", " : "") +
                (duree != null ? "duree=" + duree + ", " : "") +
                (referenceEditeur != null ? "referenceEditeur=" + referenceEditeur + ", " : "") +
                (tvaId != null ? "tvaId=" + tvaId + ", " : "") +
                (lepId != null ? "lepId=" + lepId + ", " : "") +
                (articleNumeriqueId != null ? "articleNumeriqueId=" + articleNumeriqueId + ", " : "") +
                (licenceId != null ? "licenceId=" + licenceId + ", " : "") +
            "}";
    }

}
