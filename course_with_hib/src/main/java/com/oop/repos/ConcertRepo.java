package com.oop.repos;
import com.oop.Group;
import com.oop.Concert;
import com.oop.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface ConcertRepo extends CrudRepository<Concert, Integer> {
    List<Concert> findByGroup(Group group);

}

