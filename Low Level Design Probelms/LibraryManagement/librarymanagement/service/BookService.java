package librarymanagement.service;

import librarymanagement.entities.Author;
import librarymanagement.entities.Book;
import librarymanagement.entities.BookItem;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookService {

    private BookRepository bookRepository;
    private BookItemRepository bookItemRepository;
    private AuthorRepository authorRepository;

    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> searchByAuthor(String authorName) {
        return bookRepository.findByAuthorsNameContainingIgnoreCase(authorName);
    }

    public List<Book> searchBySubject(String subject) {
        return bookRepository.findBySubjectContainingIgnoreCase(subject);
    }

    public List<Book> searchByPublicationDate(LocalDate startDate, LocalDate endDate) {
        return bookRepository.findByPublicationDateBetween(startDate, endDate);
    }


    public Book addBook(Book book, List<String> authorNames) {
        Set<Author> authors = new HashSet<>();
        for (String name : authorNames) {
            Author author = authorRepository.findByName(name)
                    .orElseGet(() -> {
                        Author newAuthor = new Author();
                        newAuthor.setName(name);
                        return authorRepository.save(newAuthor);
                    });
            authors.add(author);
        }
        book.setAuthors(authors);
        return bookRepository.save(book);
    }

    public BookItem addBookItem(String ISBN, String barcode, String rackLocation) {
        Book book = bookRepository.findById(ISBN)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        BookItem bookItem = new BookItem();
        bookItem.setBarcode(barcode);
        bookItem.setRackLocation(rackLocation);
        bookItem.setStatus(BookStatus.AVAILABLE);
        bookItem.setBook(book);

        return bookItemRepository.save(bookItem);
    }

}
