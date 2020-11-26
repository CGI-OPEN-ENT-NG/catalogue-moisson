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

    private Technologie technologie;

    private String versionReader;

    private Boolean availableHorsENT;

    private Boolean availableViaENT;

    private Boolean availableViaGAR;

    @NotNull
    private TypeLicenceGAR typeLicenceGAR;

    private Boolean canUseOffline;

    private Boolean oneClic;

    private Boolean exportCleUSB;

    private Boolean deploiementMasse;

    private String configurationMiniOS;

    private Boolean needFlash;

    private Boolean annotations;

    private Boolean creationCours;

    private Integer nbMaxiInstall;

    private Integer nbMaxSimultConnexions;

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

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Technologie getTechnologie() {
        return technologie;
    }

    public void setTechnologie(Technologie technologie) {
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

    public TypeLicenceGAR getTypeLicenceGAR() {
        return typeLicenceGAR;
    }

    public void setTypeLicenceGAR(TypeLicenceGAR typeLicenceGAR) {
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

    public Integer getNbMaxiInstall() {
        return nbMaxiInstall;
    }

    public void setNbMaxiInstall(Integer nbMaxiInstall) {
        this.nbMaxiInstall = nbMaxiInstall;
    }

    public Integer getNbMaxSimultConnexions() {
        return nbMaxSimultConnexions;
    }

    public void setNbMaxSimultConnexions(Integer nbMaxSimultConnexions) {
        this.nbMaxSimultConnexions = nbMaxSimultConnexions;
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
            ", nbMaxiInstall=" + getNbMaxiInstall() +
            ", nbMaxSimultConnexions=" + getNbMaxSimultConnexions() +
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
            "}";
    }
}
