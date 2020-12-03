package fr.openent.moisson.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

import fr.openent.moisson.domain.enumeration.Technologie;

import fr.openent.moisson.domain.enumeration.TypeLicenceGAR;

/**
 * A Techno.
 */
@Entity
@Table(name = "techno")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "techno")
public class Techno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "technologie")
    @JsonProperty("Technologie")
    private Technologie technologie;

    @Column(name = "version_reader")
    @JsonProperty("VersionReader")
    private String versionReader;

    @Column(name = "available_hors_ent")
    @JsonProperty("AvailableHorsENT")
    private Boolean availableHorsENT;

    @Column(name = "available_via_ent")
    @JsonProperty("AvailableViaENT")
    private Boolean availableViaENT;

    @Column(name = "available_via_gar")
    @JsonProperty("AvailableViaGAR")
    private Boolean availableViaGAR;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_licence_gar", nullable = false)
    @JsonProperty("TypeLicenceGA")
    private TypeLicenceGAR typeLicenceGAR;

    @Column(name = "can_use_offline")
    @JsonProperty("CanUseOffline")
    private Boolean canUseOffline;

    @Column(name = "one_clic")
    @JsonProperty("oneClic")
    private Boolean oneClic;

    @Column(name = "export_cle_usb")
    @JsonProperty("ExportCleUSB")
    private Boolean exportCleUSB;

    @Column(name = "deploiement_masse")
    @JsonProperty("deploiementMasse")
    private Boolean deploiementMasse;

    @Column(name = "configuration_mini_os")
    @JsonProperty("ConfigurationMiniOS")
    private String configurationMiniOS;

    @Column(name = "need_flash")
    @JsonProperty("NeedFlash")
     private Boolean needFlash;

    @Column(name = "annotations")
    @JsonProperty("Annotations")
    private Boolean annotations;

    @Column(name = "creation_cours")
    @JsonProperty("CreationCours")
    private Boolean creationCours;

    @Column(name = "nb_maxi_install")
    @JsonProperty("NbMaxiInstall")
    private Integer nbMaxiInstall;

    @Column(name = "nb_max_simult_connexions")
    @JsonProperty("NbMaxSimultConnexions")
    private Integer nbMaxSimultConnexions;

    @Column(name = "web_adaptatif")
    @JsonProperty("WebAdaptatif")
    private Boolean webAdaptatif;

    @Column(name = "marque_page")
    @JsonProperty("MarquePage")
    private Boolean marquePage;

    @Column(name = "capture_image")
    @JsonProperty("CaptureImage")
    private Boolean captureImage;

    @Column(name = "zoom")
    @JsonProperty("Zoom")
    private Boolean zoom;

    @Column(name = "fonctions_recherche")
    @JsonProperty("FonctionsRecherche")
    private Boolean fonctionsRecherche;

    @Column(name = "corriges_pour_enseignants")
    @JsonProperty("CorrigesPourEnseignants")
    private Boolean corrigesPourEnseignants;

    @Column(name = "assignation_taches_eleves")
    @JsonProperty("AssignationTachesEleves")
    private Boolean assignationTachesEleves;

    @Column(name = "partage_contenu_eleves")
    @JsonProperty("PartageContenuEleves")
    private Boolean partageContenuEleves;

    @Column(name = "exercices_interactifs")
    @JsonProperty("ExercicesInteractifs")
    private Boolean exercicesInteractifs;

    @Column(name = "exercices_auto_corriges")
    @JsonProperty("ExercicesAutoCorriges")
    private Boolean exercicesAutoCorriges;

    @Column(name = "export_reponses_eleves")
    @JsonProperty("ExportReponsesEleves")
    private Boolean exportReponsesEleves;

    @Column(name = "import_document")
    @JsonProperty("ImportDocument")
    private Boolean importDocument;

    @Column(name = "export_document")
    @JsonProperty("ExportDocument")
    private Boolean exportDocument;

    @Column(name = "export_scorm")
    @JsonProperty("ExportSCORM")
    private Boolean exportSCORM;

    @Column(name = "personnalisation_user_interface")
    @JsonProperty("PersonnalisationUserInterface")
    private Boolean personnalisationUserInterface;

    @Column(name = "modif_contenu_editorial")
    @JsonProperty("ModifContenuEditorial")
    private Boolean modifContenuEditorial;

    @Column(name = "dispositif_dys")
    @JsonProperty("DispositifDYS")
    private Boolean dispositifDYS;

    @ManyToOne
    @JsonIgnoreProperties(value = "technos", allowSetters = true)
    private ArticleNumerique articleNumerique;

    // jhipster-needle-entity-add-field - JHipster will add fields here
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

    public Techno technologie(Technologie technologie) {
        this.technologie = technologie;
        return this;
    }

    public String getVersionReader() {
        return versionReader;
    }

    public void setVersionReader(String versionReader) {
        this.versionReader = versionReader;
    }

    public Techno versionReader(String versionReader) {
        this.versionReader = versionReader;
        return this;
    }

    public Boolean isAvailableHorsENT() {
        return availableHorsENT;
    }

    public Techno availableHorsENT(Boolean availableHorsENT) {
        this.availableHorsENT = availableHorsENT;
        return this;
    }

    public void setAvailableHorsENT(Boolean availableHorsENT) {
        this.availableHorsENT = availableHorsENT;
    }

    public Boolean isAvailableViaENT() {
        return availableViaENT;
    }

    public Techno availableViaENT(Boolean availableViaENT) {
        this.availableViaENT = availableViaENT;
        return this;
    }

    public void setAvailableViaENT(Boolean availableViaENT) {
        this.availableViaENT = availableViaENT;
    }

    public Boolean isAvailableViaGAR() {
        return availableViaGAR;
    }

    public Techno availableViaGAR(Boolean availableViaGAR) {
        this.availableViaGAR = availableViaGAR;
        return this;
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

    public Techno typeLicenceGAR(TypeLicenceGAR typeLicenceGAR) {
        this.typeLicenceGAR = typeLicenceGAR;
        return this;
    }

    public Boolean isCanUseOffline() {
        return canUseOffline;
    }

    public Techno canUseOffline(Boolean canUseOffline) {
        this.canUseOffline = canUseOffline;
        return this;
    }

    public void setCanUseOffline(Boolean canUseOffline) {
        this.canUseOffline = canUseOffline;
    }

    public Boolean isOneClic() {
        return oneClic;
    }

    public Techno oneClic(Boolean oneClic) {
        this.oneClic = oneClic;
        return this;
    }

    public void setOneClic(Boolean oneClic) {
        this.oneClic = oneClic;
    }

    public Boolean isExportCleUSB() {
        return exportCleUSB;
    }

    public Techno exportCleUSB(Boolean exportCleUSB) {
        this.exportCleUSB = exportCleUSB;
        return this;
    }

    public void setExportCleUSB(Boolean exportCleUSB) {
        this.exportCleUSB = exportCleUSB;
    }

    public Boolean isDeploiementMasse() {
        return deploiementMasse;
    }

    public Techno deploiementMasse(Boolean deploiementMasse) {
        this.deploiementMasse = deploiementMasse;
        return this;
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

    public Techno configurationMiniOS(String configurationMiniOS) {
        this.configurationMiniOS = configurationMiniOS;
        return this;
    }

    public Boolean isNeedFlash() {
        return needFlash;
    }

    public Techno needFlash(Boolean needFlash) {
        this.needFlash = needFlash;
        return this;
    }

    public void setNeedFlash(Boolean needFlash) {
        this.needFlash = needFlash;
    }

    public Boolean isAnnotations() {
        return annotations;
    }

    public Techno annotations(Boolean annotations) {
        this.annotations = annotations;
        return this;
    }

    public void setAnnotations(Boolean annotations) {
        this.annotations = annotations;
    }

    public Boolean isCreationCours() {
        return creationCours;
    }

    public Techno creationCours(Boolean creationCours) {
        this.creationCours = creationCours;
        return this;
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

    public Techno nbMaxiInstall(Integer nbMaxiInstall) {
        this.nbMaxiInstall = nbMaxiInstall;
        return this;
    }

    public Integer getNbMaxSimultConnexions() {
        return nbMaxSimultConnexions;
    }

    public void setNbMaxSimultConnexions(Integer nbMaxSimultConnexions) {
        this.nbMaxSimultConnexions = nbMaxSimultConnexions;
    }

    public Techno nbMaxSimultConnexions(Integer nbMaxSimultConnexions) {
        this.nbMaxSimultConnexions = nbMaxSimultConnexions;
        return this;
    }

    public Boolean isWebAdaptatif() {
        return webAdaptatif;
    }

    public Techno webAdaptatif(Boolean webAdaptatif) {
        this.webAdaptatif = webAdaptatif;
        return this;
    }

    public void setWebAdaptatif(Boolean webAdaptatif) {
        this.webAdaptatif = webAdaptatif;
    }

    public Boolean isMarquePage() {
        return marquePage;
    }

    public Techno marquePage(Boolean marquePage) {
        this.marquePage = marquePage;
        return this;
    }

    public void setMarquePage(Boolean marquePage) {
        this.marquePage = marquePage;
    }

    public Boolean isCaptureImage() {
        return captureImage;
    }

    public Techno captureImage(Boolean captureImage) {
        this.captureImage = captureImage;
        return this;
    }

    public void setCaptureImage(Boolean captureImage) {
        this.captureImage = captureImage;
    }

    public Boolean isZoom() {
        return zoom;
    }

    public Techno zoom(Boolean zoom) {
        this.zoom = zoom;
        return this;
    }

    public void setZoom(Boolean zoom) {
        this.zoom = zoom;
    }

    public Boolean isFonctionsRecherche() {
        return fonctionsRecherche;
    }

    public Techno fonctionsRecherche(Boolean fonctionsRecherche) {
        this.fonctionsRecherche = fonctionsRecherche;
        return this;
    }

    public void setFonctionsRecherche(Boolean fonctionsRecherche) {
        this.fonctionsRecherche = fonctionsRecherche;
    }

    public Boolean isCorrigesPourEnseignants() {
        return corrigesPourEnseignants;
    }

    public Techno corrigesPourEnseignants(Boolean corrigesPourEnseignants) {
        this.corrigesPourEnseignants = corrigesPourEnseignants;
        return this;
    }

    public void setCorrigesPourEnseignants(Boolean corrigesPourEnseignants) {
        this.corrigesPourEnseignants = corrigesPourEnseignants;
    }

    public Boolean isAssignationTachesEleves() {
        return assignationTachesEleves;
    }

    public Techno assignationTachesEleves(Boolean assignationTachesEleves) {
        this.assignationTachesEleves = assignationTachesEleves;
        return this;
    }

    public void setAssignationTachesEleves(Boolean assignationTachesEleves) {
        this.assignationTachesEleves = assignationTachesEleves;
    }

    public Boolean isPartageContenuEleves() {
        return partageContenuEleves;
    }

    public Techno partageContenuEleves(Boolean partageContenuEleves) {
        this.partageContenuEleves = partageContenuEleves;
        return this;
    }

    public void setPartageContenuEleves(Boolean partageContenuEleves) {
        this.partageContenuEleves = partageContenuEleves;
    }

    public Boolean isExercicesInteractifs() {
        return exercicesInteractifs;
    }

    public Techno exercicesInteractifs(Boolean exercicesInteractifs) {
        this.exercicesInteractifs = exercicesInteractifs;
        return this;
    }

    public void setExercicesInteractifs(Boolean exercicesInteractifs) {
        this.exercicesInteractifs = exercicesInteractifs;
    }

    public Boolean isExercicesAutoCorriges() {
        return exercicesAutoCorriges;
    }

    public Techno exercicesAutoCorriges(Boolean exercicesAutoCorriges) {
        this.exercicesAutoCorriges = exercicesAutoCorriges;
        return this;
    }

    public void setExercicesAutoCorriges(Boolean exercicesAutoCorriges) {
        this.exercicesAutoCorriges = exercicesAutoCorriges;
    }

    public Boolean isExportReponsesEleves() {
        return exportReponsesEleves;
    }

    public Techno exportReponsesEleves(Boolean exportReponsesEleves) {
        this.exportReponsesEleves = exportReponsesEleves;
        return this;
    }

    public void setExportReponsesEleves(Boolean exportReponsesEleves) {
        this.exportReponsesEleves = exportReponsesEleves;
    }

    public Boolean isImportDocument() {
        return importDocument;
    }

    public Techno importDocument(Boolean importDocument) {
        this.importDocument = importDocument;
        return this;
    }

    public void setImportDocument(Boolean importDocument) {
        this.importDocument = importDocument;
    }

    public Boolean isExportDocument() {
        return exportDocument;
    }

    public Techno exportDocument(Boolean exportDocument) {
        this.exportDocument = exportDocument;
        return this;
    }

    public void setExportDocument(Boolean exportDocument) {
        this.exportDocument = exportDocument;
    }

    public Boolean isExportSCORM() {
        return exportSCORM;
    }

    public Techno exportSCORM(Boolean exportSCORM) {
        this.exportSCORM = exportSCORM;
        return this;
    }

    public void setExportSCORM(Boolean exportSCORM) {
        this.exportSCORM = exportSCORM;
    }

    public Boolean isPersonnalisationUserInterface() {
        return personnalisationUserInterface;
    }

    public Techno personnalisationUserInterface(Boolean personnalisationUserInterface) {
        this.personnalisationUserInterface = personnalisationUserInterface;
        return this;
    }

    public void setPersonnalisationUserInterface(Boolean personnalisationUserInterface) {
        this.personnalisationUserInterface = personnalisationUserInterface;
    }

    public Boolean isModifContenuEditorial() {
        return modifContenuEditorial;
    }

    public Techno modifContenuEditorial(Boolean modifContenuEditorial) {
        this.modifContenuEditorial = modifContenuEditorial;
        return this;
    }

    public void setModifContenuEditorial(Boolean modifContenuEditorial) {
        this.modifContenuEditorial = modifContenuEditorial;
    }

    public Boolean isDispositifDYS() {
        return dispositifDYS;
    }

    public Techno dispositifDYS(Boolean dispositifDYS) {
        this.dispositifDYS = dispositifDYS;
        return this;
    }

    public void setDispositifDYS(Boolean dispositifDYS) {
        this.dispositifDYS = dispositifDYS;
    }

    public ArticleNumerique getArticleNumerique() {
        return articleNumerique;
    }

    public void setArticleNumerique(ArticleNumerique articleNumerique) {
        this.articleNumerique = articleNumerique;
    }

    public Techno articleNumerique(ArticleNumerique articleNumerique) {
        this.articleNumerique = articleNumerique;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Techno)) {
            return false;
        }
        return id != null && id.equals(((Techno) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Techno{" +
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
