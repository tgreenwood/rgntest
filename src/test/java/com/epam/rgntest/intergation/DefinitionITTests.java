package com.epam.rgntest.intergation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.epam.rgntest.DictionaryUtils.generateDefinition;
import static com.epam.rgntest.DictionaryUtils.generateTerm;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DefinitionITTests {

    private static final String DEFINITION_PATH = "/definition/%s";

    @Before
    public void before() {
        deleteAll();
    }

    @Test
    public void shouldRetrieveOneDefinitionWhenRequestingDefinitionGetEndPointWithTerm() {
        Assert.assertEquals(0, getAllDefinitionsListSize());
        String generatedTerm = generateTerm();
        String generatedDef = generateDefinition();
        final String location = createOne(generatedTerm, generatedDef);

        RestAssured.get(location)
                .then()
                .assertThat()
                .body("term", equalTo(generatedTerm))
                .body("definition", equalTo(generatedDef));
        Assert.assertEquals(getAllDefinitionsListSize(), 1);
    }

    @Test
    public void shouldRetrieveAllDefinitionsWhenRequestingAllDefinitionsGetEndPointWithAll() {
        Assert.assertEquals(0, getAllDefinitionsListSize());

        generateAndCreateOne();
        generateAndCreateOne();

        Assert.assertEquals(getAllDefinitionsListSize(), 2);
    }

    @Test
    public void shouldDeleteAllDefinitionsWhenRequestingAllDefinitionsDeleteEndPointWithAll() {
        generateAndCreateOne();
        generateAndCreateOne();
        Assert.assertEquals(getAllDefinitionsListSize(), 2);

        deleteAll();
        Assert.assertEquals(getAllDefinitionsListSize(), 0);
    }

    @Test
    public void shouldDeleteOneDefinitionWhenRequestingDefinitionDeleteEndPointWithTerm() {
        final String location = generateAndCreateOne();
        Assert.assertEquals(1, getAllDefinitionsListSize());

        RestAssured.delete(location)
                .then()
                .assertThat()
                .statusCode(200);

        Assert.assertEquals(0, getAllDefinitionsListSize());
    }

    @Test
    public void shouldUpdateDefinitionWhenRequestingDefinitionPutEndPointWithBody() {
        String initialTerm = generateTerm();
        String initialDef = generateDefinition();
        final String location = createOne(initialTerm, initialDef);

        String updatedDef = generateDefinition();
        final String payload = "{\n" +
                "    \"term\": \"" + initialTerm + "\",\n" +
                "    \"definition\": \"" + updatedDef + "\"\n" +
                "}";
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .put(String.format(DEFINITION_PATH, ""));

        String definition = extractByTermValueEqualsTo(location, "definition");
        Assert.assertEquals(definition, updatedDef);
    }

    @Test
    public void shouldCreateDefinitionWhenRequestingDefinitionPostEndPointWithBody() {
        Assert.assertEquals(0, getAllDefinitionsListSize());

        String generatedTerm = generateTerm();
        String generatedDef = generateDefinition();
        final String location = createOne(generatedTerm, generatedDef);

        Assert.assertEquals(extractByTermValueEqualsTo(location, "term"), generatedTerm);
        Assert.assertEquals(extractByTermValueEqualsTo(location, "definition"), generatedDef);
    }

    @Test
    public void shouldGetInternalServerErrorWhenRequestingGetByNonExistingTerm() {
        RestAssured.get(String.format(DEFINITION_PATH, Integer.MAX_VALUE))
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void shouldResponse200WhenRequestingDeleteByNonExistingTerm() {
        RestAssured.delete(String.format(DEFINITION_PATH, generateTerm()))
                .then()
                .assertThat()
                .statusCode(200);
    }


    private int getAllDefinitionsListSize() {
        return RestAssured.get(String.format(DEFINITION_PATH, "all"))
                .then()
                .extract().jsonPath().getList("$")
                .size();
    }

    private String extractByTermValueEqualsTo(String location, String valueName) {
        return RestAssured.get(location)
                .then()
                .extract()
                .path(valueName);
    }

    private void deleteAll() {
        RestAssured.delete(String.format(DEFINITION_PATH, "all"));
    }

    private String createOne(String term, String definition) {
        final String payload = "{\n" +
                "    \"term\": \"" + term + "\",\n" +
                "    \"definition\": \"" + definition + "\"\n" +
                "}";

        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post(String.format(DEFINITION_PATH, ""))
                .getHeader("location");
    }

    private String generateAndCreateOne() {
        return createOne(generateTerm(), generateDefinition());
    }


}
