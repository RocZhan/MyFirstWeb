package service;

import dao.CustomerDao;
import dao.CustomerDaoImpl;
import model.Customer;
import model.PageBean;

/**
 * service 接口类，使用组合的方式，通过CustomerDaoImpl实现与数据库交互
 * Created by Zp on 2018/2/1.
 */
public interface CustomerService {
    CustomerDao customerDao = new CustomerDaoImpl();

    public void addRecord(Customer customer);

    public void deleteRecord(String id);

    public Customer findRecord(String id);

    public PageBean<Customer> findAllRecord(int pageNum ,int pageRecord);

    public void editRecord(Customer customer);

    public PageBean<Customer> advanceSearch(Customer customer, int pageNum , int pageRecord);
}
