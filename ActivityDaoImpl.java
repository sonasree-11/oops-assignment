package dao;
import database.DBConnection;
import model.Activity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ActivityDaoImpl implements ActivityDao {
    @Override
    public Activity findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM activities WHERE activity_id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
                return null;
            }
        }
    }
    @Override
    public List<Activity> findAll() throws SQLException {
        String sql = "SELECT * FROM activities";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Activity> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        }
    }
    @Override
    public void save(Activity entity) throws SQLException {
        String sql = "INSERT INTO activities (name, description, capacity, age_min, age_max) VALUES (?, ?, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getDescription());
            ps.setInt(3, entity.getCapacity());
            ps.setInt(4, entity.getAgeMin());
            ps.setInt(5, entity.getAgeMax());
            ps.executeUpdate();
        }
    }
    @Override
    public void update(Activity entity) throws SQLException {
        String sql = "UPDATE activities SET name=?, description=?, capacity=?, age_min=?, age_max=? WHERE activity_id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getDescription());
            ps.setInt(3, entity.getCapacity());
            ps.setInt(4, entity.getAgeMin());
            ps.setInt(5, entity.getAgeMax());
            ps.setInt(6, entity.getId());
            ps.executeUpdate();
        }
    }
    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM activities WHERE activity_id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Activity> findAvailable() throws SQLException {
        // simplistic: return all activities (client can filter). You may refine to check remaining capacity.
        return findAll();
    }

    private Activity map(ResultSet rs) throws SQLException {
        return new Activity(
                rs.getInt("activity_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getInt("capacity"),
                rs.getInt("age_min"),
                rs.getInt("age_max")
        );
    }

    @Override
    public boolean decrementCapacityIfAvailable(int activityId) throws SQLException {
        // Atomic decrement if capacity > 0
        String sql = "UPDATE activities SET capacity = capacity - 1 WHERE activity_id = ? AND capacity > 0";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, activityId);
            int affected = ps.executeUpdate();
            return affected == 1;
        }
    }

    @Override
    public int getRegisteredCount(int activityId) throws SQLException {
        String sql = "SELECT COUNT(*) as cnt FROM registrations WHERE activity_id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, activityId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("cnt");
                return 0;
            }
        }
    }
}
