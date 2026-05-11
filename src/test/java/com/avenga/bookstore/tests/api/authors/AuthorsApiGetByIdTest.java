package com.avenga.bookstore.tests.api.authors;

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
@Feature("GET /api/v1/Authors/{id}")
@Story("Retrieve author by ID")
public class AuthorsApiGetByIdTest extends AuthorsBaseApiTest {

    @Test(groups = {P0, AUTHORS})
    @TestID("AUTHORS-GBI-001")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyGetByIdSuccessful() {
        // Setup: id = 1 — known existing resource
        var response = authorsClient.retrieveAuthorById(1);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNotNull();
    }

    @Test(groups = {P1, AUTHORS})
    @TestID("AUTHORS-GBI-002")
    @Severity(SeverityLevel.NORMAL)
    public void verifyGetByIdValidResponseFields() {
        // Setup: id = 1 — known existing resource
        var response = authorsClient.retrieveAuthorById(1);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNotNull();

        // Soft verifications
        softly().assertThat(response.body().id()).isPositive();
        softly().assertThat(response.body().idBook()).isGreaterThanOrEqualTo(0);
        softly().assertThat(response.body().firstName()).isNotNull();
        softly().assertThat(response.body().lastName()).isNotNull();
    }

    @Test(groups = {P1, AUTHORS})
    @TestID("AUTHORS-GBI-003")
    @Severity(SeverityLevel.NORMAL)
    public void verifyGetByIdValidNotFound() {
        // Setup: id = 999999 — valid integer format, non-existent resource
        var response = authorsClient.retrieveAuthorById(999999);
        assertThat(response.statusCode()).isEqualTo(404);
        assertThat(response.body()).isNull();
    }

    @Test(groups = {P1, AUTHORS})
    @TestID("AUTHORS-GBI-004")
    @Severity(SeverityLevel.NORMAL)
    public void verifyGetByIdInvalidZero() {
        // Setup: id = 0 — boundary value, invalid per domain contract
        var response = authorsClient.retrieveAuthorById(0);
        assertThat(response.statusCode()).isEqualTo(404);
        assertThat(response.body()).isNull();
    }

    @Test(groups = {P1, AUTHORS})
    @TestID("AUTHORS-GBI-005")
    @Severity(SeverityLevel.NORMAL)
    public void verifyGetByIdInvalidNegative() {
        // Setup: id = -1 — negative value, invalid per domain contract
        var response = authorsClient.retrieveAuthorById(-1);
        assertThat(response.statusCode()).isEqualTo(404);
        assertThat(response.body()).isNull();
    }

    @Test(groups = {P1, AUTHORS})
    @TestID("AUTHORS-GBI-006")
    @Severity(SeverityLevel.NORMAL)
    public void verifyGetByIdInvalidMalformed() {
        // Setup: id = 'babayaga' — non-numeric string in integer path parameter; uses String overload of retrieveAuthorById
        var response = authorsClient.retrieveAuthorById("babayaga");
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(response.body()).isNull();
    }
}
