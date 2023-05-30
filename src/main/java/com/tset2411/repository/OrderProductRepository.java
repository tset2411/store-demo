package com.tset2411.repository;

import com.tset2411.model.OrderProduct;
import com.tset2411.model.OrderProductPK;
import org.springframework.data.repository.CrudRepository;

public interface OrderProductRepository extends CrudRepository<OrderProduct, OrderProductPK> {
}
