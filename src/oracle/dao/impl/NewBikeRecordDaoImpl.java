package oracle.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import jdbcUtil.JDBCUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.dao.NewIBikeRecordDao;

public class NewBikeRecordDaoImpl implements NewIBikeRecordDao {

	@Override
	public JSONArray getRelation(String bdate, String edate, Integer minNum) {
		JSONArray Records = new JSONArray();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			//连接数据库
			conn = JDBCUtil.getConn();
			//创建语句
			st = conn.createStatement();
			String sql = "SELECT LEASESTATION,RETURNSTATION,SUM(BIKENUM) FROM NEWBIKERECORD GROUP BY LEASESTATION,RETURNSTATION,LEASEDATE HAVING SUM(BIKENUM) > " + minNum +  
					" AND LEASEDATE > '" + bdate +  "' AND LEASEDATE < '" + edate + "' AND LEASESTATION IN ( SELECT STATIONID FROM NEWSTATIONINFO) AND RETURNSTATION IN ( SELECT STATIONID FROM NEWSTATIONINFO)"
					+ "AND LEASESTATION <> RETURNSTATION";
			System.out.println(sql);
			//执行语句
			rs = st.executeQuery(sql);
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("BIKENUM", rs.getInt("SUM(BIKENUM)"));
				obj.put("LEASESTATION", rs.getString("LEASESTATION"));
				obj.put("RETURNSTATION", rs.getString("RETURNSTATION"));
				Records.add(obj);
			}
			return Records;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(conn, st, rs);
		}
		return null;
	}
	@Override
	public JSONArray getHeatmap(String bdate, String edate, Integer minNum) {
		JSONArray Records = new JSONArray();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			//连接数据库
			conn = JDBCUtil.getConn();
			//创建语句
			st = conn.createStatement();
			String sql = "SELECT LEASESTATION,RETURNSTATION,SUM(BIKENUM) FROM NEWBIKERECORD GROUP BY LEASESTATION,RETURNSTATION,LEASEDATE HAVING SUM(BIKENUM) > " + minNum +  
					" AND LEASEDATE > '" + bdate +  "' AND LEASEDATE < '" + edate + "' AND LEASESTATION IN ( SELECT STATIONID FROM NEWSTATIONINFO) AND RETURNSTATION IN ( SELECT STATIONID FROM NEWSTATIONINFO)"
					+ "AND LEASESTATION = RETURNSTATION";
			System.out.println(sql);
			//执行语句
			rs = st.executeQuery(sql);
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("BIKENUM", rs.getInt("SUM(BIKENUM)"));
				obj.put("STATIONID", rs.getString("LEASESTATION"));
				Records.add(obj);
			}
			return Records;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(conn, st, rs);
		}
		return null;
	}

}
