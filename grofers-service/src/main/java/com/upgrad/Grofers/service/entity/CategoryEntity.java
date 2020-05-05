package com.upgrad.Grofers.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "category")
@NamedQueries({
        @NamedQuery(name = "getAllCategoriesOrderedByName", query = "select c from CategoryEntity c order by c.categoryName ASC"),
        @NamedQuery(name = "getCategoryById", query = "select c from CategoryEntity c where " +
                "c.uuid = :uuid"),
        @NamedQuery(name = "getCategoriesByStore", query = "select c from CategoryEntity c inner join c.stores s where s.uuid = :uuid order by c.categoryName")
})
public class CategoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "UUID", length = 64, nullable = false)
    private String uuid;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @ManyToMany
    @JoinTable(name = "category_item", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<ItemEntity> items = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "stores_category", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "store_id"))
    private List<StoreEntity> stores = new ArrayList<>();

    public CategoryEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ItemEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemEntity> items) {
        this.items = items;
    }

    public List<StoreEntity> getStores() {
        return stores;
    }

    public void setStores(List<StoreEntity> stores) {
        this.stores = stores;
    }

    public static Comparator<CategoryEntity> CatNameComparator = new Comparator<CategoryEntity>() {

        public int compare(CategoryEntity c1, CategoryEntity c2) {
            String CatName1 = c1.getCategoryName().toUpperCase();
            String CatName2 = c2.getCategoryName().toUpperCase();

            //ascending order
            return CatName1.compareTo(CatName2);
        }};
}
