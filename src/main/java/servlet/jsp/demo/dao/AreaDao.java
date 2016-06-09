package servlet.jsp.demo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import servlet.jsp.demo.domain.Area;

public class AreaDao {

	public List<Area> search(String gameId) {
		Connection connection = ConnectionMananger.getConnection();
		try {
			Statement s = connection.createStatement();
			String sql = "select * from areas WHERE gameid = " + gameId;
			ResultSet result = s.executeQuery(sql);
			List<Area> areas = new ArrayList<Area>();
			while (result.next()) {
				Area area = new Area();
				area.setId(result.getInt("id"));
				area.setName(result.getString("area"));
				area.setGameId(result.getInt("gameid"));
				areas.add(area);
			}
			result.close();
			return areas;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception:" + e.getMessage());
			return null;
		} finally {
			ConnectionMananger.closeConnection();
		}
	}
}
