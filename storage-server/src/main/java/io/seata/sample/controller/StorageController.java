package io.seata.sample.controller;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.sample.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author jinjunzhu
 */
@RestController
@RequestMapping("storage")
public class StorageController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private StorageService storageServiceImpl;

    /**
     * 扣减库存
     * @param actionContext save xid
     * @param productId 产品id
     * @param count 数量
     * @return
     */
    @RequestMapping("decrease")
    public boolean decrease(@RequestBody BusinessActionContext actionContext, @RequestParam("productId") Long productId, @RequestParam("count") Integer count){
        return storageServiceImpl.decrease(actionContext.getXid(), productId, count);
    }

    @RequestMapping("commit")
    public boolean commit(@RequestBody BusinessActionContext actionContext){
        try {
            return storageServiceImpl.commit(actionContext.getXid());
        }catch (IllegalStateException e){
            logger.error("commit error:", e);
            return true;
        }
    }

    @RequestMapping("rollback")
    public boolean rollback(@RequestBody BusinessActionContext actionContext){
        try {
            return storageServiceImpl.rollback(actionContext.getXid());
        }catch (IllegalStateException e){
            logger.error("rollback error:", e);
            return true;
        }
    }
}
