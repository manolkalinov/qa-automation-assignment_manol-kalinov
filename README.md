# Avenga API Automation Assessment — Online Bookstore

API test automation framework for the [FakeRestAPI](https://fakerestapi.azurewebsites.net) bookstore endpoints, built as part of the Avenga Senior QA Automation Engineer assessment.

---

## Technology Stack

| Layer | Choice                        |
|---|-------------------------------|
| Language | Java 17                       |
| Test framework | TestNG 7.9                    |
| HTTP client | OkHttp 4.12                   |
| Assertions | AssertJ 3.26                  |
| Dependency injection | Google Guice 7.0              |
| Build tool | Maven 3.9                     |
| Reporting | Allure + Custom HTML reporter |
| CI/CD | GitHub Actions                |
| Containerisation | Docker                        |
| Orchestration | Kubernetes (Job)              |

---

## Project Structure

```
src/test/java/com/avenga/bookstore/
├── framework/
│   ├── assertions/      # AssertionContext (hard + soft assertion management)
│   ├── client/          # BookstoreClient, ApiConfig, ApiResponse
│   ├── model/           # Book, Author records
│   ├── reporting/       # CustomHtmlReporter
│   ├── test/
│   │   ├── dependency/  # Guice modules (DI assembly)
│   │   ├── environment/ # Environment loader (dev / stage / test)
│   │   └── metadata/    # TestGroups constants, @TestID annotation
│   └── TestLifecycleManager.java
└── tests/api/
    ├── BaseApiTest.java
    ├── books/           # 5 test classes (GET all, GET by ID, POST, PUT, DELETE)
    └── authors/         # 6 test classes (GET all, GET by ID, GET by book ID, POST, PUT, DELETE)

src/test/resources/
├── test-environments/
│   ├── dev.properties
│   ├── stage.properties
│   └── test.properties
├── testng.xml               # Full suite (all 4 blocks)
├── testng-smoke.xml         # P0 tests only
├── testng-books.xml         # Books group only
├── testng-authors.xml       # Authors group only
└── testng-full.xml          # Full regression (no group filter)

.github/workflows/
└── ci.yml                   # Smoke → Full Regression pipeline

docker/
└── entrypoint.sh            # Suite selector script

k8s/
├── configmap.yaml
└── job.yaml

report/
└── allure-report.html       # Pre-generated Allure report

logs/
└── bookstore-tests.log      # DEBUG-level log from last local test run
```

---

## Prerequisites

- Java 17+
- Maven 3.9+
- Docker (for container runs)
- kubectl (for Kubernetes runs)

---

## Environment Configuration

Environments are configured via properties files in `src/test/resources/test-environments/`.

The active environment is selected by passing `-Dapi.env=<name>` to Maven. Default is `dev`.

```
# dev.properties
bookstore.baseUrl=https://fakerestapi.azurewebsites.net
```

Available environments: `dev`, `stage`, `test`.

---

## Test Data

Test data is currently hardcoded directly in the test classes (book and author IDs,
payloads, boundary values, malformed inputs). This is a known limitation of the current
implementation.

In a production-grade framework, test data would be externalized and supplied to tests
via a dedicated mechanism, keeping test logic decoupled from test data and making the
suite easier to maintain and extend.

---

## Running the Tests

### Run all tests (full regression)
```bash
mvn test
```

### Run a specific suite

> **Windows (Command Prompt):** Wrap `-D` properties in double quotes, e.g.
> 
> `mvn test "-Dsurefire.suiteXmlFiles=src/test/resources/testng-smoke.xml"`

```bash
# Smoke — P0 tests only (11 tests)
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng-smoke.xml

# Books only (24 tests)
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng-books.xml

# Authors only (28 tests)
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng-authors.xml

# Full regression (52 tests)
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng-full.xml
```

### Run against a specific environment
```bash
mvn test -Dapi.env=stage
```

---

## Test Reports

Two reports are generated after each test run.

### Allure Report (primary)

> A pre-generated report is included in the `report/` folder — open `report/allure-report.html` in any browser.

To regenerate from a fresh test run:

```bash
mvn clean test
mvn allure:report
```

Then serve interactively:

```bash
mvn allure:serve
```

Opens an interactive report in the browser with Epic → Feature → Story
hierarchy, severity levels, pass/fail breakdown, timeline, and full
failure details including stack traces.

### Custom HTML Report (secondary)

A lightweight custom report is generated at `target/custom-report/report.html`
after every run. Open it in any browser. Shows test name, class, groups,
status (PASS/FAIL/SKIP), duration, and error message for failures.

This report is implemented via a custom TestNG `IReporter` and runs
automatically alongside Allure.

### CI/CD

The full regression Allure report is uploaded as a workflow artifact
after every run on `master`. Download it from the GitHub Actions run
summary page.
---

## Docker

### Build the image
```bash
docker build -t bookstore-tests .
```

### Run with suite selection

| Command | Suite | Tests |
|---|---|---|
| `docker run bookstore-tests` | Full regression | 52 |
| `docker run -e SUITE=smoke bookstore-tests` | Smoke (P0) | 11 |
| `docker run -e SUITE=books bookstore-tests` | Books | 24 |
| `docker run -e SUITE=authors bookstore-tests` | Authors | 28 |

### Extract the HTML report after a run
```bash
docker run --name bookstore-run bookstore-tests
docker cp bookstore-run:/app/target/custom-report ./report
docker rm bookstore-run
```

---

## Kubernetes

The Kubernetes manifests are in `k8s/`. They run the test suite as a one-shot `Job` — the correct primitive for a test runner, as opposed to a long-running `Deployment`.

Suite and environment selection is handled via `ConfigMap`.

### Configure the run
Edit `k8s/configmap.yaml`:
```yaml
data:
  SUITE: full      # smoke | books | authors | full
  API_ENV: dev     # dev | stage | test
```

### Apply and run
```bash
# Apply the ConfigMap
kubectl apply -f k8s/configmap.yaml

# Run the Job
kubectl apply -f k8s/job.yaml

# Watch progress
kubectl get pods -w

# Extract the report
kubectl cp bookstore-tests-<pod-id>:/app/target/custom-report ./report

# Clean up
kubectl delete -f k8s/job.yaml
```

> **Note:** `imagePullPolicy: IfNotPresent` assumes the image has been built locally and is available to the cluster. For a remote cluster, push the image to a registry and update the `image` field in `job.yaml` accordingly.

---

## CI/CD — GitHub Actions

The pipeline is defined in `.github/workflows/ci.yml` and consists of two jobs:

| Job | Trigger | Suite |
|---|---|---|
| Smoke (P0) | Every push to any branch | `testng-smoke.xml` — 11 tests |
| Full Regression | Push/PR to `master` only, runs after Smoke passes | `testng-full.xml` — 52 tests |

The full regression HTML report is uploaded as a workflow artifact after every run on `master`.

---

## API Coverage

### Books API
| Endpoint | Class | Tests |
|---|---|-------|
| GET /api/v1/Books | BooksApiGetAllTest | 2     |
| GET /api/v1/Books/{id} | BooksApiGetByIdTest | 6     |
| POST /api/v1/Books | BooksApiPostTest | 6     |
| PUT /api/v1/Books/{id} | BooksApiPutTest | 6     |
| DELETE /api/v1/Books/{id} | BooksApiDeleteTest | 4     |

### Authors API
| Endpoint | Class | Tests |
|---|---|---|
| GET /api/v1/Authors | AuthorsApiGetAllTest | 2 |
| GET /api/v1/Authors/{id} | AuthorsApiGetByIdTest | 6 |
| GET /api/v1/Authors/authors/books/{idBook} | AuthorsApiGetByBookIdTest | 5 |
| POST /api/v1/Authors | AuthorsApiPostTest | 5 |
| PUT /api/v1/Authors/{id} | AuthorsApiPutTest | 6 |
| DELETE /api/v1/Authors/{id} | AuthorsApiDeleteTest | 4 |

Each endpoint is covered for happy path, response field validation, not-found / boundary / invalid input, and malformed request cases where applicable.
