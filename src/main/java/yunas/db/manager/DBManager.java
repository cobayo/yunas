package yunas.db.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yunas.db.DBName;
import yunas.db.Databases;
import yunas.db.model.ResultSetRow;
import yunas.util.BaseUtil;

import java.io.Closeable;
import java.sql.*;
import java.util.Collections;
import java.util.List;

/**
 * Custom Methods For executing sql.
 * DBManager Must be called with 'use'
 *
 * see yunas.db.Databases
 */
public class DBManager implements  AutoCloseable, Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(DBManager.class);

    private Connection connection;

    private Throwable error;

    public void setError(Throwable error) {
        this.error = error;
    }
    public boolean hasError() {
        return error != null;
    }


    /**
     * Constructor.
     *
     * @param dbName DBName
     * @throws SQLException
     */
    public DBManager(DBName dbName) throws SQLException {

        connection = Databases.getConnection(dbName);
    }

    /**
     * Execute Select SQL.
     *
     * @param sql String
     * @param params List
     * @return ResultSetRow is always not null
     */
    public ResultSetRow select(String sql, List<Object> params){

        PreparedStatement st = null;
        try {

            st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            setParameters(st, params);

            ResultSet rs = st.executeQuery();

            return ResultSetRow.convert(rs);

        } catch (Exception e) {
            LOG.debug(e.getMessage());
            this.error = e;
            return new ResultSetRow(Collections.emptyMap());
        } finally {
            BaseUtil.closeAC(st);
        }

    }

    /**
     * Execute Select SQL And Return List.
     *
     * @param sql String
     * @param params List
     * @return List is always not null
     */
    public List<ResultSetRow> selectList(String sql, List<Object> params) {

        PreparedStatement st = null;
        try {

            st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            setParameters(st, params);

            ResultSet rs = st.executeQuery();

            return ResultSetRow.convertToList(rs);

        } catch (Exception e) {
            LOG.debug(e.getMessage());
            this.error = e;
            return Collections.emptyList();
        } finally {
            BaseUtil.closeAC(st);
        }

    }


    /**
     * Execute Update Query.
     * @param sql String
     * @param params List<Object>
     * @return update rows.  -1: error.
    </Object> */
    public int update(String sql, List<Object> params) {


        PreparedStatement st = null;
        try {

            st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            setParameters(st, params);

            return st.executeUpdate();

        } catch (Exception e) {
            LOG.debug(e.getMessage());
            this.error = e;
            return -1;
        } finally {
            BaseUtil.closeAC(st);
        }

    }

    /**
     * Execute Insert Query.
     * @param sql String
     * @param params List<Object>
     * @return insert rows.  -1: error.
    </Object> */
    public long insert(String sql, List<Object> params) {

        PreparedStatement st = null;
        ResultSet rs = null;
        try {

            st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            setParameters(st, params);

            int cnt = st.executeUpdate();
            if (cnt > 0) {

                rs = st.getGeneratedKeys();

                if (rs == null || !rs.next()) {

                    return cnt;

                } else {
                    return rs.getLong(1);
                }

            }

            return cnt;

        } catch (Exception e) {
            LOG.debug(e.getMessage());
            this.error = e;
            return -1;
        } finally {
            BaseUtil.closeAC(rs, st);
        }

    }

    /**
     * Execute Delete Query.
     * @param sql String
     * @param params List<Object>
     * @return delete rows.  -1: error.
    </Object> */
    public int delete(String sql, List<Object> params){

        return update(sql, params);
    }

    private void setParameters(PreparedStatement st, List<?> params) throws  Exception {

        if (params == null || params.isEmpty()) {
            return;
        }

        for (int i = 0; i < params.size(); i++) {

            Object param = params.get(i);

            int index = i + 1;

            st.setObject(index, param);

        }

    }

    public boolean beginTransaction() {

        if (connection == null) {
            return false;
        }

        try {

            connection.setAutoCommit(false);

            return true;

        } catch (Exception e) {

            LOG.debug(e.getMessage());
            this.error = e;
            return false;

        }

    }

    public void rollback() {

        if (connection == null) {
            return;
        }

        try {

            if (!connection.getAutoCommit()) {
                connection.rollback();
            }

        } catch (Exception e) {
            LOG.debug(e.getMessage());
            this.error = e;
        }

    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        if (connection != null) {
            connection.setAutoCommit(autoCommit);
        }
    }

    public boolean commit() {

        if (connection == null) {
            return false;
        }

        try {

            if (!connection.getAutoCommit()) {
                connection.commit();
            }

            return true;

        } catch (Exception e) {

            LOG.debug(e.getMessage());
            this.error = e;

            return false;

        }

    }


    @Override
    public void close() {

        try {

            connection.close();
        } catch (Exception e) {
            LOG.debug(e.getMessage());
            this.error = e;
        }

    }



}


