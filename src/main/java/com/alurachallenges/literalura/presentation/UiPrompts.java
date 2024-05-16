package com.alurachallenges.literalura.presentation;

public final class UiPrompts {
    public static final String BOOK_REQUEST = "Enter book title or author name:";
    public static final String CONTINUE_REQUEST = "\nPress Enter key to continue.";
    public static final String CLOSING_MESSAGE = "Exiting application...\n";
    public static final String NO_RECORDS_FOUND = "No records were found.";
    public static final String INVALID_INPUT = "Invalid input. Please enter a valid value.";
    public static final String LIVING_YEAR_REQUEST = "Enter year to check living authors:";
    public static final String NO_LIVING_AUTHORS = "No authors alive in that year. Try another year";
    public static final String LANGUAGE_REQUEST = "Enter one of the listed language codes: ";
    public static final String AUTHOR_REQUEST = "Enter author name: ";

    public static final String MENU = """
                        
                        ----------------------------
                        1 - Search books
                        2 - Get all stored books
                        3 - Get all stored authors
                        4 - Get stored living authors
                        5 - Get stored books by language
                        6 - Get stored books by author
                        7 - Get top 10 most downloaded stored books
                        
                        0 - Exit
                        ----------------------------
                        """;
    private UiPrompts() {
    }
}
