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
@Feature("GET /api/v1/Authors/authors/books/{idBook}")
@Story("Retrieve authors by book ID")
public class AuthorsApiGetByBookIdTest extends AuthorsBaseApiTest {

    @Test(groups = {P0, AUTHORS})
    @TestID("AUTHORS-GBB-001")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyGetByBookIdSuccessfulAndValidateFields() {
        // Setup: idBook = 1 — known existing book resource
        var response = authorsClient.listAuthorsByBookId(1);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNotEmpty();

        // Soft verifications
        var firstAuthor = response.body().get(0);
        softly().assertThat(firstAuthor.id()).isPositive();
        softly().assertThat(firstAuthor.idBook()).isPositive();
        softly().assertThat(firstAuthor.firstName()).isNotNull();
        softly().assertThat(firstAuthor.lastName()).isNotNull();
    }

    @Test(groups = {P1, AUTHORS})
    @TestID("AUTHORS-GBB-003")
    @Severity(SeverityLevel.NORMAL)
    public void verifyGetByBookIdValidNotFound() {
        // Setup: idBook = 999999 — valid integer format, non-existent book resource
        var response = authorsClient.listAuthorsByBookId(999999);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNotNull().isEmpty();
    }

    @Test(groups = {P1, AUTHORS})
    @TestID("AUTHORS-GBB-004")
    @Severity(SeverityLevel.NORMAL)
    public void verifyGetByBookIdInvalidZero() {
        // Setup: idBook = 0 — boundary value, invalid per domain contract
        var response = authorsClient.listAuthorsByBookId(0);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNotNull().isEmpty();
    }

    @Test(groups = {P1, AUTHORS})
    @TestID("AUTHORS-GBB-005")
    @Severity(SeverityLevel.NORMAL)
    public void verifyGetByBookIdInvalidNegative() {
        // Setup: idBook = -1 — negative value, invalid per domain contract
        var response = authorsClient.listAuthorsByBookId(-1);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNotNull().isEmpty();
    }
}
