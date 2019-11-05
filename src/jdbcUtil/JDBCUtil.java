//工具类，声明为static，方便外界调用
package jdbcUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCUtil {
	public static String driver = "oracle.jdbc.OracleDriver";
	public static String url = "jdbc:oracle:thin:@localhost:1521:ORCLDB";
	public static String user = "scott";
	public static String password = "tiger";

	static {
		// 加载驱动
		try {
			Class.forName(JDBCUtil.driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConn() {
		try {
			// 连接数据库
			return DriverManager.getConnection(JDBCUtil.url, JDBCUtil.user, JDBCUtil.password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void close(Connection conn, Statement st, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
