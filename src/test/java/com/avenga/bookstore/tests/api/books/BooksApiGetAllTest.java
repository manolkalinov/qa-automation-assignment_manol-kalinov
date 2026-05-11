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
@Feature("GET /api/v1/Books")
@Story("Retrieve all books")
public class BooksApiGetAllTest extends BaseApiTest {

    @Test(groups = {P0, BOOKS})
    @TestID("BOOKS-GAL-001")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyGetAllSuccessful() {
        var response = bookstoreClient.listBooks();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNotEmpty();
    }

    @Test(groups = {P1, BOOKS})
    @TestID("BOOKS-GAL-002")
    @Severity(SeverityLevel.NORMAL)
    public void verifyGetAllResponseFieldsExist() {
        var response = bookstoreClient.listBooks();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNotEmpty();

        // Soft verifications
        var firstBook = response.body().get(0);
        softly().assertThat(firstBook.id()).isPositive();
        softly().assertThat(firstBook.title()).isNotBlank();
        softly().assertThat(firstBook.description()).isNotNull();
        softly().assertThat(firstBook.pageCount()).isGreaterThanOrEqualTo(0);
        softly().assertThat(firstBook.publishDate()).isNotBlank();
    }
}
