package oracle.dao;

import oracle.domain.BikeRecord;
import java.util.List;

import net.sf.json.JSONArray;


public interface IBikeRecordDao {
	List<BikeRecord> getAll();
	public JSONArray getSpecificRecord(String date,String stationID);
}
