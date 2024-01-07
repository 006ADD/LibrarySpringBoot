package kg.add.springcourse.Project2Boot.util;

import kg.add.springcourse.Project2Boot.models.Person;
import kg.add.springcourse.Project2Boot.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(peopleService.getPersonByFullName(person.getFullName()).isPresent()){
            errors.rejectValue("fullName", "","Человек с таким ФИО уже существует");
        }
    }
}
