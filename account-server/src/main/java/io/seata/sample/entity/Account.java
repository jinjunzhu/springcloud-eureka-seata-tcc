package io.seata.sample.entity;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

/**
 * @author jinjunzhu
 */
@Data
public class Account {

    private Long id;

    /**用户id*/
    private Long userId;

    /**总额度*/
    private BigDecimal total;

    /**已用额度*/
    private BigDecimal used;

    /**剩余额度*/
    private BigDecimal balance;
    /**更新时间*/
    private Date lastUpdateTime;
}
