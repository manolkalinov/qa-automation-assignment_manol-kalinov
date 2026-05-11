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
@Feature("GET /api/v1/Authors")
@Story("Retrieve all authors")
public class AuthorsApiGetAllTest extends BaseApiTest {

    @Test(groups = {P0, AUTHORS})
    @TestID("AUTHORS-GAL-001")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyGetAllSuccessful() {
        var response = bookstoreClient.listAuthors();
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNotEmpty();
    }

    @Test(groups = {P1, AUTHORS})
    @TestID("AUTHORS-GAL-002")
    @Severity(SeverityLevel.NORMAL)
    public void verifyGetAllResponseFieldsExist() {
        var response = bookstoreClient.listAuthors();
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isNotEmpty();

        // Soft verifications
        var firstAuthor = response.body().get(0);
        softly().assertThat(firstAuthor.id()).isPositive();
        softly().assertThat(firstAuthor.idBook()).isGreaterThanOrEqualTo(0);
        softly().assertThat(firstAuthor.firstName()).isNotNull();
        softly().assertThat(firstAuthor.lastName()).isNotNull();
    }
}
