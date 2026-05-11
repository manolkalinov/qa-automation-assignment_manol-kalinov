package com.avenga.bookstore.tests.api.authors;

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
@Feature("DELETE /api/v1/Authors/{id}")
@Story("Delete an author")
public class AuthorsApiDeleteTest extends BaseApiTest {

    @Test(groups = {P0, AUTHORS})
    @TestID("AUTHORS-DEL-001")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyDeleteSuccessful() {
        // Setup: id = 1 — known existing resource
        var response = authorsClient.deleteAuthor(1);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNull();

        // Cleanup: Note: FakeRestAPI is non-persistent — follow-up GET verification omitted intentionally. In a real system this test would verify deletion via GET returning 404.
    }

    @Test(groups = {P1, AUTHORS})
    @TestID("AUTHORS-DEL-002")
    @Severity(SeverityLevel.NORMAL)
    public void verifyDeleteValidNotFound() {
        // Setup: id = 999999 — valid integer format, non-existent resource
        var response = authorsClient.deleteAuthor(999999);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNull();
    }

    @Test(groups = {P1, AUTHORS})
    @TestID("AUTHORS-DEL-003")
    @Severity(SeverityLevel.NORMAL)
    public void verifyDeleteInvalidId() {
        // Setup: two requests — deleteAuthor(0) and deleteAuthor(-1)
        var responseZero = authorsClient.deleteAuthor(0);
        var responseNegative = authorsClient.deleteAuthor(-1);

        // Soft verifications
        softly().assertThat(responseZero.statusCode()).isEqualTo(200);
        softly().assertThat(responseNegative.statusCode()).isEqualTo(200);
    }

    @Test(groups = {P1, AUTHORS})
    @TestID("AUTHORS-DEL-004")
    @Severity(SeverityLevel.NORMAL)
    public void verifyDeleteInvalidMalformed() {
        // Setup: id = 'babayaga' — non-numeric string; uses String overload of deleteAuthor
        var response = authorsClient.deleteAuthor("babayaga");
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(response.body()).isNull();
    }
}
