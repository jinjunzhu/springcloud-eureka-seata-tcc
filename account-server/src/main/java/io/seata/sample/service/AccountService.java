package io.seata.sample.service;

import java.math.BigDecimal;

/**
 * @author jinjunzhu
 */
public interface AccountService {

    /**
     * 扣减账户余额
     * @param xid the global transactionId
     * @param userId 用户id
     * @param money 金额
     * @return prepare是否成功
     */
    boolean decrease(String xid, Long userId, BigDecimal money);

    /**
     * Commit boolean.
     *
     * @param xid the global transactionId
     * @return the boolean
     */
    boolean commit(String xid);

    /**
     * Rollback boolean.
     *
     * @param xid the global transactionId
     * @return the boolean
     */
    boolean rollback(String xid);
}
