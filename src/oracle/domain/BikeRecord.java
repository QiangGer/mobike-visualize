//domain类就是数据表的实例映射，符合javaBean规范
package oracle.domain;

public class BikeRecord {
	String leaseDate;				//借车的日期
	String leaseTime;				//具体的小时
	String leaseStation;			//借出的id
	String returnStation;			//还入的id
	int	   bikeNum;
	public String getLeaseDate() {
		return leaseDate;
	}
	public void setLeaseDate(String leaseDate) {
		this.leaseDate = leaseDate;
	}
	public String getLeaseTime() {
		return leaseTime;
	}
	public void setLeaseTime(String leaseTime) {
		this.leaseTime = leaseTime;
	}
	public String getLeaseStation() {
		return leaseStation;
	}
	public void setLeaseStation(String leaseStation) {
		this.leaseStation = leaseStation;
	}
	public String getReturnStation() {
		return returnStation;
	}
	public void setReturnStation(String returnStation) {
		this.returnStation = returnStation;
	}
	public int getBikeNum() {
		return bikeNum;
	}
	public void setBikeNum(int bikeNum) {
		this.bikeNum = bikeNum;
	}
	@Override
	public String toString() {
		return "BikeRecord [leaseStation=" + leaseStation + ", returnStation=" + returnStation + ", bikeNum=" + bikeNum + ", leaseDate="
				+ leaseDate + ", leaseTime=" + leaseTime + "]";
	}
	
}
