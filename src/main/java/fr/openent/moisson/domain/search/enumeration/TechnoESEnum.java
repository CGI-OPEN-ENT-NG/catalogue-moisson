package fr.openent.moisson.domain.search.enumeration;

import fr.openent.moisson.domain.search.ElasticSearchConstants;

public enum TechnoESEnum {

    /*
     * CONSTANTES pour TvaESEnum
     */
    ID("id", ElasticSearchConstants.TEXT),
    Technologie("technologie", ElasticSearchConstants.TEXT),
    VersionReader("versionReader", ElasticSearchConstants.TEXT),
    AvailableHorsENT("availableHorsENT", ElasticSearchConstants.BOOLEAN),
    AvailableViaENT("availableViaENT", ElasticSearchConstants.BOOLEAN),
    AvailableViaGAR("availableViaGAR", ElasticSearchConstants.BOOLEAN),
    TypeLicenceGAR("typeLicenceGAR", ElasticSearchConstants.TEXT),
    CanUseOffline("canUseOffline", ElasticSearchConstants.BOOLEAN),
    Oneclic("oneClic", ElasticSearchConstants.BOOLEAN),
    ExportCleUSB("exportCleUSB", ElasticSearchConstants.BOOLEAN),
    DeploiementMasse("deploiementMasse", ElasticSearchConstants.BOOLEAN),
    ConfigurationMiniOS("configurationMiniOS", ElasticSearchConstants.TEXT),
    NeedFlash("needFlash", ElasticSearchConstants.BOOLEAN),
    Annotations("annotations", ElasticSearchConstants.BOOLEAN),
    CreationCours("creationCours", ElasticSearchConstants.BOOLEAN),
    NbMaxiInstall("nbMaxiInstall", ElasticSearchConstants.TEXT),
    NbMaxSimultConnexions("nbMaxSimultConnexions", ElasticSearchConstants.TEXT),
    WebAdaptatif("webAdaptatif", ElasticSearchConstants.BOOLEAN),
    MarquePage("marquePage", ElasticSearchConstants.BOOLEAN),
    CaptureImage("captureImage", ElasticSearchConstants.BOOLEAN),
    Zoom("zoom", ElasticSearchConstants.BOOLEAN),
    FonctionsRecherche("fonctionsRecherche", ElasticSearchConstants.BOOLEAN),
    CorrigesPourEnseignants("corrigesPourEnseignants", ElasticSearchConstants.BOOLEAN),
    AssignationTachesEleves("assignationTachesEleves", ElasticSearchConstants.BOOLEAN),
    PartageContenuEleves("partageContenuEleves", ElasticSearchConstants.BOOLEAN),
    ExercicesInteractifs("exercicesInteractifs", ElasticSearchConstants.BOOLEAN),
    ExercicesAutoCorriges("exercicesAutoCorriges", ElasticSearchConstants.BOOLEAN),
    ExportReponsesEleves("exportReponsesEleves", ElasticSearchConstants.BOOLEAN),
    ImportDocument("importDocument", ElasticSearchConstants.BOOLEAN),
    ExportDocument("exportDocument", ElasticSearchConstants.BOOLEAN),
    ExportSCORM("exportSCORM", ElasticSearchConstants.BOOLEAN),
    PersonnalisationUserInterface("personnalisationUserInterface", ElasticSearchConstants.BOOLEAN),
    ModifContenuEditorial("modifContenuEditorial", ElasticSearchConstants.BOOLEAN),
    DispositifDYS("dispositifDYS", ElasticSearchConstants.BOOLEAN);

    /**
     * Nom du champs
     */
    String fieldName;

    /**
     * Type de champs : text, keyword, date, long, boolean, multi-field....
     */
    String fieldType;

    TechnoESEnum(String fieldName, String fieldType) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getFieldType() {
        return this.fieldType;
    }

}
