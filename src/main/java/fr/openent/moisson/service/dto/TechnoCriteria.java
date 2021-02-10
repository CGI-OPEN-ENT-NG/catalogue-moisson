package fr.openent.moisson.service.dto;

import fr.openent.moisson.domain.enumeration.Technologie;
import fr.openent.moisson.domain.enumeration.TypeLicenceGAR;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link fr.openent.moisson.domain.Techno} entity. This class is used
 * in {@link fr.openent.moisson.web.rest.TechnoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /technos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TechnoCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Technologie
     */
    public static class TechnologieFilter extends Filter<Technologie> {

        public TechnologieFilter() {
        }

        public TechnologieFilter(TechnologieFilter filter) {
            super(filter);
        }

        @Override
        public TechnologieFilter copy() {
            return new TechnologieFilter(this);
        }

    }
    /**
     * Class for filtering TypeLicenceGAR
     */
    public static class TypeLicenceGARFilter extends Filter<TypeLicenceGAR> {

        public TypeLicenceGARFilter() {
        }

        public TypeLicenceGARFilter(TypeLicenceGARFilter filter) {
            super(filter);
        }

        @Override
        public TypeLicenceGARFilter copy() {
            return new TypeLicenceGARFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TechnologieFilter technologie;

    private StringFilter versionReader;

    private BooleanFilter availableHorsENT;

    private BooleanFilter availableViaENT;

    private BooleanFilter availableViaGAR;

    private TypeLicenceGARFilter typeLicenceGAR;

    private BooleanFilter canUseOffline;

    private BooleanFilter oneClic;

    private BooleanFilter exportCleUSB;

    private BooleanFilter deploiementMasse;

    private StringFilter configurationMiniOS;

    private BooleanFilter needFlash;

    private BooleanFilter annotations;

    private BooleanFilter creationCours;

    private BooleanFilter webAdaptatif;

    private BooleanFilter marquePage;

    private BooleanFilter captureImage;

    private BooleanFilter zoom;

    private BooleanFilter fonctionsRecherche;

    private BooleanFilter corrigesPourEnseignants;

    private BooleanFilter assignationTachesEleves;

    private BooleanFilter partageContenuEleves;

    private BooleanFilter exercicesInteractifs;

    private BooleanFilter exercicesAutoCorriges;

    private BooleanFilter exportReponsesEleves;

    private BooleanFilter importDocument;

    private BooleanFilter exportDocument;

    private BooleanFilter exportSCORM;

    private BooleanFilter personnalisationUserInterface;

    private BooleanFilter modifContenuEditorial;

    private BooleanFilter dispositifDYS;

    private StringFilter nbMaxiInstall;

    private StringFilter nbMaxSimultConnexions;

    private LongFilter articleNumeriqueId;

    public TechnoCriteria() {
    }

    public TechnoCriteria(TechnoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.technologie = other.technologie == null ? null : other.technologie.copy();
        this.versionReader = other.versionReader == null ? null : other.versionReader.copy();
        this.availableHorsENT = other.availableHorsENT == null ? null : other.availableHorsENT.copy();
        this.availableViaENT = other.availableViaENT == null ? null : other.availableViaENT.copy();
        this.availableViaGAR = other.availableViaGAR == null ? null : other.availableViaGAR.copy();
        this.typeLicenceGAR = other.typeLicenceGAR == null ? null : other.typeLicenceGAR.copy();
        this.canUseOffline = other.canUseOffline == null ? null : other.canUseOffline.copy();
        this.oneClic = other.oneClic == null ? null : other.oneClic.copy();
        this.exportCleUSB = other.exportCleUSB == null ? null : other.exportCleUSB.copy();
        this.deploiementMasse = other.deploiementMasse == null ? null : other.deploiementMasse.copy();
        this.configurationMiniOS = other.configurationMiniOS == null ? null : other.configurationMiniOS.copy();
        this.needFlash = other.needFlash == null ? null : other.needFlash.copy();
        this.annotations = other.annotations == null ? null : other.annotations.copy();
        this.creationCours = other.creationCours == null ? null : other.creationCours.copy();
        this.webAdaptatif = other.webAdaptatif == null ? null : other.webAdaptatif.copy();
        this.marquePage = other.marquePage == null ? null : other.marquePage.copy();
        this.captureImage = other.captureImage == null ? null : other.captureImage.copy();
        this.zoom = other.zoom == null ? null : other.zoom.copy();
        this.fonctionsRecherche = other.fonctionsRecherche == null ? null : other.fonctionsRecherche.copy();
        this.corrigesPourEnseignants = other.corrigesPourEnseignants == null ? null : other.corrigesPourEnseignants.copy();
        this.assignationTachesEleves = other.assignationTachesEleves == null ? null : other.assignationTachesEleves.copy();
        this.partageContenuEleves = other.partageContenuEleves == null ? null : other.partageContenuEleves.copy();
        this.exercicesInteractifs = other.exercicesInteractifs == null ? null : other.exercicesInteractifs.copy();
        this.exercicesAutoCorriges = other.exercicesAutoCorriges == null ? null : other.exercicesAutoCorriges.copy();
        this.exportReponsesEleves = other.exportReponsesEleves == null ? null : other.exportReponsesEleves.copy();
        this.importDocument = other.importDocument == null ? null : other.importDocument.copy();
        this.exportDocument = other.exportDocument == null ? null : other.exportDocument.copy();
        this.exportSCORM = other.exportSCORM == null ? null : other.exportSCORM.copy();
        this.personnalisationUserInterface = other.personnalisationUserInterface == null ? null : other.personnalisationUserInterface.copy();
        this.modifContenuEditorial = other.modifContenuEditorial == null ? null : other.modifContenuEditorial.copy();
        this.dispositifDYS = other.dispositifDYS == null ? null : other.dispositifDYS.copy();
        this.nbMaxiInstall = other.nbMaxiInstall == null ? null : other.nbMaxiInstall.copy();
        this.nbMaxSimultConnexions = other.nbMaxSimultConnexions == null ? null : other.nbMaxSimultConnexions.copy();
        this.articleNumeriqueId = other.articleNumeriqueId == null ? null : other.articleNumeriqueId.copy();
    }

    @Override
    public TechnoCriteria copy() {
        return new TechnoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public TechnologieFilter getTechnologie() {
        return technologie;
    }

    public void setTechnologie(TechnologieFilter technologie) {
        this.technologie = technologie;
    }

    public StringFilter getVersionReader() {
        return versionReader;
    }

    public void setVersionReader(StringFilter versionReader) {
        this.versionReader = versionReader;
    }

    public BooleanFilter getAvailableHorsENT() {
        return availableHorsENT;
    }

    public void setAvailableHorsENT(BooleanFilter availableHorsENT) {
        this.availableHorsENT = availableHorsENT;
    }

    public BooleanFilter getAvailableViaENT() {
        return availableViaENT;
    }

    public void setAvailableViaENT(BooleanFilter availableViaENT) {
        this.availableViaENT = availableViaENT;
    }

    public BooleanFilter getAvailableViaGAR() {
        return availableViaGAR;
    }

    public void setAvailableViaGAR(BooleanFilter availableViaGAR) {
        this.availableViaGAR = availableViaGAR;
    }

    public TypeLicenceGARFilter getTypeLicenceGAR() {
        return typeLicenceGAR;
    }

    public void setTypeLicenceGAR(TypeLicenceGARFilter typeLicenceGAR) {
        this.typeLicenceGAR = typeLicenceGAR;
    }

    public BooleanFilter getCanUseOffline() {
        return canUseOffline;
    }

    public void setCanUseOffline(BooleanFilter canUseOffline) {
        this.canUseOffline = canUseOffline;
    }

    public BooleanFilter getOneClic() {
        return oneClic;
    }

    public void setOneClic(BooleanFilter oneClic) {
        this.oneClic = oneClic;
    }

    public BooleanFilter getExportCleUSB() {
        return exportCleUSB;
    }

    public void setExportCleUSB(BooleanFilter exportCleUSB) {
        this.exportCleUSB = exportCleUSB;
    }

    public BooleanFilter getDeploiementMasse() {
        return deploiementMasse;
    }

    public void setDeploiementMasse(BooleanFilter deploiementMasse) {
        this.deploiementMasse = deploiementMasse;
    }

    public StringFilter getConfigurationMiniOS() {
        return configurationMiniOS;
    }

    public void setConfigurationMiniOS(StringFilter configurationMiniOS) {
        this.configurationMiniOS = configurationMiniOS;
    }

    public BooleanFilter getNeedFlash() {
        return needFlash;
    }

    public void setNeedFlash(BooleanFilter needFlash) {
        this.needFlash = needFlash;
    }

    public BooleanFilter getAnnotations() {
        return annotations;
    }

    public void setAnnotations(BooleanFilter annotations) {
        this.annotations = annotations;
    }

    public BooleanFilter getCreationCours() {
        return creationCours;
    }

    public void setCreationCours(BooleanFilter creationCours) {
        this.creationCours = creationCours;
    }

    public BooleanFilter getWebAdaptatif() {
        return webAdaptatif;
    }

    public void setWebAdaptatif(BooleanFilter webAdaptatif) {
        this.webAdaptatif = webAdaptatif;
    }

    public BooleanFilter getMarquePage() {
        return marquePage;
    }

    public void setMarquePage(BooleanFilter marquePage) {
        this.marquePage = marquePage;
    }

    public BooleanFilter getCaptureImage() {
        return captureImage;
    }

    public void setCaptureImage(BooleanFilter captureImage) {
        this.captureImage = captureImage;
    }

    public BooleanFilter getZoom() {
        return zoom;
    }

    public void setZoom(BooleanFilter zoom) {
        this.zoom = zoom;
    }

    public BooleanFilter getFonctionsRecherche() {
        return fonctionsRecherche;
    }

    public void setFonctionsRecherche(BooleanFilter fonctionsRecherche) {
        this.fonctionsRecherche = fonctionsRecherche;
    }

    public BooleanFilter getCorrigesPourEnseignants() {
        return corrigesPourEnseignants;
    }

    public void setCorrigesPourEnseignants(BooleanFilter corrigesPourEnseignants) {
        this.corrigesPourEnseignants = corrigesPourEnseignants;
    }

    public BooleanFilter getAssignationTachesEleves() {
        return assignationTachesEleves;
    }

    public void setAssignationTachesEleves(BooleanFilter assignationTachesEleves) {
        this.assignationTachesEleves = assignationTachesEleves;
    }

    public BooleanFilter getPartageContenuEleves() {
        return partageContenuEleves;
    }

    public void setPartageContenuEleves(BooleanFilter partageContenuEleves) {
        this.partageContenuEleves = partageContenuEleves;
    }

    public BooleanFilter getExercicesInteractifs() {
        return exercicesInteractifs;
    }

    public void setExercicesInteractifs(BooleanFilter exercicesInteractifs) {
        this.exercicesInteractifs = exercicesInteractifs;
    }

    public BooleanFilter getExercicesAutoCorriges() {
        return exercicesAutoCorriges;
    }

    public void setExercicesAutoCorriges(BooleanFilter exercicesAutoCorriges) {
        this.exercicesAutoCorriges = exercicesAutoCorriges;
    }

    public BooleanFilter getExportReponsesEleves() {
        return exportReponsesEleves;
    }

    public void setExportReponsesEleves(BooleanFilter exportReponsesEleves) {
        this.exportReponsesEleves = exportReponsesEleves;
    }

    public BooleanFilter getImportDocument() {
        return importDocument;
    }

    public void setImportDocument(BooleanFilter importDocument) {
        this.importDocument = importDocument;
    }

    public BooleanFilter getExportDocument() {
        return exportDocument;
    }

    public void setExportDocument(BooleanFilter exportDocument) {
        this.exportDocument = exportDocument;
    }

    public BooleanFilter getExportSCORM() {
        return exportSCORM;
    }

    public void setExportSCORM(BooleanFilter exportSCORM) {
        this.exportSCORM = exportSCORM;
    }

    public BooleanFilter getPersonnalisationUserInterface() {
        return personnalisationUserInterface;
    }

    public void setPersonnalisationUserInterface(BooleanFilter personnalisationUserInterface) {
        this.personnalisationUserInterface = personnalisationUserInterface;
    }

    public BooleanFilter getModifContenuEditorial() {
        return modifContenuEditorial;
    }

    public void setModifContenuEditorial(BooleanFilter modifContenuEditorial) {
        this.modifContenuEditorial = modifContenuEditorial;
    }

    public BooleanFilter getDispositifDYS() {
        return dispositifDYS;
    }

    public void setDispositifDYS(BooleanFilter dispositifDYS) {
        this.dispositifDYS = dispositifDYS;
    }

    public StringFilter getNbMaxiInstall() {
        return nbMaxiInstall;
    }

    public void setNbMaxiInstall(StringFilter nbMaxiInstall) {
        this.nbMaxiInstall = nbMaxiInstall;
    }

    public StringFilter getNbMaxSimultConnexions() {
        return nbMaxSimultConnexions;
    }

    public void setNbMaxSimultConnexions(StringFilter nbMaxSimultConnexions) {
        this.nbMaxSimultConnexions = nbMaxSimultConnexions;
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
        final TechnoCriteria that = (TechnoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(technologie, that.technologie) &&
            Objects.equals(versionReader, that.versionReader) &&
            Objects.equals(availableHorsENT, that.availableHorsENT) &&
            Objects.equals(availableViaENT, that.availableViaENT) &&
            Objects.equals(availableViaGAR, that.availableViaGAR) &&
            Objects.equals(typeLicenceGAR, that.typeLicenceGAR) &&
            Objects.equals(canUseOffline, that.canUseOffline) &&
            Objects.equals(oneClic, that.oneClic) &&
            Objects.equals(exportCleUSB, that.exportCleUSB) &&
            Objects.equals(deploiementMasse, that.deploiementMasse) &&
            Objects.equals(configurationMiniOS, that.configurationMiniOS) &&
            Objects.equals(needFlash, that.needFlash) &&
            Objects.equals(annotations, that.annotations) &&
            Objects.equals(creationCours, that.creationCours) &&
            Objects.equals(webAdaptatif, that.webAdaptatif) &&
            Objects.equals(marquePage, that.marquePage) &&
            Objects.equals(captureImage, that.captureImage) &&
            Objects.equals(zoom, that.zoom) &&
            Objects.equals(fonctionsRecherche, that.fonctionsRecherche) &&
            Objects.equals(corrigesPourEnseignants, that.corrigesPourEnseignants) &&
            Objects.equals(assignationTachesEleves, that.assignationTachesEleves) &&
            Objects.equals(partageContenuEleves, that.partageContenuEleves) &&
            Objects.equals(exercicesInteractifs, that.exercicesInteractifs) &&
            Objects.equals(exercicesAutoCorriges, that.exercicesAutoCorriges) &&
            Objects.equals(exportReponsesEleves, that.exportReponsesEleves) &&
            Objects.equals(importDocument, that.importDocument) &&
            Objects.equals(exportDocument, that.exportDocument) &&
            Objects.equals(exportSCORM, that.exportSCORM) &&
            Objects.equals(personnalisationUserInterface, that.personnalisationUserInterface) &&
            Objects.equals(modifContenuEditorial, that.modifContenuEditorial) &&
            Objects.equals(dispositifDYS, that.dispositifDYS) &&
            Objects.equals(nbMaxiInstall, that.nbMaxiInstall) &&
            Objects.equals(nbMaxSimultConnexions, that.nbMaxSimultConnexions) &&
            Objects.equals(articleNumeriqueId, that.articleNumeriqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        technologie,
        versionReader,
        availableHorsENT,
        availableViaENT,
        availableViaGAR,
        typeLicenceGAR,
        canUseOffline,
        oneClic,
        exportCleUSB,
        deploiementMasse,
        configurationMiniOS,
        needFlash,
        annotations,
        creationCours,
        webAdaptatif,
        marquePage,
        captureImage,
        zoom,
        fonctionsRecherche,
        corrigesPourEnseignants,
        assignationTachesEleves,
        partageContenuEleves,
        exercicesInteractifs,
        exercicesAutoCorriges,
        exportReponsesEleves,
        importDocument,
        exportDocument,
        exportSCORM,
        personnalisationUserInterface,
        modifContenuEditorial,
        dispositifDYS,
        nbMaxiInstall,
        nbMaxSimultConnexions,
        articleNumeriqueId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TechnoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (technologie != null ? "technologie=" + technologie + ", " : "") +
                (versionReader != null ? "versionReader=" + versionReader + ", " : "") +
                (availableHorsENT != null ? "availableHorsENT=" + availableHorsENT + ", " : "") +
                (availableViaENT != null ? "availableViaENT=" + availableViaENT + ", " : "") +
                (availableViaGAR != null ? "availableViaGAR=" + availableViaGAR + ", " : "") +
                (typeLicenceGAR != null ? "typeLicenceGAR=" + typeLicenceGAR + ", " : "") +
                (canUseOffline != null ? "canUseOffline=" + canUseOffline + ", " : "") +
                (oneClic != null ? "oneClic=" + oneClic + ", " : "") +
                (exportCleUSB != null ? "exportCleUSB=" + exportCleUSB + ", " : "") +
                (deploiementMasse != null ? "deploiementMasse=" + deploiementMasse + ", " : "") +
                (configurationMiniOS != null ? "configurationMiniOS=" + configurationMiniOS + ", " : "") +
                (needFlash != null ? "needFlash=" + needFlash + ", " : "") +
                (annotations != null ? "annotations=" + annotations + ", " : "") +
                (creationCours != null ? "creationCours=" + creationCours + ", " : "") +
                (webAdaptatif != null ? "webAdaptatif=" + webAdaptatif + ", " : "") +
                (marquePage != null ? "marquePage=" + marquePage + ", " : "") +
                (captureImage != null ? "captureImage=" + captureImage + ", " : "") +
                (zoom != null ? "zoom=" + zoom + ", " : "") +
                (fonctionsRecherche != null ? "fonctionsRecherche=" + fonctionsRecherche + ", " : "") +
                (corrigesPourEnseignants != null ? "corrigesPourEnseignants=" + corrigesPourEnseignants + ", " : "") +
                (assignationTachesEleves != null ? "assignationTachesEleves=" + assignationTachesEleves + ", " : "") +
                (partageContenuEleves != null ? "partageContenuEleves=" + partageContenuEleves + ", " : "") +
                (exercicesInteractifs != null ? "exercicesInteractifs=" + exercicesInteractifs + ", " : "") +
                (exercicesAutoCorriges != null ? "exercicesAutoCorriges=" + exercicesAutoCorriges + ", " : "") +
                (exportReponsesEleves != null ? "exportReponsesEleves=" + exportReponsesEleves + ", " : "") +
                (importDocument != null ? "importDocument=" + importDocument + ", " : "") +
                (exportDocument != null ? "exportDocument=" + exportDocument + ", " : "") +
                (exportSCORM != null ? "exportSCORM=" + exportSCORM + ", " : "") +
                (personnalisationUserInterface != null ? "personnalisationUserInterface=" + personnalisationUserInterface + ", " : "") +
                (modifContenuEditorial != null ? "modifContenuEditorial=" + modifContenuEditorial + ", " : "") +
                (dispositifDYS != null ? "dispositifDYS=" + dispositifDYS + ", " : "") +
                (nbMaxiInstall != null ? "nbMaxiInstall=" + nbMaxiInstall + ", " : "") +
                (nbMaxSimultConnexions != null ? "nbMaxSimultConnexions=" + nbMaxSimultConnexions + ", " : "") +
                (articleNumeriqueId != null ? "articleNumeriqueId=" + articleNumeriqueId + ", " : "") +
            "}";
    }

}
