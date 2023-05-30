package com.tset2411.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.Valid;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="orderProducts")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy") private LocalDate dateCreated;

    private String status;
    
    @Transient
    private String userType;
    
    @OneToMany(mappedBy = "pk.order")
    @Valid
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Transient
    public Double getTotalOrderPrice() {
        double sum = 0D;
        List<OrderProduct> orderProducts = getOrderProducts();
        boolean isGroceries;
        for (OrderProduct op : orderProducts) {
            sum += op.getTotalPrice();
        }
        
        BigDecimal percetageOff = null;
        
        if(UserType.valueOf(userType).equals(UserType.EMPLOYEE)) {
        	percetageOff = new BigDecimal(30);
        } else if(UserType.valueOf(userType).equals(UserType.CUSTOMER)) {
        	percetageOff = new BigDecimal(5);
        } else if(UserType.valueOf(userType).equals(UserType.AFFILIATE)) {
        	percetageOff = new BigDecimal(10);
        }
        
        BigDecimal ab = percetageOff.multiply(new BigDecimal(sum)).divide(new BigDecimal(100));
        sum = sum - ab.longValue();
        return sum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    @Transient
    public int getNumberOfProducts() {
        return this.orderProducts.size();
    }
}
