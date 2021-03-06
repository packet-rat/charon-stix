package io.digitalstate.stix.datamarkings;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.digitalstate.stix.bundle.BundleObject;
import io.digitalstate.stix.datamarkings.markingtypes.MarkingObjectType;
import io.digitalstate.stix.domainobjects.Identity;
import io.digitalstate.stix.domainobjects.types.ExternalReference;
import io.digitalstate.stix.helpers.JsonConvertable;

import java.time.ZonedDateTime;
import java.util.LinkedHashSet;

public interface StixDataMarking extends DataMarkingsAppliable, BundleObject, JsonConvertable {

    String getType();
    void setType(String type);

    String getId();
    void setId(String id);

    Identity getCreatedByRef();
    void setCreatedByRef(Identity createdByRef);

    ZonedDateTime getCreated();
    void setCreated(ZonedDateTime created);

    LinkedHashSet<ExternalReference> getExternalReferences();
    void setExternalReferences(LinkedHashSet<ExternalReference> externalReferences);

    String getDefinitionType();
    void setDefinitionType(String definitionType);

    MarkingObjectType getDefinition();
    void setDefinition(MarkingObjectType definition);

    LinkedHashSet<BundleObject> getAllCommonPropertiesBundleObjects();


}
