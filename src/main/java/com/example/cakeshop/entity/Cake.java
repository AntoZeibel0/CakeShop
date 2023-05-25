package com.example.cakeshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cakes")
@SequenceGenerator(name = "start_cake", sequenceName = "start_cake", initialValue = 5)
public class Cake {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "start_cake")
    private int id;

    @Column(name = "cake_name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @ManyToMany
    @JoinTable(
            name = "order_cake",
            joinColumns = @JoinColumn(name = "cake_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    @JsonIgnore
    private List<Order> orders;

    @OneToOne(mappedBy = "inventoryCake")
    @JsonIgnore
    private Inventory cake;

}
