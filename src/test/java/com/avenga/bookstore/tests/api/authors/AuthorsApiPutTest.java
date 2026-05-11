package com.avenga.bookstore.tests.api.authors;

import com.avenga.bookstore.framework.model.Author;
import com.avenga.bookstore.framework.test.metadata.TestID;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static com.avenga.bookstore.framework.test.metadata.TestGroups.Api.AUTHORS;
import static com.avenga.bookstore.framework.test.metadata.TestGroups.Priority.P0;
import static com.avenga.bookstore.framework.test.metadata.TestGroups.Priority.P1;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Authors API")
@Feature("PUT /api/v1/Authors/{id}")
@Story("Update an author")
public class AuthorsApiPutTest extends AuthorsBaseApiTest {

    @Test(groups = {P0, AUTHORS})
    @TestID("AUTHORS-PUT-001")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyPutSuccessfulAndValidateFields() {
        // Setup: id = 1, author with all fields populated including updated firstName
        var author = new Author(1, 1, "Updated FirstName", "Doe");
        var response = authorsClient.updateAuthor(1, author);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNotNull();

        // Soft verifications
        softly().assertThat(response.body().id()).isPositive();
        softly().assertThat(response.body().idBook()).isGreaterThanOrEqualTo(0);
        softly().assertThat(response.body().firstName()).isNotNull();
        softly().assertThat(response.body().lastName()).isNotNull();

        // Cleanup: Note: FakeRestAPI is non-persistent — follow-up GET verification omitted intentionally. In a real system this test would verify the update via GET.
    }

    @Test(groups = {P1, AUTHORS})
    @TestID("AUTHORS-PUT-003")
    @Severity(SeverityLevel.NORMAL)
    public void verifyPutValidNotFound() {
        // Setup: id = 999999 — valid integer format, non-existent resource; valid author body
        var author = new Author(999999, 1, "John", "Doe");
        var response = authorsClient.updateAuthor(999999, author);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNotNull();
    }

    @Test(groups = {P1, AUTHORS})
    @TestID("AUTHORS-PUT-004")
    @Severity(SeverityLevel.NORMAL)
    public void verifyPutMissingRequiredFields() {
        // Setup: id = 1, empty Author body — all fields absent
        var author = new Author(0, 0, null, null);
        var response = authorsClient.updateAuthor(1, author);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNotNull();
    }

    @Test(groups = {P1, AUTHORS})
    @TestID("AUTHORS-PUT-005")
    @Severity(SeverityLevel.NORMAL)
    public void verifyPutInvalidId() {
        // Setup: two requests — valid author body with id=0 and id=-1 in path
        var author = new Author(0, 1, "John", "Doe");
        var responseZero = authorsClient.updateAuthor(0, author);
        var responseNegative = authorsClient.updateAuthor(-1, author);

        // Soft verifications
        softly().assertThat(responseZero.statusCode()).isEqualTo(200);
        softly().assertThat(responseNegative.statusCode()).isEqualTo(200);
    }

    @Test(groups = {P1, AUTHORS})
    @TestID("AUTHORS-PUT-006")
    @Severity(SeverityLevel.NORMAL)
    public void verifyPutInvalidMalformed() {
        var response = authorsClient.updateAuthor(1, "{invalid");
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(response.body()).isNull();
    }
}
