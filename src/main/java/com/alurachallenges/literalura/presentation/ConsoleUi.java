package com.alurachallenges.literalura.presentation;

import com.alurachallenges.literalura.businesslogic.exceptions.InvalidLanguageException;
import com.alurachallenges.literalura.businesslogic.exceptions.NotFoundException;
import com.alurachallenges.literalura.businesslogic.exceptions.NullParameterException;
import com.alurachallenges.literalura.businesslogic.services.AuthorService;
import com.alurachallenges.literalura.businesslogic.services.BookService;
import com.alurachallenges.literalura.businesslogic.services.LanguageService;
import com.alurachallenges.literalura.dto.author.GetAuthorDto;
import com.alurachallenges.literalura.dto.book.GetBookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

@Component
public class ConsoleUi {
    private final Scanner scanner = new Scanner(System.in);
    private final BookService bookService;
    private final AuthorService authorService;
    private final LanguageService languageService;

    @Autowired
    public ConsoleUi(BookService bookService, AuthorService authorService, LanguageService languageService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.languageService = languageService;
    }

    public void displayMenu(){
        var option = -1;
        final var exitOption = 0;

        while (option != exitOption) {
            try {
                System.out.println(UiPrompts.MENU);

                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1: {
                        System.out.println(UiPrompts.BOOK_REQUEST);
                        var searchInput = scanner.nextLine();
                        var book = bookService.searchBooks(searchInput);

                        displayBook(book);

                        System.out.println(UiPrompts.CONTINUE_REQUEST);
                        scanner.nextLine();
                    }
                        break;
                    case 2: {
                        var books = bookService.getAllStoredBooks();

                        if (books.isEmpty()) {
                            System.out.println(UiPrompts.NO_RECORDS_FOUND);
                        } else {
                            books.forEach(ConsoleUi::displayBook);
                        }

                        System.out.println(UiPrompts.CONTINUE_REQUEST);
                        scanner.nextLine();
                        break;
                    }
                    case 3: {
                        var authors = authorService.getStoredAuthors();

                        if (authors.isEmpty()) {
                            System.out.println(UiPrompts.NO_RECORDS_FOUND);
                        } else {
                            authors.forEach(ConsoleUi::displayAuthor);
                        }

                        System.out.println(UiPrompts.CONTINUE_REQUEST);
                        scanner.nextLine();
                        break;
                    }
                    case 4: {
                        System.out.println(UiPrompts.LIVING_YEAR_REQUEST);
                        Integer year = scanner.nextInt();
                        scanner.nextLine();

                        var authors = authorService.getLivingAuthors(year);

                        if (authors.isEmpty()) {
                            System.out.println(UiPrompts.NO_LIVING_AUTHORS);
                        } else {
                            authors.forEach(ConsoleUi::displayAuthor);
                        }

                        System.out.println(UiPrompts.CONTINUE_REQUEST);
                        scanner.nextLine();
                        break;
                    }
                    case 5: {
                        var languages = languageService.getAllLanguages();

                        languages.forEach(language -> {
                            var locale = Locale.of(language.languageCode());
                            System.out.println(language.languageCode() + " - " + locale.getDisplayLanguage());
                        });

                        System.out.println(UiPrompts.LANGUAGE_REQUEST);
                        var language = scanner.nextLine();

                        var books = bookService.getBooksByLanguage(language);

                        if (books.isEmpty()) {
                            System.out.println(UiPrompts.NO_RECORDS_FOUND);
                        } else {
                            books.forEach(ConsoleUi::displayBook);
                        }

                        System.out.println(UiPrompts.CONTINUE_REQUEST);
                        scanner.nextLine();
                        break;
                    }
                    case 6: {
                        System.out.println(UiPrompts.AUTHOR_REQUEST);
                        var author = scanner.nextLine();
                        var books = bookService.getBooksByAuthor(author);

                        if (books.isEmpty()) {
                            System.out.println(UiPrompts.NO_RECORDS_FOUND);
                        } else {
                            books.forEach(ConsoleUi::displayBook);
                        }

                        System.out.println(UiPrompts.CONTINUE_REQUEST);
                        scanner.nextLine();
                        break;
                    }
                    case 7: {
                        var books = bookService.getTop10MostDownloadedBooks();

                        if (books.isEmpty()) {
                            System.out.println(UiPrompts.NO_RECORDS_FOUND);
                        } else {
                            books.forEach(ConsoleUi::displayBook);
                        }

                        System.out.println(UiPrompts.CONTINUE_REQUEST);
                        scanner.nextLine();
                        break;
                    }
                    case exitOption:
                        System.out.println(UiPrompts.CLOSING_MESSAGE);
                        break;
                    default:
                        System.out.println(UiPrompts.INVALID_INPUT);
                        System.out.println(UiPrompts.CONTINUE_REQUEST);
                        scanner.nextLine();
                }
            }catch (NullParameterException e){
                System.out.println(e.getMessage());
                System.out.println(UiPrompts.CONTINUE_REQUEST);
                scanner.nextLine();
            } catch (InputMismatchException e){
                System.out.println(UiPrompts.INVALID_INPUT);
                scanner.nextLine();
                System.out.println(UiPrompts.CONTINUE_REQUEST);
                scanner.nextLine();
            }catch (InvalidLanguageException e){
                System.out.println(e.getMessage());
                System.out.println(UiPrompts.CONTINUE_REQUEST);
                scanner.nextLine();
            }catch (NotFoundException e){
                System.out.println(e.getMessage());
                scanner.nextLine();
                System.out.println(UiPrompts.CONTINUE_REQUEST);
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
                scanner.nextLine();
                System.out.println(UiPrompts.CONTINUE_REQUEST);
                scanner.nextLine();            }
        }
    }

    private static void displayBook(GetBookDto book){
        System.out.println(
                "\n----- BOOK -----\n" +
                    "Title: " + book.title() + "\n" +
                    "Authors: " + (book.authors().isEmpty() ? "N/A" : String.join(" | ", book.authors())) + "\n" +
                    "Languages: " + String.join(", ",book.languages()) + "\n" +
                    "Download Count: " + book.downloadCount() + "\n" +
                "-----------------"
        );
    }

    private static void displayAuthor(GetAuthorDto author){
        System.out.println(
                "\n" +
                "Author: " + author.name() + "\n" +
                "Birth year: " + author.birthYear() + "\n" +
                "Death year: " + author.deathYear() + "\n" +
                "Books: " + String.join(", ",author.books())
        );
    }
}
