package io.seata.sample.feign;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author jinjunzhu
 */
@FeignClient(value = "storage-server")
@LocalTCC
public interface StorageApi {

    /**
     * 扣减库存
     * @param productId
     * @param count
     * @returns
     */
    @TwoPhaseBusinessAction(name = "storageApi", commitMethod = "commit", rollbackMethod = "rollback")
    @GetMapping(value = "/storage/decrease")
    boolean decrease(@RequestBody BusinessActionContext actionContext, @RequestParam("productId") Long productId, @RequestParam("count") Integer count);

    /**
     * 提交事务
     * @param actionContext save xid
     * @return
     */
    @GetMapping(value = "/storage/commit")
    boolean commit(@RequestBody BusinessActionContext actionContext);

    /**
     * 回滚事务
     * @param actionContext save xid
     * @return
     */
    @GetMapping(value = "/storage/rollback")
    boolean rollback(@RequestBody BusinessActionContext actionContext);
}
