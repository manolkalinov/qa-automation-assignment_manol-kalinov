package com.avenga.bookstore.framework.test.metadata;

public class TestGroups {

    public static class Priority {
        public static final String P0 = "p0";
        public static final String P1 = "p1";
        public static final String P2 = "p2";

        private Priority() {}
    }

    public static class Api {
        public static final String BOOKS = "books";
        public static final String AUTHORS = "authors";

        private Api() {}
    }

    private TestGroups() {}
}
