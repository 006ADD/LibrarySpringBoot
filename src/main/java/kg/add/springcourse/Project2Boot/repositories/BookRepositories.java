package kg.add.springcourse.Project2Boot.repositories;

import kg.add.springcourse.Project2Boot.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepositories extends JpaRepository<Book,Integer> {
    List<Book> findByTitleStartingWith(String title);
}
