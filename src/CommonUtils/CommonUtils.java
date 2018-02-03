package CommonUtils;

import dao.CustomerDao;
import dao.CustomerDaoImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Zp on 2018/2/2.
 */
public class CommonUtils {

    public static String generateId() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    public static <T> T toBean(Map map, Class<T> clazz) {
        try {
            T bean = clazz.newInstance();
            ConvertUtils.register(new DateConverter(), Date.class);
            BeanUtils.populate(bean, map);
            return bean;
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }
}
