package io.seata.sample.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jinjunzhu
 */
@Service("storageServiceImpl")
public class StorageServiceImpl implements StorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageServiceImpl.class);

    private Map<String, Statement> statementMap = new ConcurrentHashMap<>(100);
    private Map<String, Connection> connectionMap = new ConcurrentHashMap<>(100);

    @Resource
    private DataSource hikariDataSource;

    /**
     * 扣减库存
     * @param xid 全局xid
     * @param productId 产品id
     * @param count 数量
     * @return
     */
    @Override
    public boolean decrease(String xid, Long productId, Integer count) {
        try {
            LOGGER.info("------->扣减库存prepare开始");
            Connection connection = hikariDataSource.getConnection();
            connection.setAutoCommit(false);
            String sql = "UPDATE storage SET used = used + ?,residue = residue - ? WHERE product_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, count);
            stmt.setInt(2, count);
            stmt.setLong(3, productId);
            statementMap.put(xid, stmt);
            connectionMap.put(xid, connection);
            LOGGER.info("------->扣减库存prepare结束");
        } catch (SQLException e) {
            LOGGER.error("decrease parepare failure:", e);
            return false;
        }
         return true;
    }

    @Override
    public boolean commit(String xid) {
        LOGGER.info("扣减库存金额, commit, xid:{}", xid);
        PreparedStatement statement = (PreparedStatement) statementMap.get(xid);
        Connection connection = connectionMap.get(xid);
        try {
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("扣减库存失败:", e);
            return false;
        }finally {
            try {
                statementMap.remove(xid);
                connectionMap.remove(xid);
                if (null != statement){
                    statement.close();
                }
                if (null != connection){
                    connection.close();
                }
            } catch (SQLException e) {
                LOGGER.error("扣减库存提交事务后关闭连接池失败:", e);
            }
        }
        return true;
    }

    @Override
    public boolean rollback(String xid) {
        LOGGER.info("扣减库存金额, rollback, xid:{}", xid);
        PreparedStatement statement = (PreparedStatement) statementMap.get(xid);
        Connection connection = connectionMap.get(xid);
        try {
            connection.rollback();
        } catch (SQLException e) {
            return false;
        }finally {
            try {
                if (null != statement){
                    statement.close();
                }
                if (null != connection){
                    connection.close();
                }
                statementMap.remove(xid);
                connectionMap.remove(xid);
            } catch (SQLException e) {
                LOGGER.error("扣减库存回滚事务后关闭连接池失败:", e);
            }
        }
        return true;
    }
}
