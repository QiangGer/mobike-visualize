package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.dao.IStationInfoDao;
import oracle.dao.impl.StationInfoDaoImpl;
import oracle.domain.StationInfo;

public class newMapInit implements Servlet {

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		JSONArray Stations = new JSONArray();

		IStationInfoDao dao = new StationInfoDaoImpl();
		List<StationInfo> StationList = dao.newGetAll();

		for (StationInfo k : StationList) {
			Stations.add(JSONObject.fromObject(k));
		}

		res.setContentType("text/plain;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		PrintWriter out = res.getWriter();
		
		out.print(Stations);

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
