package dao;

import model.Activity;
import java.sql.SQLException;
import java.util.List;

public interface ActivityDao extends GenericDao<Activity, Integer> {
    // additional activity-specific methods
    List<Activity> findAvailable() throws SQLException;
    boolean decrementCapacityIfAvailable(int activityId) throws SQLException;
    int getRegisteredCount(int activityId) throws SQLException;
}
