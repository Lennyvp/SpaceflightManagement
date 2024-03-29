package com.lenart.spaceflightmanagement.service.impl;

import com.lenart.spaceflightmanagement.DAO.TouristDao;
import com.lenart.spaceflightmanagement.model.Tourist;
import com.lenart.spaceflightmanagement.service.TouristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristServiceImpl implements TouristService {

    private TouristDao touristDao;

    @Autowired
    public TouristServiceImpl(TouristDao touristDao) {
        this.touristDao = touristDao;
    }

    @Override
    public Tourist findTouristById(Long id) {
        return touristDao.findTouristById(id);
    }

    @Override
    public List<Tourist> findAllTourists() {
        return touristDao.findAll();
    }

    @Override
    public List<Tourist> findAllTouristsByName(String name) {
        return touristDao.findAllByFirstNameLikeOrLastNameLike(name, name);
    }

    @Override
    public void save(Tourist tourist) {
        touristDao.save(tourist);
    }

    @Override
    public void delete(Tourist tourist) {
        touristDao.delete(tourist);
    }
}
