package dao;
import database.DBConnection;
import model.Registration;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class RegistrationDaoImpl implements RegistrationDao {
    @Override
    public Registration findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM registrations WHERE reg_id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
                return null;
            }
        }
    }

    @Override
    public List<Registration> findAll() throws SQLException {
        String sql = "SELECT * FROM registrations";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Registration> l = new ArrayList<>();
            while (rs.next()) l.add(map(rs));
            return l;
        }
    }

    @Override
    public void save(Registration entity) throws SQLException {
        saveRegistration(entity);
    }

    @Override
    public void update(Registration entity) throws SQLException {
        // not needed for now
        throw new UnsupportedOperationException("Update not supported");
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM registrations WHERE reg_id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public boolean exists(int userId, int activityId) throws SQLException {
        String sql = "SELECT COUNT(*) as cnt FROM registrations WHERE user_id = ? AND activity_id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, activityId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("cnt") > 0;
                return false;
            }
        }
    }

    @Override
    public void saveRegistration(Registration r) throws SQLException {
        String sql = "INSERT INTO registrations (user_id, activity_id) VALUES (?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, r.getUserId());
            ps.setInt(2, r.getActivityId());
            ps.executeUpdate();
            try (ResultSet gk = ps.getGeneratedKeys()) {
                if (gk.next()) {
                    // generated id available if needed
                }
            }
        }
    }

    @Override
    public List<Registration> findByActivity(int activityId) throws SQLException {
        String sql = "SELECT * FROM registrations WHERE activity_id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, activityId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Registration> l = new ArrayList<>();
                while (rs.next()) l.add(map(rs));
                return l;
            }
        }
    }

    private Registration map(ResultSet rs) throws SQLException {
        Timestamp ts = rs.getTimestamp("registered_at");
        LocalDateTime dt = ts == null ? null : ts.toLocalDateTime();
        return new Registration(
                rs.getInt("reg_id"),
                rs.getInt("user_id"),
                rs.getInt("activity_id"),
                dt
        );
    }
}
