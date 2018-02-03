package Servlet;

import CommonUtils.CommonUtils;
import com.sun.deploy.net.HttpResponse;
import dao.CustomerDao;
import dao.CustomerDaoImpl;
import model.Customer;
import model.PageBean;
import service.CustomerService;
import service.CustomerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.sql.SQLException;

/**
 *  Servlet类
 * 与浏览器进行交互，获得客户端发送过来的指令和数据
 * 使用组合的方式，通过CustomerServiceImpl类来实现对数据库相应的操作
 * Created by Zp on 2018/2/1.
 */
@WebServlet(name = "Servlet")
public class CustomerServlet extends HttpServlet {

    CustomerService customerService = new CustomerServiceImpl();

    /**
     * service方法获得HTTP请求中相应的参数
     * 然后根据参数中method的值，通过反射的方式动态调用相应的方法对参数进行处理
     * 然后根据参数的结果进行请求转发
     * @param req 请求
     * @param resp 响应
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //判断http的请求方式
        //若为GET，则通过GetRequest重新编码
        //若为POST，则通过setCharacterEncoding方法将请求正文重新编码
        if (req.getMethod().equals("get")) {
            if (!(req instanceof GetRequest))
                req = new GetRequest(req);
        } else {
            req.setCharacterEncoding("UTF-8");
        }

        //获得请求参数中method的值
        resp.setContentType("text/html;charset = UTF-8");
        String methodName = req.getParameter("method");
        Method method = null;

        //根据methodName，通过class对象获得相应的方法
        try {
            method = this.getClass().getMethod(methodName,
                    HttpServletRequest.class, HttpServletResponse.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        //通过反射运行方法，并根据结果进行请求转发
        try {
            String result = (String) method.invoke(this, req, resp);
            if (result != null && !result.trim().isEmpty()) {
                req.getRequestDispatcher(result).forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加顾客
     * @param req
     * @param resp
     * @return
     * @throws SQLException
     */
    public String doAdd(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException{

        Customer customer = CommonUtils.toBean(req.getParameterMap(),Customer.class);
        customer.setId(CommonUtils.generateId());

        customerService.addRecord(customer);
        req.setAttribute("msg","恭喜，添加顾客 " + customer.getName() +" 成功！");
        return "/msg.jsp";
    }

    /**
     * 删除顾客
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException{
        String id = req.getParameter("id");
        if(id != null && !id.trim().isEmpty()){
            req.setAttribute("msg", "删除顾客 " + req.getParameter("name") + " 成功！");
            customerService.deleteRecord(id);
            req.getRequestDispatcher("/msg.jsp").forward(req,resp);
        }
        else{
            req.setAttribute("msg", "删除顾客失败！");
            req.getRequestDispatcher("/msg.jsp").forward(req,resp);
        }
    }

    /**
     * 查找全部顾客
     * @param req
     * @param resp
     * @return
     */
    public String doFindAll(HttpServletRequest req, HttpServletResponse resp){
        int pageNum = 0;
        int pageRecord = 10;

        String value = req.getParameter("pageNum");
        if(value == null || value.trim().isEmpty())
            pageNum = 1;

        PageBean<Customer> pageBean = customerService.findAllRecord(pageNum,pageRecord);
        pageBean.setUrl(getUrl(req));
        req.setAttribute("pageBean",pageBean);
        return "/list.jsp";
    }

    /**
     * 根据浏览器端发送过来的id，从数据库中找到相应的顾客
     * 然后将顾客的信息转发给edit.jsp
     * @param req
     * @param resp
     * @return
     */
    public String doPreEdit(HttpServletRequest req, HttpServletResponse resp){
        String id = req.getParameter("id");
        Customer customer = customerService.findRecord(id);
        req.setAttribute("customer",customer);
        return "/edit.jsp";
    }

    /**
     * 编辑用户的信息
     * @param req
     * @param resp
     * @return
     */
    public String doEdit(HttpServletRequest req, HttpServletResponse resp){
        Customer customer = CommonUtils.toBean(req.getParameterMap(), Customer.class);
        customerService.editRecord(customer);
        req.setAttribute("msg","编辑用户 " + customer.getName() + " 成功！");
        return "/msg.jsp";
    }

    /**
     * 高级搜索，根据浏览器端输入的数据进行模糊查询
     * @param req
     * @param resp
     * @return
     * @throws UnsupportedEncodingException
     * @throws SQLException
     */
    public String doAdvanceSearch(HttpServletRequest req, HttpServletResponse resp)
            throws UnsupportedEncodingException, SQLException{
        int pageNum = 0;
        int pageRecord = 10;

        String value = req.getParameter("pageNum");
        if(value == null || value.trim().isEmpty())
            pageNum = 1;

        Customer customer = CommonUtils.toBean(req.getParameterMap(), Customer.class);
        PageBean<Customer> pageBean = customerService.advanceSearch(customer,pageNum,pageRecord);

        if(pageBean == null){
            req.setAttribute("msg", "无查询记录！");
            return "/msg.jsp";
        }
        pageBean.setUrl(getUrl(req));

        req.setAttribute("pageBean", pageBean);
        return "/list.jsp";
    }

    /**
     * 获得当前的url
     * @param request
     * @return
     */
    private String getUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String queryString = request.getQueryString();

        if(queryString ==null || queryString.isEmpty())
            return contextPath + servletPath;

        if (queryString.contains("&pageNum=")) {
            int index = queryString.lastIndexOf("&pageNum=");
            queryString = queryString.substring(0, index);
        }

        return contextPath + servletPath + "?" + queryString;
    }

}
