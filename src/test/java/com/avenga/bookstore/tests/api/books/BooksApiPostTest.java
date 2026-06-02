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
@Feature("POST /api/v1/Books")
@Story("Create a book")
public class BooksApiPostTest extends BooksBaseApiTest {

    @Test(groups = {P0, BOOKS})
    @TestID("BOOKS-PST-001")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyCreateSuccessfulAndValidateFields() {
        // Setup: book with all fields populated — title, description, pageCount > 0, excerpt, publishDate
        var book = new Book(0, "Clean Code", "A Handbook of Agile Software Craftsmanship", 431, "Chapter excerpt", "2024-01-01T00:00:00");
        var response = booksClient.createBook(book);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNotNull();

        // Soft verifications
        softly().assertThat(response.body().id()).isGreaterThanOrEqualTo(0);
        softly().assertThat(response.body().title()).isNotBlank();
        softly().assertThat(response.body().description()).isNotNull();
        softly().assertThat(response.body().pageCount()).isGreaterThanOrEqualTo(0);
        softly().assertThat(response.body().publishDate()).isNotBlank();

        // Cleanup: Note: FakeRestAPI is non-persistent — follow-up GET verification omitted intentionally. In a real system this test would verify the created resource via GET.
    }

    @Test(groups = {P1, BOOKS})
    @TestID("BOOKS-PST-003")
    @Severity(SeverityLevel.NORMAL)
    public void verifyCreateValidIdEchoed() {
        // Setup: book with explicit id = 42
        var book = new Book(42, "Clean Code", "A Handbook of Agile Software Craftsmanship", 431, "Chapter excerpt", "2024-01-01T00:00:00");
        var response = booksClient.createBook(book);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNotNull();

        // Soft verifications
        softly().assertThat(response.body().id()).isEqualTo(42);

        // Cleanup: Note: FakeRestAPI is non-persistent — follow-up GET verification omitted intentionally.
    }

    @Test(groups = {P1, BOOKS})
    @TestID("BOOKS-PST-004")
    @Severity(SeverityLevel.NORMAL)
    public void verifyCreateMissingRequiredFields() {
        // Setup: empty Book body — all fields absent
        var book = new Book(0, null, null, 0, null, null);
        var response = booksClient.createBook(book);
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(response.body()).isNull();
    }

    @Test(groups = {P1, BOOKS})
    @TestID("BOOKS-PST-005")
    @Severity(SeverityLevel.NORMAL)
    public void verifyCreateInvalidPageCount() {
        // Setup: two requests — book with pageCount=0 and book with pageCount=-1
        // TODO: consider parametrized approach in a future refactor
        var bookZero = new Book(0, "Test Title", "Description", 0, "Excerpt", "2024-01-01T00:00:00");
        var bookNegative = new Book(0, "Test Title", "Description", -1, "Excerpt", "2024-01-01T00:00:00");
        var responseZero = booksClient.createBook(bookZero);
        var responseNegative = booksClient.createBook(bookNegative);

        // Soft verifications
        softly().assertThat(responseZero.statusCode()).isEqualTo(200);
        softly().assertThat(responseNegative.statusCode()).isEqualTo(200);
    }

    @Test(groups = {P1, BOOKS})
    @TestID("BOOKS-PST-006")
    @Severity(SeverityLevel.NORMAL)
    public void verifyCreateInvalidMalformed() {
        var response = booksClient.createBook("{invalid");
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(response.body()).isNull();
    }
}
