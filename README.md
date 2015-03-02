# jackson-databind-java-optional
[![Build Status](https://travis-ci.org/zapodot/jackson-databind-java-optional.svg)](https://travis-ci.org/zapodot/jackson-databind-java-optional)
[![Coverage Status](https://img.shields.io/coveralls/zapodot/jackson-databind-java-optional.svg)](https://coveralls.io/r/zapodot/jackson-databind-java-optional?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.zapodot/jackson-databind-java-optional/badge.svg)](http://mvnrepository.com/artifact/org.zapodot/jackson-databind-java-optional)

A shim library to support mapping Java 8 Optional through Jackson. Forked from @realjenuis jackson-databind-java8 project.

This library is compiled with Java 8 and will thus only be useful in a Java 8 (or higher) runtime environment.

## Usage
The library is distributed through Sonatype's OSS repo
### Maven dependency
```xml
        <dependency>
            <groupId>org.zapodot</groupId>
            <artifactId>jackson-databind-java-optional</artifactId>
            <version>2.5.0</version>
        </dependency>
```

### SBT
```scala
    libraryDependencies += "org.zapodot" % "jackson-databind-java-optional" % "2.5.0"
```


### Registering module
The module is auto-discoverable using the Jackson ObjectMappers's findAndRegisterModules method

```java
final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
```

If you are not to crazy about using auto discovery, you can always register the module manually
```java
final ObjectMapper objectMapper = new ObjectMapper()
                                          .registerModule(
                                                new JavaOptionalModule());
```

### Serialization
Empty Optionals will be serialized as JSON nulls.
Example:
```java
    public class Bean {

        public static final String PRESENT_VALUE = "present";
        @JsonProperty
        private Optional<String> empty = Optional.empty();

        @JsonProperty
        private Optional<String> notSet;

        @JsonProperty
        private Optional<String> present = Optional.of(PRESENT_VALUE);

        public static void serialize() {
            final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
            final String json = mapper.writeValueAsString(new Bean());
            System.out.println(json); // will print '{"empty":null,"notSet":null,"present":"present"}'
        }


    }
```

### Deserialization
Nulls will be deserialized as _Optional.empty()_
Example:
```java
    public class JavaOptionalDeserializeTest {

        public static class Bean {

            public static final String PRESENT_VALUE = "present";

            @JsonProperty
            private Optional<String> empty = Optional.empty();

            @JsonProperty
            private Optional<String> notSet;

            @JsonProperty
            private Optional<String> present = Optional.of(PRESENT_VALUE);

        }

        @Test
        public void testDeserialize() throws Exception {
            final Bean bean = new ObjectMapper().findAndRegisterModules()
                                                .readValue("{\"empty\":null,\"notSet\":null}", Bean.class);
            assertNotNull(bean.empty);
            assertEquals(Optional.empty(), bean.empty);
            assertNotNull(bean.notSet);
            assertEquals(Optional.empty(), bean.notSet);
            assertEquals(Optional.of(Bean.PRESENT_VALUE), bean.present);

        }
    }
```
