package io.digitalstate.stix.vocabularies;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.digitalstate.stix.helpers.StixDataFormats;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ToolLabels implements StixVocabulary {

    @JsonProperty("tool_labels_vocabulary")
    private Set<String> terms = new HashSet<>(Arrays.asList(
            "denial-of-service", "exploitation", "information-gathering",
            "network-capture", "credential-exploitation", "remote-access",
            "vulnerability-scanning"));

    //
    // Getters and Setters
    //

    public Set<String> getTerms() {
        return terms;
    }

    public void setTerms(Set<String> terms) {
        Objects.requireNonNull(terms, "terms cannot be null");
        this.terms = terms;
    }

    public boolean vocabularyContains(Set<String> value){
        Objects.requireNonNull(value, "value cannot be null");
        return getTerms().containsAll(value);
    }

    public boolean vocabularyContains(String... value){
        Objects.requireNonNull(value, "value cannot be null");
        return vocabularyContains(new HashSet<>(Arrays.asList(value)));
    }

    public boolean vocabularyContains(String value){
        Objects.requireNonNull(value, "value cannot be null");
        return vocabularyContains(new HashSet<>(Arrays.asList(value)));
    }

    public ToolLabels addTerms(Set<String> additionalTerms){
        Objects.requireNonNull(additionalTerms, "additionalTerms cannot be null");
        getTerms().addAll(additionalTerms);
        return this;
    }

    @Override
    public String toJsonString() throws JsonProcessingException {
        return StixDataFormats.getJsonMapper().writeValueAsString(getTerms());
    }
}
