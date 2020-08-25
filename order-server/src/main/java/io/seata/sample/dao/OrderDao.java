package io.seata.sample.dao;

import io.seata.sample.entity.Order;
import java.math.BigDecimal;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author jinjunzhu
 */
@Repository
public interface OrderDao {

    /**
     * 创建订单
     * @param order
     * @return
     */
    void create(Order order);

    /**
     * 修改订单金额
     * @param userId
     * @param payAmount
     */
    void update(@Param("userId") Long userId,@Param("payAmount") BigDecimal payAmount, @Param("status") Integer status);
}
