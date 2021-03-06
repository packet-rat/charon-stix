package io.digitalstate.stix.relationshipobjects.properties;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import io.digitalstate.stix.bundle.BundleObject;
import io.digitalstate.stix.datamarkings.definitions.MarkingDefinition;
import io.digitalstate.stix.datamarkings.granular.GranularMarking;
import io.digitalstate.stix.domainobjects.Identity;
import io.digitalstate.stix.domainobjects.types.ExternalReference;
import io.digitalstate.stix.helpers.StixDataFormats;
import io.digitalstate.stix.helpers.StixSpecVersion;
import org.apache.commons.lang3.StringUtils;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public abstract class CommonProperties {

    @JsonIgnore
    private final String specVersion = StixSpecVersion.SPECVERSION;

    private String type;
    private String id;

    private Identity createdByRef = null;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = StixDataFormats.DATEPATTERN, timezone = StixDataFormats.DATETIMEZONE)
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private ZonedDateTime created = ZonedDateTime.now();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = StixDataFormats.DATEPATTERN, timezone = StixDataFormats.DATETIMEZONE)
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private ZonedDateTime modified = this.created;

    private boolean revoked = false;

    @JsonInclude(NON_NULL)
    private LinkedHashSet<String> labels = null;

    @JsonProperty("external_references")
    @JsonInclude(NON_NULL)
    private LinkedHashSet<ExternalReference> externalReferences = null;

    private LinkedHashSet<MarkingDefinition> objectMarkingRefs = null;

    @JsonProperty("granular_markings")
    @JsonInclude(NON_NULL)
    private LinkedHashSet<GranularMarking> granularMarkings = null;

    private HashMap<String, Object> customProperties = null;


    //
    // Getters and Setters
    //

    public String getType() {
        return type;
    }

    public void setType(String type) {
        Objects.requireNonNull(type, "Type cannot be null");
        if (StringUtils.isNotBlank(type)){
            this.type = type;
        } else {
            throw new IllegalArgumentException("type cannot be null or blank");
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        Objects.requireNonNull(id, "Id cannot be null");
        if (StringUtils.isBlank(getType())){
            throw new IllegalArgumentException("Cannot set id without Type property being defined");

        }else if (StringUtils.isNotBlank(id)){
            this.id = id;

        } else {
            throw new IllegalArgumentException("Id can't be null or blank");
        }
    }

    public void setId(String prefix, String uuid) {
        Objects.requireNonNull(prefix, "prefix cannot be null");
        Objects.requireNonNull(uuid, "Id cannot be null");

        this.setId(String.join("--", getType(), id));
    }

    @JsonIgnore
    public Identity getCreatedByRef() {
        return createdByRef;
    }

    public void setCreatedByRef(Identity createdByRef) {
        this.createdByRef = createdByRef;
    }

    /**
     * Used by JSON serlizer to return proper spec value for created_by_ref property
     * @return
     */
    @JsonProperty("created_by_ref")
    @JsonInclude(NON_NULL)
    public String getCreatedByRefId() {
        Identity identity = this.getCreatedByRef();
        if (identity != null){
            return this.getCreatedByRef().getId();
        } else {
            return null;
        }
    }


    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        Objects.requireNonNull(type, "created date cannot be null");
        this.created = created;
    }

    public ZonedDateTime getModified() {
        return modified;
    }

    public void setModified(ZonedDateTime modified) {
        Objects.requireNonNull(type, "modified date cannot be null");
        this.modified = modified;
    }

    public boolean getRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public LinkedHashSet<String> getLabels() {
        return labels;
    }

    public void setLabels(LinkedHashSet<String> labels) {
        this.labels = labels;
    }

    public LinkedHashSet<ExternalReference> getExternalReferences() {
        return externalReferences;
    }

    public void setExternalReferences(LinkedHashSet<ExternalReference> externalReferences) {
        this.externalReferences = externalReferences;
    }

    @JsonIgnore
    public LinkedHashSet<MarkingDefinition> getObjectMarkingRefs() {
        return this.objectMarkingRefs;
    }

    @JsonProperty("object_marking_refs")
    @JsonInclude(NON_NULL)
    public LinkedHashSet<String> getObjectMarkingRefsAsStrings() {
        if (getObjectMarkingRefs() != null) {
            return this.getObjectMarkingRefs().stream()
                    .map(MarkingDefinition::getId)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        } else {
            return null;
        }
    }

    public void setObjectMarkingRefs(LinkedHashSet<MarkingDefinition> markingDefinitions) {
        this.objectMarkingRefs = markingDefinitions;
    }

    public void setObjectMarkingRefs(MarkingDefinition... objectMarkingRefs) {
        this.setObjectMarkingRefs(new LinkedHashSet<>(Arrays.asList(objectMarkingRefs)));
    }

    public void addObjectMarkingRefs(MarkingDefinition... objectMarkingRefs) {
        if (this.getObjectMarkingRefs() == null){
            this.setObjectMarkingRefs(new LinkedHashSet<>(Arrays.asList(objectMarkingRefs)));
        } else {
            this.getObjectMarkingRefs().addAll(Arrays.asList(objectMarkingRefs));
        }
    }

    public LinkedHashSet<GranularMarking> getGranularMarkings() {
        return this.granularMarkings;
    }

    public void setGranularMarkings(LinkedHashSet<GranularMarking> granularMarkings) {
        this.granularMarkings = granularMarkings;
    }

    public void setGranularMarkings(GranularMarking... objectMarkingRefs) {
        this.setGranularMarkings(new LinkedHashSet<>(Arrays.asList(objectMarkingRefs)));
    }

    public void addGranularMarkings(GranularMarking... granularMarkings) {
        if (this.getGranularMarkings() == null){
            this.setGranularMarkings(new LinkedHashSet<>(Arrays.asList(granularMarkings)));
        } else {
            this.getGranularMarkings().addAll(Arrays.asList(granularMarkings));
        }
    }

    @JsonIgnore
    public HashMap<String, Object> getCustomProperties() {
        return this.customProperties;
    }

    public void setCustomProperties(HashMap<String, Object> customProperties) {
        this.customProperties = customProperties;
    }

    /**
     * Returns a Map with keys that start with "x_".
     * @return
     */
    @JsonAnyGetter
    @JsonInclude(NON_NULL)
    public HashMap<String, Object> getCustomPropertiesForJson() {
        String prefix = "x_";

        HashMap<String, Object> customProperties = this.getCustomProperties();

        // @TODO Add support for proper casing of Keys

        if (customProperties == null){
            return null;
        } else {
            HashMap<String, Object> customPropertiesWithPrefixUpdate = new HashMap<>();
            this.getCustomProperties().forEach((k,v)->{
                if (!k.startsWith(prefix)){
                    customPropertiesWithPrefixUpdate.put(String.join("", prefix, k), v);
                } else {
                    customPropertiesWithPrefixUpdate.put(k, v);
                }
            });
            return customPropertiesWithPrefixUpdate;
        }
    }


    @JsonIgnore
    public LinkedHashSet<BundleObject> getAllCommonPropertiesBundleObjects(){
        LinkedHashSet<BundleObject> bundleObjects = new LinkedHashSet<>();

        bundleObjects.add(getCreatedByRef());

        if (getObjectMarkingRefs() != null) {
            getObjectMarkingRefs().forEach(om -> {
                bundleObjects.add(om);

                if (om.getCreatedByRef() != null){
                    bundleObjects.add(om.getCreatedByRef());
                }

                if (om.getObjectMarkingRefs() != null) {
                    bundleObjects.addAll(om.getObjectMarkingRefs());
                }

                if (om.getGranularMarkings() != null) {
                    if (om.getGranularMarkings() != null) {
                        om.getGranularMarkings().forEach(gm -> {
                            bundleObjects.add(gm.getMarkingRef());
                        });
                    }
                }
            });
        }

        if (getGranularMarkings() != null) {
            getGranularMarkings().forEach(gm -> {
                bundleObjects.add(gm.getMarkingRef());
            });
        }

        return bundleObjects;
    }

    public String toJsonString() throws JsonProcessingException {
        return StixDataFormats.getJsonMapper().writeValueAsString(this);
    }

}