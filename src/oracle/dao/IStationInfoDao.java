package oracle.dao;

import java.util.List;
import oracle.domain.StationInfo;

public interface IStationInfoDao {
	//��ȡָ����StationInfo
	StationInfo get(int id);
	//��ȡ����StationInfo
	List<StationInfo> getAll();
	List<StationInfo> newGetAll();
	
}
