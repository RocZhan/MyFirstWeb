package model;

import java.util.List;

/**
 * Created by Zp on 2018/2/1.
 */
public class PageBean<Object> {

    //页码
    private int pageNum;
    //数据库中总的记录数
    private int totalRecord;
    //每页的记录数
    private int pageRecord;
    //当前页相应的记录
    private List<Object> beanList;
    //总的页数
    private int totalPage;
    //记录浏览器端发送过来的url
    private String url;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getPageRecord() {
        return pageRecord;
    }

    public void setPageRecord(int pageRecord) {
        this.pageRecord = pageRecord;
    }

    public List<Object> getBeanList() {
        return beanList;
    }

    public void setBeanList(List<Object> beanList) {
        this.beanList = beanList;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
