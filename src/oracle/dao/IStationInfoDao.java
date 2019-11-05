package oracle.dao;

import java.util.List;
import oracle.domain.StationInfo;

public interface IStationInfoDao {
	//获取指定的StationInfo
	StationInfo get(int id);
	//获取所有StationInfo
	List<StationInfo> getAll();
	List<StationInfo> newGetAll();
	
}
