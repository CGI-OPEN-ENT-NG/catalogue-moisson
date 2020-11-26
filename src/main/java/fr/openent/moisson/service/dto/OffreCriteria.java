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

    private StringFilter reference;

    private IntegerFilter duree;

    private BooleanFilter adoption;

    private IntegerFilter quantiteMinimaleAchat;

    private StringFilter licence;

    private BooleanFilter prescripteur;

    private StringFilter libelle;

    private BigDecimalFilter prixHT;

    private LongFilter tvaId;

    private LongFilter lepId;

    private LongFilter articleNumeriqueId;

    public OffreCriteria() {
    }

    public OffreCriteria(OffreCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.eanLibraire = other.eanLibraire == null ? null : other.eanLibraire.copy();
        this.reference = other.reference == null ? null : other.reference.copy();
        this.duree = other.duree == null ? null : other.duree.copy();
        this.adoption = other.adoption == null ? null : other.adoption.copy();
        this.quantiteMinimaleAchat = other.quantiteMinimaleAchat == null ? null : other.quantiteMinimaleAchat.copy();
        this.licence = other.licence == null ? null : other.licence.copy();
        this.prescripteur = other.prescripteur == null ? null : other.prescripteur.copy();
        this.libelle = other.libelle == null ? null : other.libelle.copy();
        this.prixHT = other.prixHT == null ? null : other.prixHT.copy();
        this.tvaId = other.tvaId == null ? null : other.tvaId.copy();
        this.lepId = other.lepId == null ? null : other.lepId.copy();
        this.articleNumeriqueId = other.articleNumeriqueId == null ? null : other.articleNumeriqueId.copy();
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

    public StringFilter getReference() {
        return reference;
    }

    public void setReference(StringFilter reference) {
        this.reference = reference;
    }

    public IntegerFilter getDuree() {
        return duree;
    }

    public void setDuree(IntegerFilter duree) {
        this.duree = duree;
    }

    public BooleanFilter getAdoption() {
        return adoption;
    }

    public void setAdoption(BooleanFilter adoption) {
        this.adoption = adoption;
    }

    public IntegerFilter getQuantiteMinimaleAchat() {
        return quantiteMinimaleAchat;
    }

    public void setQuantiteMinimaleAchat(IntegerFilter quantiteMinimaleAchat) {
        this.quantiteMinimaleAchat = quantiteMinimaleAchat;
    }

    public StringFilter getLicence() {
        return licence;
    }

    public void setLicence(StringFilter licence) {
        this.licence = licence;
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
            Objects.equals(reference, that.reference) &&
            Objects.equals(duree, that.duree) &&
            Objects.equals(adoption, that.adoption) &&
            Objects.equals(quantiteMinimaleAchat, that.quantiteMinimaleAchat) &&
            Objects.equals(licence, that.licence) &&
            Objects.equals(prescripteur, that.prescripteur) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(prixHT, that.prixHT) &&
            Objects.equals(tvaId, that.tvaId) &&
            Objects.equals(lepId, that.lepId) &&
            Objects.equals(articleNumeriqueId, that.articleNumeriqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        eanLibraire,
        reference,
        duree,
        adoption,
        quantiteMinimaleAchat,
        licence,
        prescripteur,
        libelle,
        prixHT,
        tvaId,
        lepId,
        articleNumeriqueId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OffreCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (eanLibraire != null ? "eanLibraire=" + eanLibraire + ", " : "") +
                (reference != null ? "reference=" + reference + ", " : "") +
                (duree != null ? "duree=" + duree + ", " : "") +
                (adoption != null ? "adoption=" + adoption + ", " : "") +
                (quantiteMinimaleAchat != null ? "quantiteMinimaleAchat=" + quantiteMinimaleAchat + ", " : "") +
                (licence != null ? "licence=" + licence + ", " : "") +
                (prescripteur != null ? "prescripteur=" + prescripteur + ", " : "") +
                (libelle != null ? "libelle=" + libelle + ", " : "") +
                (prixHT != null ? "prixHT=" + prixHT + ", " : "") +
                (tvaId != null ? "tvaId=" + tvaId + ", " : "") +
                (lepId != null ? "lepId=" + lepId + ", " : "") +
                (articleNumeriqueId != null ? "articleNumeriqueId=" + articleNumeriqueId + ", " : "") +
            "}";
    }

}
