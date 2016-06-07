package servlet.jsp.demo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.jsp.demo.dao.GameDao;
import servlet.jsp.demo.domain.Game;

public class GameController extends HttpServlet {
	
	private static final long serialVersionUID = -4554229475249662155L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String game = request.getParameter("game");
		String pageNum = request.getParameter("pageNum");
		String pageSizeStr = request.getParameter("pageSize");
		int pageSize = Integer.valueOf(pageSizeStr);
		int start = (Integer.valueOf(pageNum) - 1) * pageSize;
		try {
			GameDao dao = new GameDao();
			List<Game> games = dao.search(game, start, pageSize);
			request.setAttribute("games", games);
			request.getRequestDispatcher("/WEB-INF/views/games.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
