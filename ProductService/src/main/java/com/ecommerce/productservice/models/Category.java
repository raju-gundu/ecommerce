package com.ecommerce.productservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel {

//     being already mapped by an attribute called category
//    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
//    private List<Product> products;
    private String name;


//    public String getName() {
//        System.out.println( userName + " has called this method.");
//        System.out.println("nothi");
//    }
}

// categories
// name | base_model_id

// basemodels
// id | created_at | last_updated_at | is_deleted
