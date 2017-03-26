package espace.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM")
public class Item extends BaseEntity {

    private String name;

}
