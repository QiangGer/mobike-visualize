//domain类就是数据表的实例映射，符合javaBean规范

package oracle.domain;

public class StationInfo {
	String stationID;
	String stationName;
	String address;
	String x;
	String y;
	public String getStationID() {
		return stationID;
	}
	public void setStationID(String stationID) {
		this.stationID = stationID;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	@Override
	public String toString() {
		return "StationInfo [stationID=" + stationID + ", stationName=" + stationName + ", address=" + address + ", x="
				+ x + ", y=" + y + "]";
	}
	
	
}
