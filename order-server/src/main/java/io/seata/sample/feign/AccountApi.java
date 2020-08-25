package io.seata.sample.feign;

import java.math.BigDecimal;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author jinjunzhu
 */
@FeignClient(value = "account-server")
@LocalTCC
public interface AccountApi {

    /**
     * 扣减账户余额
     * @param actionContext save xid
     * @param userId 用户id
     * @param money 金额
     * @return
     */
    @TwoPhaseBusinessAction(name = "accountApi", commitMethod = "commit", rollbackMethod = "rollback")
    @RequestMapping("/account/decrease")
    boolean prepare(@RequestBody BusinessActionContext actionContext, @RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money);

    /**
     * Commit boolean.
     *
     * @param actionContext save xid
     * @return the boolean
     */
    @RequestMapping("/account/commit")
    boolean commit(@RequestBody BusinessActionContext actionContext);

    /**
     * Rollback boolean.
     *
     * @param actionContext save xid
     * @return the boolean
     */
    @RequestMapping("/account/rollback")
    boolean rollback(@RequestBody BusinessActionContext actionContext);
}
