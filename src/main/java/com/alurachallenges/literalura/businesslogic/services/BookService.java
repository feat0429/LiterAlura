package com.alurachallenges.literalura.businesslogic.services;

import com.alurachallenges.literalura.businesslogic.exceptions.NotFoundException;
import com.alurachallenges.literalura.businesslogic.exceptions.InvalidLanguageException;
import com.alurachallenges.literalura.businesslogic.exceptions.NullParameterException;
import com.alurachallenges.literalura.businesslogic.utility.ApiCaller;
import com.alurachallenges.literalura.businesslogic.utility.Mapper;
import com.alurachallenges.literalura.dataaccess.repository.AuthorRepository;
import com.alurachallenges.literalura.dataaccess.repository.BookRepository;
import com.alurachallenges.literalura.dataaccess.repository.LanguageRepository;
import com.alurachallenges.literalura.dto.ApiResponseDto;
import com.alurachallenges.literalura.dto.book.BookApiResponseDto;
import com.alurachallenges.literalura.dto.book.GetBookDto;
import com.alurachallenges.literalura.model.Author;
import com.alurachallenges.literalura.model.Book;
import com.alurachallenges.literalura.model.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookService {

    private final String URL_BASE = "https://gutendex.com/books/";
    private final ApiCaller apiCaller;
    private final Mapper mapper;
    private final BookRepository bookRepository;
    private final LanguageRepository languageRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(ApiCaller apiCaller, Mapper mapper, BookRepository bookRepository, LanguageRepository languageRepository, AuthorRepository authorRepository) {
        this.apiCaller = apiCaller;
        this.mapper = mapper;
        this.bookRepository = bookRepository;
        this.languageRepository = languageRepository;
        this.authorRepository = authorRepository;
    }

    public GetBookDto searchBooks(String searchInput){

        if(searchInput.isBlank()){
            throw new NullParameterException("Input cannot be null. Please provide a value.");
        }

        String url = URL_BASE +"?search=" + searchInput.replace(" ", "+");
        String json = apiCaller.getResponseResults(url);

        ApiResponseDto<BookApiResponseDto> apiResponse = mapper.mapApiResponse(json,BookApiResponseDto.class);

        if(apiResponse.results().isEmpty()){
            throw new NotFoundException("We couldn't find the book. Please try with another title or author.");
        }

        BookApiResponseDto responseBook = mapper.mapApiResponse(json,BookApiResponseDto.class)
                .results()
                .getFirst();

        boolean isBookStored = bookRepository.existsByTitle(responseBook.title());

        if(isBookStored){
            var existingBook = bookRepository.findByTitle(responseBook.title());
            return mapper.mapToGetBookDto(existingBook);
        }

        Set<Author> responseAuthors = mapper.mapToAuthors(responseBook.authors());

        var existingAuthors = authorRepository.findByNameIn(
                responseAuthors.stream()
                    .map(Author::getName)
                    .collect(Collectors.toSet())
        );

        var newAuthors = existingAuthors.isEmpty()
                ? responseAuthors
                : responseAuthors.stream()
                    .filter(responseAuthor ->
                            existingAuthors.stream()
                            .anyMatch(existingAuthor -> !existingAuthor.getName().equals(responseAuthor.getName()))
                    )
                    .collect(Collectors.toSet());

        if(!newAuthors.isEmpty()){
           authorRepository.saveAll(newAuthors);
        }

        var responseLanguages = mapper.mapToLanguages(responseBook.languages());
        var existingLanguages = languageRepository.findByNameIn(
                responseLanguages.stream()
                    .map(Language::getName)
                    .collect(Collectors.toSet())
        );

        Set<Language> newLanguages = existingLanguages.isEmpty()
                ? responseLanguages
                : responseLanguages.stream()
                    .filter(responseLanguage ->
                            existingLanguages.stream()
                                    .anyMatch(existingLanguage -> !existingLanguage.getName().equals(responseLanguage.getName()))
                    )
                    .collect(Collectors.toSet());

        if(!newLanguages.isEmpty()){
            languageRepository.saveAll(newLanguages);
        }

        Book newBook = new Book(
                responseBook.title(),
                responseBook.downloadCount(),
                Stream.concat(newAuthors.stream(), existingAuthors.stream()).collect(Collectors.toSet()),
                Stream.concat(newLanguages.stream(), existingLanguages.stream()).collect(Collectors.toSet())
        );

        bookRepository.save(newBook);

        return mapper.mapToGetBookDto(newBook);
    }

    public List<GetBookDto> getAllStoredBooks(){
        return  bookRepository.findAll().stream()
                .map(mapper::mapToGetBookDto)
                .toList();
    }

    public List<GetBookDto> getBooksByLanguage(String language){
        var isLanguageValid = Arrays.asList(Locale.getISOLanguages()).contains(language);

        if(!isLanguageValid){
            throw new InvalidLanguageException("The language provided is not valid. Please enter a valid language displayed on the list.");
        }

        return bookRepository.findByLanguage(language).stream()
                .map(mapper::mapToGetBookDto)
                .toList();
    }

    public List<GetBookDto> getBooksByAuthor(String authorName){
        return bookRepository.findByAuthor(authorName.toLowerCase()).stream()
                .map(mapper::mapToGetBookDto)
                .toList();
    }

    public List<GetBookDto> getTop10MostDownloadedBooks(){
        return bookRepository.findTop10MostDownloadedBooks().stream()
                .map(mapper::mapToGetBookDto)
                .toList();
    }
}
