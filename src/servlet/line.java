package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.sf.json.JSONArray;
import oracle.dao.NewIBikeRecordDao;
import oracle.dao.impl.NewBikeRecordDaoImpl;

public class line implements Servlet {

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		String bdate = req.getParameter("beginDate");
		String edate = req.getParameter("endDate");
		Integer minNum = Integer.parseInt(req.getParameter("minNum"));
		
		NewIBikeRecordDao dao = new NewBikeRecordDaoImpl();
		JSONArray Records = dao.getRelation(bdate,edate,minNum);

		res.setContentType("text/plain;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		PrintWriter out = res.getWriter();

		out.print(Records);

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
