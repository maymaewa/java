package com.oop.repos;
import com.oop.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface GroupRepo extends CrudRepository<Group, Integer> {

    List<Group> findByName(String Name);
}
