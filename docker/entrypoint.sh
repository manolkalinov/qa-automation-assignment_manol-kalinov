#!/bin/sh
set -e

case "${SUITE}" in
  smoke)
    SUITE_ARGS="-Dsurefire.suiteXmlFiles=src/test/resources/testng-smoke.xml"
    ;;
  books)
    SUITE_ARGS="-Dsurefire.suiteXmlFiles=src/test/resources/testng-books.xml"
    ;;
  authors)
    SUITE_ARGS="-Dsurefire.suiteXmlFiles=src/test/resources/testng-authors.xml"
    ;;
  full|"")
      SUITE_ARGS="-Dsurefire.suiteXmlFiles=src/test/resources/testng-full.xml"
    ;;
    *)
      SUITE_ARGS="-Dsurefire.suiteXmlFiles=src/test/resources/testng-full.xml"
    ;;
esac

exec mvn test $SUITE_ARGS
