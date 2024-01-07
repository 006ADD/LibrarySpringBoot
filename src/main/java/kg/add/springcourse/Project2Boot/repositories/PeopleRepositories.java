package kg.add.springcourse.Project2Boot.repositories;

import kg.add.springcourse.Project2Boot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepositories extends JpaRepository<Person, Integer>{
    Optional<Person> findByFullName(String fullName);
}
