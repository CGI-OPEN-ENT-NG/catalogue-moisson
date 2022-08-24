package fr.openent.moisson.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.openent.moisson.domain.enumeration.Technologie;
import fr.openent.moisson.domain.enumeration.TypeLicenceGAR;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A Techno.
 */
@Entity
@Table(name = "techno")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
// @org.springframework.data.elasticsearch.annotations.Document(indexName = "techno")
@Setting(settingPath = "/settings/settings.json")
public class Techno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    // @Enumerated(EnumType.STRING)
    @Column(name = "technologie", columnDefinition = "default 'Windows'")
    @JsonProperty("Technologie")
    @Field(type = FieldType.Keyword, normalizer = "lower_normalizer")
    private String technologie;

    @Column(name = "version_reader")
    @JsonProperty("VersionReader")
    @Field
    private String versionReader;

    @Column(name = "available_hors_ent")
    @JsonProperty("AvailableHorsENT")
    @Field
    private Boolean availableHorsENT;

    @Column(name = "available_via_ent")
    @JsonProperty("AvailableViaENT")
    @Field
    private Boolean availableViaENT;

    @Column(name = "available_via_gar")
    @JsonProperty("AvailableViaGAR")
    @Field
    private Boolean availableViaGAR;

    // @Enumerated(EnumType.STRING)
    @Column(name = "type_licence_gar", columnDefinition = "default 'non transferable'", nullable = false)    @JsonProperty("TypeLicenceGAR")
    @Field
    private String typeLicenceGAR;

    @Column(name = "can_use_offline")
    @JsonProperty("CanUseOffline")
    @Field
    private Boolean canUseOffline;

    @Column(name = "one_clic")
    @JsonProperty("Oneclic")
    @Field
    private Boolean oneClic;

    @Column(name = "export_cle_usb")
    @JsonProperty("ExportCleUSB")
    @Field
    private Boolean exportCleUSB;

    @Column(name = "deploiement_masse")
    @JsonProperty("DeploiementMasse")
    @Field
    private Boolean deploiementMasse;

    @Column(name = "configuration_mini_os")
    @JsonProperty("ConfigurationMiniOS")
    @Field(type = FieldType.Keyword, normalizer = "lower_normalizer")
    private String configurationMiniOS;

    @Column(name = "need_flash")
    @JsonProperty("NeedFlash")
    @Field
    private Boolean needFlash;

    @Column(name = "annotations")
    @JsonProperty("Annotations")
    @Field
    private Boolean annotations;

    @Column(name = "creation_cours")
    @JsonProperty("CreationCours")
    @Field
    private Boolean creationCours;

    @Column(name = "web_adaptatif")
    @JsonProperty("WebAdaptatif")
    @Field
    private Boolean webAdaptatif;

    @Column(name = "marque_page")
    @JsonProperty("MarquePage")
    @Field
    private Boolean marquePage;

    @Column(name = "capture_image")
    @JsonProperty("CaptureImage")
    @Field
    private Boolean captureImage;

    @Column(name = "zoom")
    @JsonProperty("Zoom")
    @Field
    private Boolean zoom;

    @Column(name = "fonctions_recherche")
    @JsonProperty("FonctionsRecherche")
    @Field
    private Boolean fonctionsRecherche;

    @Column(name = "corriges_pour_enseignants")
    @JsonProperty("CorrigesPourEnseignants")
    @Field
    private Boolean corrigesPourEnseignants;

    @Column(name = "assignation_taches_eleves")
    @JsonProperty("AssignationTachesEleves")
    @Field
    private Boolean assignationTachesEleves;

    @Column(name = "partage_contenu_eleves")
    @JsonProperty("PartageContenuEleves")
    @Field
    private Boolean partageContenuEleves;

    @Column(name = "exercices_interactifs")
    @JsonProperty("ExercicesInteractifs")
    @Field
    private Boolean exercicesInteractifs;

    @Column(name = "exercices_auto_corriges")
    @JsonProperty("ExercicesAutoCorriges")
    @Field
    private Boolean exercicesAutoCorriges;

    @Column(name = "export_reponses_eleves")
    @JsonProperty("ExportReponsesEleves")
    @Field
    private Boolean exportReponsesEleves;

    @Column(name = "import_document")
    @JsonProperty("ImportDocument")
    @Field
    private Boolean importDocument;

    @Column(name = "export_document")
    @JsonProperty("ExportDocument")
    @Field
    private Boolean exportDocument;

    @Column(name = "export_scorm")
    @JsonProperty("ExportSCORM")
    @Field
    private Boolean exportSCORM;

    @Column(name = "personnalisation_user_interface")
    @JsonProperty("PersonnalisationUserInterface")
    @Field
    private Boolean personnalisationUserInterface;

    @Column(name = "modif_contenu_editorial")
    @JsonProperty("ModifContenuEditorial")
    @Field
    private Boolean modifContenuEditorial;

    @Column(name = "dispositif_dys")
    @JsonProperty("DispositifDYS")
    @Field
    private Boolean dispositifDYS;

    @Column(name = "nb_maxi_install")
    @JsonProperty("NbMaxiInstall")
    @Field
    private String nbMaxiInstall;

    @Column(name = "nb_max_simult_connexions")
    @JsonProperty("NbMaxSimultConnexions")
    @Field
    private String nbMaxSimultConnexions;

    @Column(name = "messagerie")
    @JsonProperty("Messagerie")
    @Field
    private Boolean messagerie;

    @Column(name = "niveau_rgaa")
    @JsonProperty("NiveauRGAA")
    @Field
    private String niveauRGAA;

    @Column(name = "niveau_a_2_rne")
    @JsonProperty("NiveauA2RNE")
    @Field
    private Integer niveauA2RNE;

    @Column(name = "conforme_sco_lomfr")
    @JsonProperty("ConformeScoLOMFR")
    @Field
    private Boolean conformeScoLOMFR;

    @Column(name = "conforme_scorm")
    @JsonProperty("ConformeSCORM")
    @Field
    private Boolean conformeSCORM;

    @Column(name = "conforme_ims_qti")
    @JsonProperty("ConformeIMS_QTI")
    @Field
    private Boolean conformeIMS_QTI;

    @Column(name = "conformex_api")
    @JsonProperty("ConformexAPI")
    @Field
    private Boolean conformexAPI;

    @Column(name = "format_video")
    @JsonProperty("FormatVideo")
    @Field
    private String formatVideo;

    @Column(name = "ts_last_modif")
    @JsonProperty("ts_lastModif")
    @Field
    private Long ts_LastModif;

    @Column(name = "configuration_mini_navigateur")
    @JsonProperty("ConfigurationMiniNavigateur")
    @Field
    private String configurationMiniNavigateur;

    @Column(name = "parcours_eleve_personnalise")
    @JsonProperty("ParcoursElevePersonnalise")
    @Field
    private Boolean parcoursElevePersonnalise;

    @Column(name = "gestion_groupes_eleve")
    @JsonProperty("GestionGroupesEleve")
    @Field
    private Boolean gestionGroupesEleve;

    @Column(name = "personnalisation_cont_enseign")
    @JsonProperty("PersonnalisationContEnseign")
    @Field
    private Boolean personnalisationContEnseign;

    @Column(name = "edition_formule_math")
    @JsonProperty("EditionFormuleMath")
    @Field
    private Boolean editionFormuleMath;

    @Column(name = "correct_travail_eleve_par_enseign")
    @JsonProperty("CorrectTravailEleveParEnseign")
    @Field
    private Boolean correctTravailEleveParEnseign;

    @Column(name = "suivi_eleve")
    @JsonProperty("SuiviEleve")
    @Field
    private Boolean suiviEleve;

    @Column(name = "commentaire")
    @JsonProperty("Commentaire")
    @Field
    private String commentaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "technos", allowSetters = true)
    private ArticleNumerique articleNumerique;

    // jhipster-needle-entity-add-field - JHipster will add fields here
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

    public Techno technologie(String technologie) {
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

    public String getTypeLicenceGAR() {
        return typeLicenceGAR;
    }

    public void setTypeLicenceGAR(String typeLicenceGAR) {
        this.typeLicenceGAR = typeLicenceGAR;
    }

    public Techno typeLicenceGAR(String typeLicenceGAR) {
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

    public void setDispositifDYS(Boolean dispositifDYS) {this.dispositifDYS = dispositifDYS;}

    public String getNbMaxiInstall() {
        return nbMaxiInstall;
    }

    public Techno nbMaxiInstall(String nbMaxiInstall) {
        this.nbMaxiInstall = nbMaxiInstall;
        return this;
    }

    public void setNbMaxiInstall(String nbMaxiInstall) {
        this.nbMaxiInstall = nbMaxiInstall;
    }

    public String getNbMaxSimultConnexions() {
        return nbMaxSimultConnexions;
    }

    public Techno nbMaxSimultConnexions(String nbMaxSimultConnexions) {
        this.nbMaxSimultConnexions = nbMaxSimultConnexions;
        return this;
    }

    public void setNbMaxSimultConnexions(String nbMaxSimultConnexions) {
        this.nbMaxSimultConnexions = nbMaxSimultConnexions;
    }

    public Boolean isMessagerie() {
        return messagerie;
    }

    public Techno messagerie(Boolean messagerie) {
        this.messagerie = messagerie;
        return this;
    }

    public void setMessagerie(Boolean messagerie) {this.messagerie = messagerie; }

    public String getNiveauRGAA() {
        return niveauRGAA;
    }

    public Techno niveauRGAA(String niveauRGAA) {
        this.niveauRGAA = niveauRGAA;
        return this;
    }

    public void setNiveauRGAA(String niveauRGAA) {
        this.niveauRGAA = niveauRGAA;
    }

    public Integer getNiveauA2RNE() {
        return niveauA2RNE;
    }

    public Techno niveauA2RNE(Integer niveauA2RNE) {
        this.niveauA2RNE = niveauA2RNE;
        return this;
    }

    public void setNiveauA2RNE(Integer niveauA2RNE) {
        this.niveauA2RNE = niveauA2RNE;
    }

    public Boolean isConformeScoLOMFR() {
        return conformeScoLOMFR;
    }

    public Techno conformeScoLOMFR(Boolean conformeScoLOMFR) {
        this.conformeScoLOMFR = conformeScoLOMFR;
        return this;
    }

    public void setConformeScoLOMFR(Boolean conformeScoLOMFR) {
        this.conformeScoLOMFR = conformeScoLOMFR;
    }

    public Boolean isConformeSCORM() {
        return conformeSCORM;
    }

    public Techno conformeSCORM(Boolean conformeSCORM) {
        this.conformeSCORM = conformeSCORM;
        return this;
    }

    public void setConformeSCORM(Boolean conformeSCORM) {
        this.conformeSCORM = conformeSCORM;
    }

    public Boolean isConformeIMS_QTI() {
        return conformeIMS_QTI;
    }

    public Techno conformeIMS_QTI(Boolean conformeIMS_QTI) {
        this.conformeIMS_QTI = conformeIMS_QTI;
        return this;
    }

    public void setConformeIMS_QTI(Boolean conformeIMS_QTI) {
        this.conformeIMS_QTI = conformeIMS_QTI;
    }

    public Boolean isConformexAPI() {
        return conformexAPI;
    }

    public Techno conformexAPI(Boolean conformexAPI) {
        this.conformexAPI = conformexAPI;
        return this;
    }

    public void setConformexAPI(Boolean conformexAPI) {
        this.conformexAPI = conformexAPI;
    }

    public String getFormatVideo() {
        return formatVideo;
    }

    public Techno formatVideo(String formatVideo) {
        this.formatVideo = formatVideo;
        return this;
    }

    public void setFormatVideo(String formatVideo) {
        this.formatVideo = formatVideo;
    }

    public Long getTs_LastModif() {
        return ts_LastModif;
    }

    public Techno ts_LastModif(Long ts_LastModif) {
        this.ts_LastModif = ts_LastModif;
        return this;
    }

    public void setTs_LastModif(Long ts_LastModif) {
        this.ts_LastModif = ts_LastModif;
    }

    public String getConfigurationMiniNavigateur() {
        return configurationMiniNavigateur;
    }

    public Techno configurationMiniNavigateur(String configurationMiniNavigateur) {
        this.configurationMiniNavigateur = configurationMiniNavigateur;
        return this;
    }

    public void setConfigurationMiniNavigateur(String configurationMiniNavigateur) {
        this.configurationMiniNavigateur = configurationMiniNavigateur;
    }

    public Boolean isParcoursElevePersonnalise() {
        return parcoursElevePersonnalise;
    }

    public Techno parcoursElevePersonnalise(Boolean parcoursElevePersonnalise) {
        this.parcoursElevePersonnalise = parcoursElevePersonnalise;
        return this;
    }

    public void setParcoursElevePersonnalise(Boolean parcoursElevePersonnalise) {
        this.parcoursElevePersonnalise = parcoursElevePersonnalise;
    }

    public Boolean isGestionGroupesEleve() {
        return gestionGroupesEleve;
    }

    public Techno gestionGroupesEleve(Boolean gestionGroupesEleve) {
        this.gestionGroupesEleve = gestionGroupesEleve;
        return this;
    }

    public void setGestionGroupesEleve(Boolean gestionGroupesEleve) {
        this.gestionGroupesEleve = gestionGroupesEleve;
    }

    public Boolean isPersonnalisationContEnseign() {
        return personnalisationContEnseign;
    }

    public Techno personnalisationContEnseign(Boolean personnalisationContEnseign) {
        this.personnalisationContEnseign = personnalisationContEnseign;
        return this;
    }

    public void setPersonnalisationContEnseign(Boolean personnalisationContEnseign) {
        this.personnalisationContEnseign = personnalisationContEnseign;
    }

    public Boolean isEditionFormuleMath() {
        return editionFormuleMath;
    }

    public Techno editionFormuleMath(Boolean editionFormuleMath) {
        this.editionFormuleMath = editionFormuleMath;
        return this;
    }

    public void setEditionFormuleMath(Boolean editionFormuleMath) {
        this.editionFormuleMath = editionFormuleMath;
    }

    public Boolean isCorrectTravailEleveParEnseign() {
        return correctTravailEleveParEnseign;
    }

    public Techno correctTravailEleveParEnseign(Boolean correctTravailEleveParEnseign) {
        this.correctTravailEleveParEnseign = correctTravailEleveParEnseign;
        return this;
    }

    public void setCorrectTravailEleveParEnseign(Boolean correctTravailEleveParEnseign) {
        this.correctTravailEleveParEnseign = correctTravailEleveParEnseign;
    }

    public Boolean isSuiviEleve() {
        return suiviEleve;
    }

    public Techno suiviEleve(Boolean suiviEleve) {
        this.suiviEleve = suiviEleve;
        return this;
    }

    public void setSuiviEleve(Boolean suiviEleve) {
        this.suiviEleve = suiviEleve;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Techno commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
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
            ", niveauRGAA='" + getNiveauRGAA() + "'" +
            ", niveauA2RNE=" + getNiveauA2RNE() +
            ", conformeScoLOMFR='" + isConformeScoLOMFR() + "'" +
            ", conformeSCORM='" + isConformeSCORM() + "'" +
            ", conformeIMS_QTI='" + isConformeIMS_QTI() + "'" +
            ", conformexAPI='" + isConformexAPI() + "'" +
            ", formatVideo='" + getFormatVideo() + "'" +
            ", ts_LastModif=" + getTs_LastModif() +
            ", configurationMiniNavigateur='" + getConfigurationMiniNavigateur() + "'" +
            ", parcoursElevePersonnalise='" + isParcoursElevePersonnalise() + "'" +
            ", gestionGroupesEleve='" + isGestionGroupesEleve() + "'" +
            ", personnalisationContEnseign='" + isPersonnalisationContEnseign() + "'" +
            ", editionFormuleMath='" + isEditionFormuleMath() + "'" +
            ", correctTravailEleveParEnseign='" + isCorrectTravailEleveParEnseign() + "'" +
            ", suiviEleve='" + isSuiviEleve() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
