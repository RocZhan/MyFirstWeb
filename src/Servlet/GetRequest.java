package Servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Zp on 2018/2/2.
 */
public class GetRequest extends HttpServletRequestWrapper {
    private HttpServletRequest request;

    public GetRequest(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public String getParameter(String name) {
        String value = request.getParameter(name);
        if(name == null) return null;

        try{
            return new String(value.getBytes("ISO-8859-1"),"UTF-8");
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return super.getParameter(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String,String[]> map = request.getParameterMap();

        if(map == null) return null;

        Iterator iterator = map.keySet().iterator();

        while (iterator.hasNext()){
            String key = (String)iterator.next();
            String[] value = map.get(key);

            for (String str : value) {
                try{
                    str = new String(str.getBytes("ISO-8859-1"),"UTF-8");
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
            }
        }


        return map;
    }
}
