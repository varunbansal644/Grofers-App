package com.upgrad.Grofers.service.business;

import com.upgrad.Grofers.service.dao.CategoryDao;
import com.upgrad.Grofers.service.entity.CategoryEntity;
import com.upgrad.Grofers.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryDao categoryDao;


    @Override
    public CategoryEntity getCategoryById(String categoryId) throws CategoryNotFoundException {
        String uuid = null;
        CategoryEntity categoryEntity = categoryDao.getCategoryById(uuid);
        if(uuid == " ") {
            throw new CategoryNotFoundException("CNF-001","Category id field should not be empty");
        } else if(categoryEntity == null) {
            throw new CategoryNotFoundException("CNF-002","No category by this id");
        } else {
            return categoryEntity;
        }
    }

    @Override
    public List<CategoryEntity> getAllCategoriesOrderedByName()  {
        return null;
    }

    @Override
    public List<CategoryEntity> getCategoriesByStores(String storeId)  {

        return null;
    }
}
