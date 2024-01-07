package kg.add.springcourse.Project2Boot.services;

import kg.add.springcourse.Project2Boot.models.Book;
import kg.add.springcourse.Project2Boot.models.Person;
import kg.add.springcourse.Project2Boot.repositories.PeopleRepositories;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private PeopleRepositories peopleRepositories;

    @Autowired
    public PeopleService(PeopleRepositories peopleRepositories) {
        this.peopleRepositories = peopleRepositories;
    }

    public List<Person> findAll(){
        return peopleRepositories.findAll();
    }

    public Person findOne(int id){
        Optional<Person> foundPerson = peopleRepositories.findById(id);
        return foundPerson.orElse(null);
    }

   @Transactional
    public void save(Person person){
        peopleRepositories.save(person);
    }

    @Transactional
    public void update(int id, Person updatePerson){
        updatePerson.setId(id);
        peopleRepositories.save(updatePerson);
    }

    @Transactional
    public void delete(int id){
        peopleRepositories.deleteById(id);
    }

    public Optional<Person> getPersonByFullName(String fullname){
        return peopleRepositories.findByFullName(fullname);
    }

    public List<Book> getBooksByPersonId(int id){
        Optional<Person> person = peopleRepositories.findById(id);
        if(person.isPresent()){
            Hibernate.initialize(person.get().getBooks());
            person.get().getBooks().forEach(book->{
                long diffInMillies=Math.abs(book.getTakenAt().getTime()-new Date().getTime());
                if(diffInMillies>864000000)//10суток
                    book.setExpired(true);
            });
            return person.get().getBooks();
        }
        else{
            return Collections.emptyList();
        }
    }

}
