package dao;

import JdbcUtils.C3p0Utils;
import model.Customer;
import model.PageBean;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 与数据库交互的实现类
 * Created by Zp on 2018/1/30.
 */
public class CustomerDaoImpl implements CustomerDao {

    private QueryRunner queryRunner = C3p0Utils.getQueryRunner();

    @Override
    public void addRecord(Customer customer) {
        try{
            String sql = "insert into customer values(?,?,?,?,?)";
            Object[] params = {customer.getId(),customer.getName(),customer.getAge(),
                    customer.getGender(),customer.getProduct()};
            queryRunner.update(sql,params);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRecord(String id) {
        try{
            String sql = "delete from customer where id = ?";
            queryRunner.update(sql,id);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Customer findRecord(String id) {
        try{
            String sql = "select * from customer where id = ?";
            return queryRunner.query(sql, new BeanHandler<Customer>(Customer.class),id);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PageBean<Customer> findAllRecord(int pageNum, int pageRecord) {

        PageBean<Customer> pageBean = new PageBean<>();
        pageBean.setPageNum(pageNum);
        pageBean.setPageRecord(pageRecord);

        try{
            //通过sql语句与ScalarHandler来查询表中的总记录数
            String sql = "select count(*) from customer";
            Number number = (Number)queryRunner.query(sql,new ScalarHandler<>());
            pageBean.setTotalRecord(number.intValue());

            //查询customer表，按name列排序
            // limit？，？指从查询结果中查询对应的记录，比如100,10指从第100条记录开始取10条数据
            sql = "select * from customer order by name limit ?,?";
            Object[] params = {(pageNum - 1) * pageRecord,pageRecord};
            List<Customer> beanlist = queryRunner.query(sql,new BeanListHandler<Customer>(Customer.class),params);
            pageBean.setBeanList(beanlist);

            return pageBean;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void editRecord(Customer customer) {
        try{
            String sql = "update customer set name = ?, age = ?," +
                    " gender = ?, product = ? where id = ?";
            Object[] params = {customer.getName(),customer.getAge(),
                    customer.getGender(),customer.getProduct(),customer.getId()};
            queryRunner.update(sql,params);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public PageBean<Customer> advanceSearch(Customer customer, int pageNum, int pageRecord) {
        PageBean<Customer> pageBean = new PageBean<>();
        pageBean.setPageRecord(pageRecord);
        pageBean.setPageNum(pageNum);

        try{
            StringBuilder cntSql = new StringBuilder("select count(*) from customer");
            StringBuilder whereSql = new StringBuilder(" where 1=1 ");
            List<Object> params = new ArrayList<>();

            String name = customer.getName();
            if(name != null && !name.trim().isEmpty()){
                whereSql.append("and name like ?");
                params.add("%" + name + "%");
            }

            String age = customer.getAge();
            if(age != null && !age.trim().isEmpty()){
                whereSql.append(" and age=?");
                params.add(Integer.parseInt(age));
            }


            String gender = customer.getGender();
            if(gender != null && !gender.trim().isEmpty()){
                whereSql.append(" and gender=?");
                params.add(gender);
            }

            String product = customer.getProduct();
            if(product != null && !product.trim().isEmpty()){
                whereSql.append(" and product like ?");
                params.add("%" + product + "%");
            }

            Number number = (Number) queryRunner.query(cntSql.append(whereSql).toString(),
                    new ScalarHandler<>(),params.toArray());

            if(number.intValue() == 0) return null;

            pageBean.setTotalRecord(number.intValue());

            StringBuilder sql=new StringBuilder("select * from customer");
            StringBuilder lmitSql=new StringBuilder(" limit ?,?");

            params.add((pageNum - 1) * pageRecord);
            params.add(pageRecord);

            List<Customer> beanList = queryRunner.query(sql.append(whereSql).append(lmitSql).toString(),
                    new BeanListHandler<Customer>(Customer.class),params.toArray());
            pageBean.setBeanList(beanList);

            return pageBean;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static int getAllPageRecord() throws SQLException{
        QueryRunner queryRunner = C3p0Utils.getQueryRunner();
        String sql = "select count(*) from customer";
        Number number = queryRunner.query(sql, new ScalarHandler<>());
        return number.intValue();
    }
}
