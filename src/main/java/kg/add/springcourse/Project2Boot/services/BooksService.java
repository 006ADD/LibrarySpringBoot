package kg.add.springcourse.Project2Boot.services;

import kg.add.springcourse.Project2Boot.models.Book;
import kg.add.springcourse.Project2Boot.models.Person;
import kg.add.springcourse.Project2Boot.repositories.BookRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private BookRepositories bookRepositories;

    @Autowired
    public BooksService(BookRepositories bookRepositories) {
        this.bookRepositories = bookRepositories;
    }

    public List<Book> findAll(boolean sortByYear){
        if(sortByYear){
            return bookRepositories.findAll(Sort.by("year"));
        }else{
            return bookRepositories.findAll();
        }
    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear){
        if(sortByYear){
            return bookRepositories.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        }
        else{
            return bookRepositories.findAll(PageRequest.of(page, booksPerPage)).getContent();
        }
    }

    public Book findOne(int id){
        Optional<Book> foundBook = bookRepositories.findById(id);
        return foundBook.orElse(null);
    }

    public List<Book> searchByTitle(String query){
        return bookRepositories.findByTitleStartingWith(query);
    }

    @Transactional
    public void save(Book book){
        bookRepositories.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        Book bookToBeUpdated = bookRepositories.findById(id).get();
        updatedBook.setId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());
        bookRepositories.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        bookRepositories.deleteById(id);
    }

    public Person getBookOwner(int id){
        return bookRepositories.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void release(int id){
        bookRepositories.findById(id).ifPresent(
                book->{
                    book.setOwner(null);
                    book.setTakenAt(null);
                }
        );
    }

    @Transactional
    public void assign(int id, Person selectedPerson){
        bookRepositories.findById(id).ifPresent(
                book->{
                    book.setOwner(selectedPerson);
                    book.setTakenAt(new Date());//текущее время
                }
        );
    }
}
