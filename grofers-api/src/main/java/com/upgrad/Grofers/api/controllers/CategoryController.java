package com.upgrad.Grofers.api.controllers;

import com.upgrad.Grofers.api.CategoriesListResponse;
import com.upgrad.Grofers.api.CategoryDetailsResponse;
import com.upgrad.Grofers.api.CategoryListResponse;
import com.upgrad.Grofers.api.ItemList;
import com.upgrad.Grofers.service.business.CategoryService;
import com.upgrad.Grofers.service.entity.CategoryEntity;
import com.upgrad.Grofers.service.entity.ItemEntity;
import com.upgrad.Grofers.service.entity.StoreEntity;
import com.upgrad.Grofers.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.upgrad.Grofers.api.ItemList.*;

@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/category", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ArrayList> getAllCategories() {
        List<CategoryEntity> categoryEntity = categoryService.getAllCategoriesOrderedByName();
        List<CategoryListResponse> categoryListResponse = new ArrayList<CategoryListResponse>();
        for (int i=0; i < categoryEntity.size(); i++) {
            categoryListResponse.add(new CategoryListResponse()
                    .id(UUID.fromString(categoryEntity.get(i).getUuid()))
                    .categoryName(categoryEntity.get(i).getCategoryName())
            );
        }
        return new ResponseEntity<ArrayList>((ArrayList)categoryListResponse, HttpStatus.OK);
    }
    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/category/{category_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public <ItemTypeEnum> ResponseEntity<CategoryDetailsResponse> getCategoryById(@PathVariable final String category_id) throws CategoryNotFoundException {
        CategoryEntity categoryEntity = categoryService.getCategoryById(category_id);
        List<StoreEntity> itemEntities = categoryEntity.getStores();
        List<ItemList> itemLists = new ArrayList<ItemList>();
        for(int i = 0; i < itemEntities.size(); i++) {
            ItemList itemList = new ItemList();
            itemList.setId(UUID.fromString(itemEntities.get(i).getUuid()));
            itemList.setItemName(itemEntities.get(i).getStoreName();
            itemList.setPrice(itemEntities.get(i).getItems();

            ItemList.ItemTypeEnum itemTypeEnum = ItemTypeEnum.values()[Integer.parseInt(itemEntities.get(i).getUuid()];
            itemList.setItemType(itemTypeEnum);
            itemLists.add(itemList);
        }
        CategoryDetailsResponse categoryDetailsResponse = new CategoryDetailsResponse()
                .id(UUID.fromString(categoryEntity.getUuid()))
                .categoryName(categoryEntity.getCategoryName())
                .itemList(itemLists);
        return new ResponseEntity<CategoryDetailsResponse>(categoryDetailsResponse, HttpStatus.OK);
    }
}
