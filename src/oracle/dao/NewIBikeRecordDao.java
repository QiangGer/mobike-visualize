package oracle.dao;

import net.sf.json.JSONArray;


public interface NewIBikeRecordDao {
	public JSONArray getRelation(String bdate, String edate, Integer minNum);
	public JSONArray getHeatmap(String bdate, String edate, Integer minNum);
}
