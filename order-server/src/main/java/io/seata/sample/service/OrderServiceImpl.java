package io.seata.sample.service;

import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.sample.dao.OrderDao;
import io.seata.sample.entity.Order;
import io.seata.sample.feign.AccountApi;
import io.seata.sample.feign.StorageApi;
import io.seata.spring.annotation.GlobalTransactional;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @author jinjunzhu
 */
@Service("orderServiceImpl")
public class OrderServiceImpl implements OrderService{

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Resource
    private OrderDao orderDao;
    @Resource
    private StorageApi storageApi;
    @Resource
    private OrderApi orderSaveImpl;
    @Resource
    private AccountApi accountApi;


    /**
     * 创建订单
     * @param order
     * @return
     * 测试结果：
     * 1.添加本地事务：仅仅扣减库存
     * 2.不添加本地事务：创建订单，扣减库存
     */
    @Override
    @GlobalTransactional
    public boolean create(Order order) {
        String xid = RootContext.getXID();
        LOGGER.info("------->交易开始");
        BusinessActionContext actionContext = new BusinessActionContext();
        actionContext.setXid(xid);
        boolean result = orderSaveImpl.saveOrder(actionContext, order);
        if(!result){
            throw new RuntimeException("保存订单失败");
        }
        //远程方法 扣减库存
        LOGGER.info("------->扣减库存开始storage中");
        result = storageApi.decrease(actionContext, order.getProductId(), order.getCount());
        if(!result){
            throw new RuntimeException("扣减库存失败");
        }
        LOGGER.info("------->扣减库存结束storage中");
        //远程方法 扣减账户余额
        LOGGER.info("------->扣减账户开始account中");
        result = accountApi.prepare(actionContext, order.getUserId(),order.getPayAmount());
        LOGGER.info("------->扣减账户结束account中" + result);
        LOGGER.info("------->交易结束");
        throw new RuntimeException("调用2阶段提交的rollback方法");
        //return true;
    }

    /**
     * 修改订单状态
     */
    @Override
    public void update(Long userId,BigDecimal payAmount,Integer status) {
        LOGGER.info("修改订单状态，入参为：userId={},payAmount={},status={}",userId,payAmount,status);
        orderDao.update(userId,payAmount,status);
    }
}
