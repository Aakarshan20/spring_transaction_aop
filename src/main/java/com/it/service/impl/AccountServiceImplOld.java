package com.it.service.impl;

import com.it.dao.IAccountDao;
import com.it.domain.Account;
import com.it.service.IAccountService;
import com.it.utils.TransactionManager;

import java.util.List;

/**
 * 帳戶的業務層實現類
 */

public class AccountServiceImplOld implements IAccountService {


    private IAccountDao accountDao = null;
    private TransactionManager txManager;

    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setTxManager(TransactionManager txManager) {
        this.txManager = txManager;
    }

    public List<Account> findAllAccount() {
        try{
            //1. 開啟事務
            txManager.beginTransaction();
            //2. 執行操作
            List<Account> accounts = accountDao.findAllAccount();
            //3. 提交事務
            txManager.commit();
            //4. 返回結果
            return accounts;
        }catch(Exception e) {
            //5. 回滾操作
            txManager.rollback();
            throw new RuntimeException(e);
        }finally{
            //6. 釋放連接
            txManager.release();
        }

    }

    public Account findAccountById(Integer accountId) {
        try{
            //1. 開啟事務
            txManager.beginTransaction();
            //2. 執行操作
            Account account = accountDao.findAccountById(accountId);
            //3. 提交事務
            txManager.commit();
            //4. 返回結果
            return account;
        }catch(Exception e) {
            //5. 回滾操作
            txManager.rollback();
            throw new RuntimeException(e);
        }finally{
            //6. 釋放連接
            txManager.release();
        }
    }

    public void saveAccount(Account account) {
        try{
            //1. 開啟事務
            txManager.beginTransaction();
            //2. 執行操作
            accountDao.saveAccount(account);
            //3. 提交事務
            txManager.commit();
        }catch(Exception e) {
            //4. 回滾操作
            txManager.rollback();
            throw new RuntimeException(e);
        }finally{
            //5. 釋放連接
            txManager.release();
        }
    }

    public void updateAccount(Account account) {
        try{
            //1. 開啟事務
            txManager.beginTransaction();
            //2. 執行操作
            accountDao.updateAccount(account);
            //3. 提交事務
            txManager.commit();
        }catch(Exception e) {
            //4. 回滾操作
            txManager.rollback();
            throw new RuntimeException(e);
        }finally{
            //5. 釋放連接
            txManager.release();
        }
    }

    public void deleteAccount(Integer accountId) {
        try{
            //1. 開啟事務
            txManager.beginTransaction();
            //2. 執行操作
            accountDao.deleteAccount(accountId);
            //3. 提交事務
            txManager.commit();
        }catch(Exception e) {
            //4. 回滾操作
            txManager.rollback();
            throw new RuntimeException(e);
        }finally{
            //5. 釋放連接
            txManager.release();
        }
    }

    @Override
    public void transfer(String sourceName, String targetName, Float money) {
        try{
            //1. 開啟事務
            txManager.beginTransaction();
            //2. 執行操作
            //2-1.根據名稱查詢轉出帳戶
            Account source = accountDao.findAccountByName(sourceName);
            //2-2.根據名稱查詢轉入帳戶
            Account target = accountDao.findAccountByName(targetName);
            //2-3.轉出帳戶扣錢
            source.setMoney(source.getMoney()-money);
            //2-4.轉入帳戶加錢
            target.setMoney(target.getMoney()+money);
            //2-5.更新轉出帳戶
            accountDao.updateAccount(source);
            int i=1/0;
            //2-6.更新轉入帳戶
            accountDao.updateAccount(target);
            //3. 提交事務
            txManager.commit();
        }catch(Exception e) {
            //4. 回滾操作
            txManager.rollback();
            throw new RuntimeException(e);
        }finally{
            //5. 釋放連接
            txManager.release();
        }

    }
}
