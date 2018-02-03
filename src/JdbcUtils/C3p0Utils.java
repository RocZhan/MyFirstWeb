package JdbcUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by Zp on 2018/1/30.
 */
public class C3p0Utils {

    private static DataSource dataSource;

    public static QueryRunner getQueryRunner(){
        dataSource = new ComboPooledDataSource();
        return new QueryRunner(dataSource);
    }

    public static boolean addUdaptDelete(String sql, Object[] arr){
        QueryRunner qr = C3p0Utils.getQueryRunner();
        int count;
        try{
            count = qr.update(sql,arr);
            return count > 0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
