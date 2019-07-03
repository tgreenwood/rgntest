package com.epam.rgntest.intergation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


/**
 * I have to use @FixMethodOrder(MethodSorters.NAME_ASCENDING) and naming convention like
 * <should[A,B,C..]Get..>. I do not like the approach, but it looks not that ugly as others I can find.
 * The matter is I need to perform add, update and delete operations once before all tests, otherwise
 * the tests will affect ech other, "@BeforeClass" does not work here.
 *
 * "@Before" with delete all audit data is not an option as I do not think it's a good
 * idea to have API to delete audit
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DefinitionAuditITTests {

    private static final String DEF_AUDIT_ALL_PATH = "/definition/audit/all";
    private static final String DEF_AUDIT_OPERTION_PATH = "/definition/audit/operation/%s";
    private static final String DEFINITION_PATH = "/definition/%s";

    @Test
    public void shouldAGetAllDefinitionAuditsWhenRequestingAll() {
        Assert.assertEquals(0, getAllDefinitionAuditListSize());

        doAddModifyAndDeleteOperation();

        Assert.assertEquals(3, getAllDefinitionAuditListSize());
    }

    @Test
    public void shouldBGetAddedDefinitionAuditsWhenRequestingByAddedOperation() {
        testOperation("added");
    }

    @Test
    public void shouldCGetUpdatedDefinitionAuditsWhenRequestingByModifiedOperation() {
        testOperation("modified");
    }

    @Test
    public void shouldDGetRemovedDefinitionAuditsWhenRequestingByDeletedOperation() {
        testOperation("deleted");
    }

    @Test
    public void shouldGGetBadRequestErrorWhenRequestingByNonExistingOperation() {
        RestAssured.get(String.format(DEF_AUDIT_OPERTION_PATH, "non-existing-operation"))
                .then()
                .assertThat()
                .statusCode(400);
    }


    private int getAllDefinitionAuditListSize() {
        return RestAssured.get(DEF_AUDIT_ALL_PATH)
                .then()
                .extract().jsonPath().getList("$")
                .size();
    }

    private void doAddModifyAndDeleteOperation() {
        final String term = "term";
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(getDefinitionPayload(term, "def"))
                .post(String.format(DEFINITION_PATH, ""));

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(getDefinitionPayload(term, "defUpdated"))
                .put(String.format(DEFINITION_PATH, ""));

        RestAssured.delete(String.format(DEFINITION_PATH, term));
    }

    private String getDefinitionPayload(String term, String def) {
        return "{\n" +
                "    \"term\": \"" + term + "\",\n" +
                "    \"definition\": \"" + def + "\"\n" +
                "}";
    }

    private void testOperation(String operationType) {
        int size = RestAssured.get(String.format(DEF_AUDIT_OPERTION_PATH, operationType)).then()
                .extract().jsonPath().getList("$")
                .size();
        Assert.assertEquals(1, size);

        Response response = RestAssured.get(String.format(DEF_AUDIT_OPERTION_PATH, operationType));
        List<String> operations = response.path("operation");
        operations.forEach(op -> Assert.assertEquals(operationType, op));
    }
}
