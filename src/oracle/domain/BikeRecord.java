//domain��������ݱ��ʵ��ӳ�䣬����javaBean�淶
package oracle.domain;

public class BikeRecord {
	String leaseDate;				//�賵������
	String leaseTime;				//�����Сʱ
	String leaseStation;			//�����id
	String returnStation;			//�����id
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
