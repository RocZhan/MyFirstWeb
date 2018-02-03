package dao;

import model.Customer;
import model.PageBean;

import java.util.List;

/**
 * 与数据库交互的接口
 * Created by Zp on 2018/1/30.
 */
public interface CustomerDao {

    public void addRecord(Customer customer);

    public void deleteRecord(String id);

    public Customer findRecord(String id);

    public PageBean<Customer> findAllRecord(int pageNum ,int pageRecord);

    public void editRecord(Customer customer);

    public PageBean<Customer> advanceSearch(Customer customer, int pageNum, int pageRecord);
}
