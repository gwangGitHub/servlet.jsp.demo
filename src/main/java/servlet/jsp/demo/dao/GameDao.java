package servlet.jsp.demo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import servlet.jsp.demo.domain.Game;

public class GameDao {

	public List<Game> search(String game, int start, int size) {
		Connection connection = ConnectionMananger.getConnection();
		try {
			Statement s = connection.createStatement();
			String sql = "select * from games";
			if (game != null && !"".equals(game.trim())) {
				sql += "WHERE game LIKE '%" + game + "%' order by id desc limit " + start + " , " + size;
			}
			ResultSet result = s.executeQuery(sql);
			List<Game> games = new ArrayList<Game>();
			while (result.next()) {
				Game gamename = new Game();
				gamename.setId(result.getInt("id"));
				gamename.setName(result.getString("game"));
				games.add(gamename);
			}
			result.close();
			return games;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception:" + e.getMessage());
			return null;
		} finally {
			ConnectionMananger.closeConnection();
		}
	}
}
