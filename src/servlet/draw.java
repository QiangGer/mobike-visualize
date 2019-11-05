package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.sf.json.JSONArray;
import oracle.dao.IBikeRecordDao;
import oracle.dao.impl.BikeRecordDaoImpl;

public class draw implements Servlet{
	
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		
		 String date = req.getParameter("date");
		 String id   = req.getParameter("id");
	     res.setContentType("text/plain;charset=utf-8");
	     req.setCharacterEncoding("utf-8");
	     PrintWriter out = res.getWriter();
	     
		IBikeRecordDao dao = new BikeRecordDaoImpl();
		JSONArray Records = dao.getSpecificRecord(date, id);
		out.println(Records);
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
