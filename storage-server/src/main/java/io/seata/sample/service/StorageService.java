package io.seata.sample.service;

/**
 * @author jinjunzhu
 */
public interface StorageService {

    /**
     * 扣减库存
     * @param xid 全局xid
     * @param productId 产品id
     * @param count 数量
     * @return
     */
    boolean decrease(String xid, Long productId, Integer count);

    /**
     * 提交事务
     * @param xid 全局 xid
     * @return
     */
    boolean commit(String xid);

    /**
     * 回滚事务
     * @param xid 全局 xid
     * @return
     */
    boolean rollback(String xid);
}
