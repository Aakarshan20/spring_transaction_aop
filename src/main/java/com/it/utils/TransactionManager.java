package com.it.utils;

/**
 * 和事務管理相關的工具類 包含了開啟事務 提交事務 回滾事務 與釋放連接
 */
public class TransactionManager {

    private ConnectionUtils connectionUtils;

    public void setConnectionUtils(ConnectionUtils connetcionUtils) {
        this.connectionUtils = connetcionUtils;
    }

    /**
     * 開啟事務
     */
    public void beginTransaction(){
        try{
            connectionUtils.getThreadConnection().setAutoCommit(false);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 提交事務
     */
    public void commit(){
        try{
            connectionUtils.getThreadConnection().commit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 回滾事務
     */
    public void rollback(){
        try{
            connectionUtils.getThreadConnection().rollback();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 釋放連接
     */
    public void release(){
        try{
            connectionUtils.getThreadConnection().close();//不是真的關了 是還回連接池中
            connectionUtils.removeConnection();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
