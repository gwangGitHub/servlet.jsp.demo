package servlet.jsp.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.jsp.demo.dao.AreaDao;
import servlet.jsp.demo.domain.Area;

import com.alibaba.fastjson.JSON;

public class AreaController extends HttpServlet {
	
	private static final long serialVersionUID = -4554229475249662155L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		String game = request.getParameter("game");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			AreaDao dao = new AreaDao();
			List<Area> areas = dao.search(game);
			result.put("areas", areas);
			result.put("success", true);
			out.println(JSON.toJSONString(result));
		} catch (Exception e) {
			result.put("success", false);
			out.println(JSON.toJSONString(result));
			e.printStackTrace();
		}finally {
			out.flush();
			out.close();
		}
	}

}
