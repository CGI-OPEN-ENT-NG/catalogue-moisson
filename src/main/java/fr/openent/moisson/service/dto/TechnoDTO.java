package fr.openent.moisson.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import fr.openent.moisson.domain.enumeration.Technologie;
import fr.openent.moisson.domain.enumeration.TypeLicenceGAR;

/**
 * A DTO for the {@link fr.openent.moisson.domain.Techno} entity.
 */
public class TechnoDTO implements Serializable {

    private Long id;

    private String technologie;

    private String versionReader;

    private Boolean availableHorsENT;

    private Boolean availableViaENT;

    private Boolean availableViaGAR;

    private String typeLicenceGAR;

    private Boolean canUseOffline;

    private Boolean oneClic;

    private Boolean exportCleUSB;

    private Boolean deploiementMasse;

    private String configurationMiniOS;

    private Boolean needFlash;

    private Boolean annotations;

    private Boolean creationCours;

    private Boolean webAdaptatif;

    private Boolean marquePage;

    private Boolean captureImage;

    private Boolean zoom;

    private Boolean fonctionsRecherche;

    private Boolean corrigesPourEnseignants;

    private Boolean assignationTachesEleves;

    private Boolean partageContenuEleves;

    private Boolean exercicesInteractifs;

    private Boolean exercicesAutoCorriges;

    private Boolean exportReponsesEleves;

    private Boolean importDocument;

    private Boolean exportDocument;

    private Boolean exportSCORM;

    private Boolean personnalisationUserInterface;

    private Boolean modifContenuEditorial;

    private Boolean dispositifDYS;

    private String nbMaxiInstall;

    private String nbMaxSimultConnexions;

    private Boolean messagerie;


    private Long articleNumeriqueId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTechnologie() {
        return technologie;
    }

    public void setTechnologie(String technologie) {
        this.technologie = technologie;
    }

    public String getVersionReader() {
        return versionReader;
    }

    public void setVersionReader(String versionReader) {
        this.versionReader = versionReader;
    }

    public Boolean isAvailableHorsENT() {
        return availableHorsENT;
    }

    public void setAvailableHorsENT(Boolean availableHorsENT) {
        this.availableHorsENT = availableHorsENT;
    }

    public Boolean isAvailableViaENT() {
        return availableViaENT;
    }

    public void setAvailableViaENT(Boolean availableViaENT) {
        this.availableViaENT = availableViaENT;
    }

    public Boolean isAvailableViaGAR() {
        return availableViaGAR;
    }

    public void setAvailableViaGAR(Boolean availableViaGAR) {
        this.availableViaGAR = availableViaGAR;
    }

    public String getTypeLicenceGAR() {
        return typeLicenceGAR;
    }

    public void setTypeLicenceGAR(String typeLicenceGAR) {
        this.typeLicenceGAR = typeLicenceGAR;
    }

    public Boolean isCanUseOffline() {
        return canUseOffline;
    }

    public void setCanUseOffline(Boolean canUseOffline) {
        this.canUseOffline = canUseOffline;
    }

    public Boolean isOneClic() {
        return oneClic;
    }

    public void setOneClic(Boolean oneClic) {
        this.oneClic = oneClic;
    }

    public Boolean isExportCleUSB() {
        return exportCleUSB;
    }

    public void setExportCleUSB(Boolean exportCleUSB) {
        this.exportCleUSB = exportCleUSB;
    }

    public Boolean isDeploiementMasse() {
        return deploiementMasse;
    }

    public void setDeploiementMasse(Boolean deploiementMasse) {
        this.deploiementMasse = deploiementMasse;
    }

    public String getConfigurationMiniOS() {
        return configurationMiniOS;
    }

    public void setConfigurationMiniOS(String configurationMiniOS) {
        this.configurationMiniOS = configurationMiniOS;
    }

    public Boolean isNeedFlash() {
        return needFlash;
    }

    public void setNeedFlash(Boolean needFlash) {
        this.needFlash = needFlash;
    }

    public Boolean isAnnotations() {
        return annotations;
    }

    public void setAnnotations(Boolean annotations) {
        this.annotations = annotations;
    }

    public Boolean isCreationCours() {
        return creationCours;
    }

    public void setCreationCours(Boolean creationCours) {
        this.creationCours = creationCours;
    }

    public Boolean isWebAdaptatif() {
        return webAdaptatif;
    }

    public void setWebAdaptatif(Boolean webAdaptatif) {
        this.webAdaptatif = webAdaptatif;
    }

    public Boolean isMarquePage() {
        return marquePage;
    }

    public void setMarquePage(Boolean marquePage) {
        this.marquePage = marquePage;
    }

    public Boolean isCaptureImage() {
        return captureImage;
    }

    public void setCaptureImage(Boolean captureImage) {
        this.captureImage = captureImage;
    }

    public Boolean isZoom() {
        return zoom;
    }

    public void setZoom(Boolean zoom) {
        this.zoom = zoom;
    }

    public Boolean isFonctionsRecherche() {
        return fonctionsRecherche;
    }

    public void setFonctionsRecherche(Boolean fonctionsRecherche) {
        this.fonctionsRecherche = fonctionsRecherche;
    }

    public Boolean isCorrigesPourEnseignants() {
        return corrigesPourEnseignants;
    }

    public void setCorrigesPourEnseignants(Boolean corrigesPourEnseignants) {
        this.corrigesPourEnseignants = corrigesPourEnseignants;
    }

    public Boolean isAssignationTachesEleves() {
        return assignationTachesEleves;
    }

    public void setAssignationTachesEleves(Boolean assignationTachesEleves) {
        this.assignationTachesEleves = assignationTachesEleves;
    }

    public Boolean isPartageContenuEleves() {
        return partageContenuEleves;
    }

    public void setPartageContenuEleves(Boolean partageContenuEleves) {
        this.partageContenuEleves = partageContenuEleves;
    }

    public Boolean isExercicesInteractifs() {
        return exercicesInteractifs;
    }

    public void setExercicesInteractifs(Boolean exercicesInteractifs) {
        this.exercicesInteractifs = exercicesInteractifs;
    }

    public Boolean isExercicesAutoCorriges() {
        return exercicesAutoCorriges;
    }

    public void setExercicesAutoCorriges(Boolean exercicesAutoCorriges) {
        this.exercicesAutoCorriges = exercicesAutoCorriges;
    }

    public Boolean isExportReponsesEleves() {
        return exportReponsesEleves;
    }

    public void setExportReponsesEleves(Boolean exportReponsesEleves) {
        this.exportReponsesEleves = exportReponsesEleves;
    }

    public Boolean isImportDocument() {
        return importDocument;
    }

    public void setImportDocument(Boolean importDocument) {
        this.importDocument = importDocument;
    }

    public Boolean isExportDocument() {
        return exportDocument;
    }

    public void setExportDocument(Boolean exportDocument) {
        this.exportDocument = exportDocument;
    }

    public Boolean isExportSCORM() {
        return exportSCORM;
    }

    public void setExportSCORM(Boolean exportSCORM) {
        this.exportSCORM = exportSCORM;
    }

    public Boolean isPersonnalisationUserInterface() {
        return personnalisationUserInterface;
    }

    public void setPersonnalisationUserInterface(Boolean personnalisationUserInterface) {
        this.personnalisationUserInterface = personnalisationUserInterface;
    }

    public Boolean isModifContenuEditorial() {
        return modifContenuEditorial;
    }

    public void setModifContenuEditorial(Boolean modifContenuEditorial) {
        this.modifContenuEditorial = modifContenuEditorial;
    }

    public Boolean isDispositifDYS() {
        return dispositifDYS;
    }

    public void setDispositifDYS(Boolean dispositifDYS) {
        this.dispositifDYS = dispositifDYS;
    }

    public String getNbMaxiInstall() {
        return nbMaxiInstall;
    }

    public void setNbMaxiInstall(String nbMaxiInstall) {
        this.nbMaxiInstall = nbMaxiInstall;
    }

    public String getNbMaxSimultConnexions() {
        return nbMaxSimultConnexions;
    }

    public void setNbMaxSimultConnexions(String nbMaxSimultConnexions) {
        this.nbMaxSimultConnexions = nbMaxSimultConnexions;
    }

    public Boolean isMessagerie() {
        return messagerie;
    }

    public void setMessagerie(Boolean messagerie) {
        this.messagerie = messagerie;
    }

    public Long getArticleNumeriqueId() {
        return articleNumeriqueId;
    }

    public void setArticleNumeriqueId(Long articleNumeriqueId) {
        this.articleNumeriqueId = articleNumeriqueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TechnoDTO)) {
            return false;
        }

        return id != null && id.equals(((TechnoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TechnoDTO{" +
            "id=" + getId() +
            ", technologie='" + getTechnologie() + "'" +
            ", versionReader='" + getVersionReader() + "'" +
            ", availableHorsENT='" + isAvailableHorsENT() + "'" +
            ", availableViaENT='" + isAvailableViaENT() + "'" +
            ", availableViaGAR='" + isAvailableViaGAR() + "'" +
            ", typeLicenceGAR='" + getTypeLicenceGAR() + "'" +
            ", canUseOffline='" + isCanUseOffline() + "'" +
            ", oneClic='" + isOneClic() + "'" +
            ", exportCleUSB='" + isExportCleUSB() + "'" +
            ", deploiementMasse='" + isDeploiementMasse() + "'" +
            ", configurationMiniOS='" + getConfigurationMiniOS() + "'" +
            ", needFlash='" + isNeedFlash() + "'" +
            ", annotations='" + isAnnotations() + "'" +
            ", creationCours='" + isCreationCours() + "'" +
            ", webAdaptatif='" + isWebAdaptatif() + "'" +
            ", marquePage='" + isMarquePage() + "'" +
            ", captureImage='" + isCaptureImage() + "'" +
            ", zoom='" + isZoom() + "'" +
            ", fonctionsRecherche='" + isFonctionsRecherche() + "'" +
            ", corrigesPourEnseignants='" + isCorrigesPourEnseignants() + "'" +
            ", assignationTachesEleves='" + isAssignationTachesEleves() + "'" +
            ", partageContenuEleves='" + isPartageContenuEleves() + "'" +
            ", exercicesInteractifs='" + isExercicesInteractifs() + "'" +
            ", exercicesAutoCorriges='" + isExercicesAutoCorriges() + "'" +
            ", exportReponsesEleves='" + isExportReponsesEleves() + "'" +
            ", importDocument='" + isImportDocument() + "'" +
            ", exportDocument='" + isExportDocument() + "'" +
            ", exportSCORM='" + isExportSCORM() + "'" +
            ", personnalisationUserInterface='" + isPersonnalisationUserInterface() + "'" +
            ", modifContenuEditorial='" + isModifContenuEditorial() + "'" +
            ", dispositifDYS='" + isDispositifDYS() + "'" +
            ", nbMaxiInstall='" + getNbMaxiInstall() + "'" +
            ", nbMaxSimultConnexions='" + getNbMaxSimultConnexions() + "'" +
            ", messagerie='" + isMessagerie() + "'" +
            ", articleNumeriqueId=" + getArticleNumeriqueId() +
            "}";
    }
}
