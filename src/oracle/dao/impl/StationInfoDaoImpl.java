package oracle.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jdbcUtil.JDBCUtil;
import oracle.dao.IStationInfoDao;
import oracle.domain.StationInfo;

public class StationInfoDaoImpl implements IStationInfoDao {

	@Override
	public StationInfo get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StationInfo> getAll() {
		Connection conn = null;
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		try {
			// 连接数据库
			conn = JDBCUtil.getConn();
			// 创建语句
			st = conn.createStatement();
			st2 = conn.createStatement();
			String sql = "SELECT ADDRESS,STATIONID,STATIONNAME FROM STATIONINFO ";
			// 执行语句
			rs = st.executeQuery(sql);
			// 创建一个集合
			List<StationInfo> list = new ArrayList<StationInfo>();
			while (rs.next()) {
				StationInfo info = new StationInfo();
				info.setAddress(rs.getString("ADDRESS"));
				info.setStationID(rs.getString("STATIONID"));
				info.setStationName(rs.getString("STATIONNAME"));

				String sql2 = "SELECT BAIDU_X, BAIDU_Y FROM STATIONORIENT WHERE STATIONID = " + info.getStationID();
				rs2 = st2.executeQuery(sql2);
				rs2.next();
				info.setX(rs2.getString("BAIDU_X"));
				info.setY(rs2.getString("BAIDU_Y"));

				list.add(info);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, st, rs);
			JDBCUtil.close(conn, st2, rs2);
		}
		return null;
	}
	@Override
	public List<StationInfo> newGetAll() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			// 连接数据库
			conn = JDBCUtil.getConn();
			// 创建语句
			st = conn.createStatement();
			String sql = "SELECT * FROM NEWSTATIONINFO ";
			// 执行语句
			rs = st.executeQuery(sql);
			// 创建一个集合
			List<StationInfo> list = new ArrayList<StationInfo>();
			while (rs.next()) {
				StationInfo info = new StationInfo();
				//info.setAddress(rs.getString("ADDRESS"));
				info.setStationID(rs.getString("STATIONID"));
				//info.setStationName(rs.getString("STATIONNAME"));
				info.setX(rs.getString("BAIDU_X"));
				info.setY(rs.getString("BAIDU_Y"));

				list.add(info);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, st, rs);
		}
		return null;
	}
}
