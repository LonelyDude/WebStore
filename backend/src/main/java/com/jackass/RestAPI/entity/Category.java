package com.jackass.RestAPI.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table(name = "category")
@Entity
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "category_id")
    private int id;

    @Getter
    @Setter
    @Column(name = "category_name")
    private String name;

    @Getter
    @Setter
    @OneToMany
    @JoinColumn(name = "category_id")
    private List<SubCategory> subCategories = new ArrayList<>();

}
