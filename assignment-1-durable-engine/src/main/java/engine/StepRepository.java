package engine;

import java.sql.*;

public class StepRepository {

    private final Connection connection;

    public StepRepository() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:durable.db");
            initTable();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initTable() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS steps (
                    workflow_id TEXT,
                    step_key TEXT,
                    status TEXT,
                    output TEXT,
                    PRIMARY KEY (workflow_id, step_key)
                )
                """;
        connection.createStatement().execute(sql);
    }

    public synchronized StepRecord find(String workflowId, String stepKey) throws SQLException {
        String sql = "SELECT * FROM steps WHERE workflow_id=? AND step_key=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, workflowId);
        ps.setString(2, stepKey);

        ResultSet rs = ps.executeQuery();
        if (!rs.next()) return null;

        StepRecord r = new StepRecord();
        r.workflowId = workflowId;
        r.stepKey = stepKey;
        r.status = rs.getString("status");
        r.output = rs.getString("output");
        return r;
    }

    public synchronized void insertInProgress(String workflowId, String stepKey) throws SQLException {
        String sql = "INSERT INTO steps VALUES (?, ?, 'IN_PROGRESS', NULL)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, workflowId);
        ps.setString(2, stepKey);
        ps.executeUpdate();
    }

    public synchronized void markSuccess(String workflowId, String stepKey, String output)
            throws SQLException {

        String sql = "UPDATE steps SET status='SUCCESS', output=? WHERE workflow_id=? AND step_key=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, output);
        ps.setString(2, workflowId);
        ps.setString(3, stepKey);
        ps.executeUpdate();
    }

    public synchronized void cleanupZombieSteps() throws SQLException {
        connection.createStatement()
                .execute("DELETE FROM steps WHERE status='IN_PROGRESS'");
    }
}
