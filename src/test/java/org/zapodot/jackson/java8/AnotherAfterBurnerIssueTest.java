package org.zapodot.jackson.java8;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Tests are based on contribution from @nathan in issue #2.
 *
 * @author zapodot
 */
public class AnotherAfterBurnerIssueTest {

    @Test
    public void testSerializeJavaBeanWithAfterBurnerFirst() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new AfterburnerModule());
        objectMapper.registerModule(new JavaOptionalModule());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        final CustomJsonObject customJsonObject = new CustomJsonObject();
        String jsonText = objectMapper.writeValueAsString(customJsonObject);

        JsonNode json = objectMapper.readTree(jsonText);

        assertEquals(CustomJsonObject.INT_VALUE, json.get("integer").asInt());

        assertFalse(json.has("optionalEmpty"));
    }

    @Test
    public void testSerializeJavaBeanWithAfterBurnerLast() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaOptionalModule());
        objectMapper.registerModule(new AfterburnerModule());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        final CustomJsonObject customJsonObject = new CustomJsonObject();
        String jsonText = objectMapper.writeValueAsString(customJsonObject);

        JsonNode json = objectMapper.readTree(jsonText);
        assertEquals(CustomJsonObject.INT_VALUE, json.get("integer").asInt());
        assertFalse(json.has("optionalEmpty"));
    }

    @Test
    public void testSerializeAnnotatedBeanWithAfterBurnerFirst() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new AfterburnerModule());
        objectMapper.registerModule(new JavaOptionalModule());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        final AnotherBean customJsonObject = new AnotherBean();
        String jsonText = objectMapper.writeValueAsString(customJsonObject);

        JsonNode json = objectMapper.readTree(jsonText);

        assertEquals(CustomJsonObject.INT_VALUE, json.get("integer").asInt());
        assertFalse(json.has("optionalEmpty"));
    }


    public static class CustomJsonObject {

        public static final int INT_VALUE = 5;

        public CustomJsonObject() {
        }

        private int integer = INT_VALUE;
        private String nullString = null;
        private Optional<String> optionalEmpty = Optional.empty();
        private Optional<String> optionalString = Optional.of("optional string");

        public int getInteger() {
            return integer;
        }

        public String getNullString() {
            return nullString;
        }

        public Optional<String> getOptionalEmpty() {
            return optionalEmpty;
        }

        public Optional<String> getOptionalString() {
            return optionalString;
        }
    }

    public class AnotherBean {

        @JsonProperty
        private int integer = CustomJsonObject.INT_VALUE;

        @JsonProperty
        private String nullString = null;

        @JsonProperty
        private Optional<String> optionalEmpty = Optional.empty();

        @JsonProperty
        private Optional<String> optionalString = Optional.of("optional string");

    }
}
