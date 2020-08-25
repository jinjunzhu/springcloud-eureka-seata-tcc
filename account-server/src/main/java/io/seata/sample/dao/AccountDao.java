package io.seata.sample.dao;

import java.math.BigDecimal;
import org.apache.ibatis.annotations.Param;

/**
 * @author jinjunzhu
 */
public interface AccountDao {

    /**
     * 扣减账户余额
     * @param userId 用户id
     * @param payAmount 金额
     */
    void decrease(@Param("userId") Long userId, @Param("payAmount") BigDecimal payAmount);
}
