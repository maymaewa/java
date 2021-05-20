package com.oop.repos;
        import com.oop.Group;
        import com.oop.Person;
        import org.springframework.data.repository.CrudRepository;
        import org.springframework.stereotype.Repository;
        import org.springframework.transaction.annotation.Transactional;

        import java.util.List;

@Transactional
@Repository
public interface PersonRepo extends CrudRepository<Person, Integer> {

    List<Person> findByName(String Name);
    List<Person> findByGroup(Group group);
}

