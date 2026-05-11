package com.avenga.bookstore.tests.api.books;

import com.avenga.bookstore.framework.model.Book;
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
@Feature("PUT /api/v1/Books/{id}")
@Story("Update a book")
public class BooksApiPutTest extends BooksBaseApiTest {

    @Test(groups = {P0, BOOKS})
    @TestID("BOOKS-PUT-001")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyPutSuccessful() {
        // Setup: id = 1, book with all fields populated including updated title
        var book = new Book(1, "Updated Title", "A Handbook of Agile Software Craftsmanship", 431, "Chapter excerpt", "2024-01-01T00:00:00");
        var response = booksClient.updateBook(1, book);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNotNull();

        // Cleanup: Note: FakeRestAPI is non-persistent — follow-up GET verification omitted intentionally. In a real system this test would verify the update via GET.
    }

    @Test(groups = {P1, BOOKS})
    @TestID("BOOKS-PUT-002")
    @Severity(SeverityLevel.NORMAL)
    public void verifyPutValidResponseFieldsExist() {
        // Setup: id = 1, book with all fields populated
        var book = new Book(1, "Updated Title", "A Handbook of Agile Software Craftsmanship", 431, "Chapter excerpt", "2024-01-01T00:00:00");
        var response = booksClient.updateBook(1, book);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNotNull();

        // Soft verifications
        softly().assertThat(response.body().id()).isPositive();
        softly().assertThat(response.body().title()).isNotBlank();
        softly().assertThat(response.body().description()).isNotNull();
        softly().assertThat(response.body().pageCount()).isGreaterThanOrEqualTo(0);
        softly().assertThat(response.body().publishDate()).isNotBlank();

        // Cleanup: Note: FakeRestAPI is non-persistent — follow-up GET verification omitted intentionally.
    }

    @Test(groups = {P1, BOOKS})
    @TestID("BOOKS-PUT-003")
    @Severity(SeverityLevel.NORMAL)
    public void verifyPutValidNotFound() {
        // Setup: id = 999999 — valid integer format, non-existent resource; valid book body
        var book = new Book(999999, "Updated Title", "A Handbook of Agile Software Craftsmanship", 431, "Chapter excerpt", "2024-01-01T00:00:00");
        var response = booksClient.updateBook(999999, book);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNotNull();
    }

    @Test(groups = {P1, BOOKS})
    @TestID("BOOKS-PUT-004")
    @Severity(SeverityLevel.NORMAL)
    public void verifyPutMissingRequiredFields() {
        // Setup: id = 1, empty Book body — all fields absent
        var book = new Book(0, null, null, 0, null, null);
        var response = booksClient.updateBook(1, book);
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(response.body()).isNull();
    }

    @Test(groups = {P1, BOOKS})
    @TestID("BOOKS-PUT-005")
    @Severity(SeverityLevel.NORMAL)
    public void verifyPutInvalidId() {
        // Setup: two requests — valid book body with id=0 and id=-1 in path
        var book = new Book(0, "Test Title", "Description", 100, "Excerpt", "2024-01-01T00:00:00");
        var responseZero = booksClient.updateBook(0, book);
        var responseNegative = booksClient.updateBook(-1, book);

        // Soft verifications
        softly().assertThat(responseZero.statusCode()).isEqualTo(200);
        softly().assertThat(responseNegative.statusCode()).isEqualTo(200);
    }

    @Test(groups = {P1, BOOKS})
    @TestID("BOOKS-PUT-006")
    @Severity(SeverityLevel.NORMAL)
    public void verifyPutInvalidMalformed() {
        var response = booksClient.updateBook(1, "{invalid");
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(response.body()).isNull();
    }
}
