package oracle.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import jdbcUtil.JDBCUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

import oracle.dao.IBikeRecordDao;
import oracle.domain.BikeRecord;


public class BikeRecordDaoImpl implements IBikeRecordDao{

	@Override
	public List<BikeRecord> getAll() {

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			//连接数据库
			conn = JDBCUtil.getConn();
			//创建语句
			st = conn.createStatement();
			String sql = "SELECT * FROM BIKERECORD";
			//执行语句
			rs = st.executeQuery(sql);
			//创建一个集合
			List<BikeRecord> Records = new ArrayList<BikeRecord>();
			while(rs.next()) {
				BikeRecord record = new BikeRecord();
				record.setBikeNum(rs.getInt("BIKENUM"));
				record.setLeaseDate(rs.getString("LEASEDATE"));
				record.setLeaseStation(rs.getString("LEASESTATION"));
				record.setLeaseTime(rs.getString("LEASETIME"));
				record.setReturnStation(rs.getString("RETURNSTATION"));
				
				Records.add(record);
			}
			return Records;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(conn, st, rs);
		}
			
		return null;
	}
	
	public JSONArray getSpecificRecord(String date,String stationID) {
		JSONArray Records = new JSONArray();
		List<BikeRecord> RecordsList = this.getAll();

		for(BikeRecord k : RecordsList) {
			if(!k.getLeaseDate().equals(date) && (k.getLeaseStation().equals(stationID) || k.getReturnStation().equals(stationID) ) ) {
				RecordsList.remove(k);			
			}
		}
		for(int i = 1;i<24;i++) {
			int leaseNum = 0;
			int returnNum = 0;
			String time = i<10 ? "0" + i + "" : i + "" ;
			for(BikeRecord k : RecordsList) {
				if(k.getLeaseTime().equals(time) ) {
					if(k.getLeaseStation().equals(stationID)) {
						leaseNum += k.getBikeNum();
					}
					if(k.getReturnStation().equals(stationID)) {
						returnNum += k.getBikeNum();
					}
				}
			}
			JSONObject obj = new JSONObject();
			obj.put("StationID", stationID);
			obj.put("leaseDate", date);
			obj.put("leaseTime", time);
			obj.put("leaseNum", leaseNum);
			obj.put("returnNum", returnNum);
			
			Records.add(obj);
		}
		return Records;
	}
}
