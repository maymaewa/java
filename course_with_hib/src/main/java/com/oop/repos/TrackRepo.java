package com.oop.repos;
import com.oop.Group;
import com.oop.Person;
import com.oop.Track;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface TrackRepo extends CrudRepository<Track, Integer> {

    List<Track> findByName(String Name);
    List<Track> findByGroup(Group group);
}

