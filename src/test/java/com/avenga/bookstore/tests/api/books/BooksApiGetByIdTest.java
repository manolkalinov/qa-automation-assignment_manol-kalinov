package com.avenga.bookstore.tests.api.books;

import com.avenga.bookstore.framework.test.metadata.TestID;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static com.avenga.bookstore.framework.test.metadata.TestGroups.Api.BOOKS;
import static com.avenga.bookstore.framework.test.metadata.TestGroups.Priority.P0;
import static com.avenga.bookstore.framework.test.metadata.TestGroups.Priority.P1;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Books API")
@Feature("GET /api/v1/Books/{id}")
@Story("Retrieve book by ID")
public class BooksApiGetByIdTest extends BooksBaseApiTest {

    @Test(groups = {P0, BOOKS})
    @TestID("BOOKS-GBI-001")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyGetByIdSuccessfulAndValidateFields() {
        // Setup: id = 1 — known existing resource
        var response = booksClient.retrieveBookById(1);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNotNull();

        // Soft verifications
        softly().assertThat(response.body().id()).isPositive();
        softly().assertThat(response.body().title()).isNotBlank();
        softly().assertThat(response.body().description()).isNotNull();
        softly().assertThat(response.body().pageCount()).isGreaterThanOrEqualTo(0);
        softly().assertThat(response.body().publishDate()).isNotBlank();
    }

    @Test(groups = {P1, BOOKS})
    @TestID("BOOKS-GBI-003")
    @Severity(SeverityLevel.NORMAL)
    public void verifyGetByIdValidNotFound() {
        // Setup: id = 999999 — valid integer format, non-existent resource
        var response = booksClient.retrieveBookById(999999);
        assertThat(response.statusCode()).isEqualTo(404);
        assertThat(response.body()).isNull();
    }

    @Test(groups = {P1, BOOKS})
    @TestID("BOOKS-GBI-004")
    @Severity(SeverityLevel.NORMAL)
    public void verifyGetByIdInvalidZero() {
        // Setup: id = 0 — boundary value, invalid per domain contract
        var response = booksClient.retrieveBookById(0);
        assertThat(response.statusCode()).isEqualTo(404);
        assertThat(response.body()).isNull();
    }

    @Test(groups = {P1, BOOKS})
    @TestID("BOOKS-GBI-005")
    @Severity(SeverityLevel.NORMAL)
    public void verifyGetByIdInvalidNegative() {
        // Setup: id = -1 — negative value, invalid per domain contract
        var response = booksClient.retrieveBookById(-1);
        assertThat(response.statusCode()).isEqualTo(404);
        assertThat(response.body()).isNull();
    }

    @Test(groups = {P1, BOOKS})
    @TestID("BOOKS-GBI-006")
    @Severity(SeverityLevel.NORMAL)
    public void verifyGetByIdInvalidMalformed() {
        // Setup: id = 'babayaga' — non-numeric string in integer path parameter; uses String overload of retrieveBookById
        var response = booksClient.retrieveBookById("babayaga");
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(response.body()).isNull();
    }
}
