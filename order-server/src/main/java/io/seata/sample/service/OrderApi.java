package io.seata.sample.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
import io.seata.sample.entity.Order;

@LocalTCC
public interface OrderApi {

    @TwoPhaseBusinessAction(name = "orderApi", commitMethod = "commit", rollbackMethod = "rollback")
    boolean saveOrder(BusinessActionContext actionContext, Order order);

    /**
     * 提交事务
     * @param actionContext save xid
     * @return
     */
    boolean commit(BusinessActionContext actionContext);

    /**
     * 回滚事务
     * @param actionContext save xid
     * @return
     */
    boolean rollback(BusinessActionContext actionContext);
}
