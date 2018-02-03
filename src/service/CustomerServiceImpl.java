package service;

import dao.CustomerDao;
import dao.CustomerDaoImpl;
import model.Customer;
import model.PageBean;

/**
 * Created by Zp on 2018/2/1.
 */
public class CustomerServiceImpl implements CustomerService{

    CustomerDao customerDao = new CustomerDaoImpl();

    @Override
    public void addRecord(Customer customer) {
        customerDao.addRecord(customer);
    }

    @Override
    public void deleteRecord(String id) {
        customerDao.deleteRecord(id);
    }

    @Override
    public Customer findRecord(String id) {
        return customerDao.findRecord(id);
    }

    @Override
    public PageBean<Customer> findAllRecord(int pageNum, int pageRecord) {
        return customerDao.findAllRecord(pageNum,pageRecord);
    }

    @Override
    public void editRecord(Customer customer) {
        customerDao.editRecord(customer);
    }

    @Override
    public PageBean<Customer> advanceSearch(Customer customer, int pageNum, int pageRecord) {
        return customerDao.advanceSearch(customer,pageNum,pageRecord);
    }
}
