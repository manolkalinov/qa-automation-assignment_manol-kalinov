package com.avenga.bookstore.tests.api.authors;

import com.avenga.bookstore.framework.model.Author;
import com.avenga.bookstore.framework.test.metadata.TestID;
import com.avenga.bookstore.tests.api.BaseApiTest;
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
@Feature("POST /api/v1/Authors")
@Story("Create an author")
public class AuthorsApiPostTest extends BaseApiTest {

    @Test(groups = {P0, AUTHORS})
    @TestID("AUTHORS-PST-001")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyCreateSuccessful() {
        // Setup: author with all fields populated — idBook = 1, firstName and lastName non-blank
        var author = new Author(0, 1, "John", "Doe");
        var response = bookstoreClient.createAuthor(author);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNotNull();

        // Cleanup: Note: FakeRestAPI is non-persistent — follow-up GET verification omitted intentionally. In a real system this test would verify the created resource via GET.
    }

    @Test(groups = {P1, AUTHORS})
    @TestID("AUTHORS-PST-002")
    @Severity(SeverityLevel.NORMAL)
    public void verifyCreateValidResponseFieldsExist() {
        // Setup: author with all fields populated
        var author = new Author(0, 1, "John", "Doe");
        var response = bookstoreClient.createAuthor(author);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNotNull();

        // Soft verifications
        softly().assertThat(response.body().id()).isGreaterThanOrEqualTo(0);
        softly().assertThat(response.body().idBook()).isGreaterThanOrEqualTo(0);
        softly().assertThat(response.body().firstName()).isNotNull();
        softly().assertThat(response.body().lastName()).isNotNull();

        // Cleanup: Note: FakeRestAPI is non-persistent — follow-up GET verification omitted intentionally.
    }

    @Test(groups = {P1, AUTHORS})
    @TestID("AUTHORS-PST-003")
    @Severity(SeverityLevel.NORMAL)
    public void verifyCreateValidIdEchoed() {
        // Setup: author with explicit id = 42, idBook = 1
        var author = new Author(42, 1, "John", "Doe");
        var response = bookstoreClient.createAuthor(author);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNotNull();

        // Soft verifications
        softly().assertThat(response.body().id()).isEqualTo(42);

        // Cleanup: Note: FakeRestAPI is non-persistent — follow-up GET verification omitted intentionally.
    }

    @Test(groups = {P1, AUTHORS})
    @TestID("AUTHORS-PST-004")
    @Severity(SeverityLevel.NORMAL)
    public void verifyCreateMissingRequiredFields() {
        // Setup: empty Author body — all fields absent
        var author = new Author(0, 0, null, null);
        var response = bookstoreClient.createAuthor(author);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNotNull();
    }

    @Test(groups = {P1, AUTHORS})
    @TestID("AUTHORS-PST-005")
    @Severity(SeverityLevel.NORMAL)
    public void verifyCreateInvalidMalformed() {
        var response = bookstoreClient.createAuthor("{invalid");
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(response.body()).isNull();
    }
}
