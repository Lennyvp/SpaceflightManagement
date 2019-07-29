package com.lenart.spaceflightmanagement.DAO;

import com.lenart.spaceflightmanagement.model.Tourist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TouristDao extends CrudRepository<Tourist, Long> {
    List<Tourist> findAll();

    List<Tourist> findAllByFirstNameLikeOrLastNameLike(String firstName, String lastName);

    Tourist findTouristById(Long id);
}
