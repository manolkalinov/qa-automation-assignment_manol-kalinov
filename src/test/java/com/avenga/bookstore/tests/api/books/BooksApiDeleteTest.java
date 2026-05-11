package com.avenga.bookstore.tests.api.books;

import com.avenga.bookstore.framework.test.metadata.TestID;
import com.avenga.bookstore.tests.api.BaseApiTest;
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
@Feature("DELETE /api/v1/Books/{id}")
@Story("Delete a book")
public class BooksApiDeleteTest extends BaseApiTest {

    @Test(groups = {P0, BOOKS})
    @TestID("BOOKS-DEL-001")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyDeleteSuccessful() {
        // Setup: id = 1 — known existing resource
        var response = booksClient.deleteBook(1);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNull();

        // Cleanup: Note: FakeRestAPI is non-persistent — follow-up GET verification omitted intentionally. In a real system this test would verify deletion via GET returning 404.
    }

    @Test(groups = {P1, BOOKS})
    @TestID("BOOKS-DEL-002")
    @Severity(SeverityLevel.NORMAL)
    public void verifyDeleteValidNotFound() {
        // Setup: id = 999999 — valid integer format, non-existent resource
        var response = booksClient.deleteBook(999999);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNull();
    }

    @Test(groups = {P1, BOOKS})
    @TestID("BOOKS-DEL-003")
    @Severity(SeverityLevel.NORMAL)
    public void verifyDeleteInvalidId() {
        // Setup: two requests — deleteBook(0) and deleteBook(-1)
        var responseZero = booksClient.deleteBook(0);
        var responseNegative = booksClient.deleteBook(-1);

        // Soft verifications
        softly().assertThat(responseZero.statusCode()).isEqualTo(200);
        softly().assertThat(responseNegative.statusCode()).isEqualTo(200);
    }

    @Test(groups = {P1, BOOKS})
    @TestID("BOOKS-DEL-004")
    @Severity(SeverityLevel.NORMAL)
    public void verifyDeleteInvalidMalformed() {
        // Setup: id = 'babayaga' — non-numeric string; uses String overload of deleteBook
        var response = booksClient.deleteBook("babayaga");
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(response.body()).isNull();
    }
}
