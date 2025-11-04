package dao;

import model.Registration;
import java.sql.SQLException;
import java.util.List;

public interface RegistrationDao extends GenericDao<Registration, Integer> {
    boolean exists(int userId, int activityId) throws SQLException;
    void saveRegistration(Registration r) throws SQLException;
    List<Registration> findByActivity(int activityId) throws SQLException;
}
