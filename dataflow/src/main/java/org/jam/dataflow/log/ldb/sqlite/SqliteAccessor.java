package org.jam.dataflow.log.ldb.sqlite;

import java.sql.*;

import com.alibaba.fastjson.JSON;
import org.jam.dataflow.Dataflow;
import org.jam.dataflow.log.domain.FailLog;
import org.jam.dataflow.log.ldb.LocalDBAccessor;

/**
 * Created by James on 16/4/29.
 */
public class SqliteAccessor implements LocalDBAccessor {

    private Connection conn;

    public SqliteAccessor(String dbFileName) throws Exception{
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:" + dbFileName + ".db");
        Statement stat = conn.createStatement();
        String sql = new StringBuffer()
                .append("create table if not exists fail_log (")
                .append("id text, time num, biz text, phase integer, retry integer, status integer, ok_time num, error_msg text, data text")
                .append(");").toString();
        stat.executeUpdate(sql);
        sql = new StringBuffer()
                .append("create index if not exists fail_log_index on fail_log (status, time, retry)").toString();
        stat.executeUpdate(sql);
    }

    public void addFailLog(FailLog log){
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement("insert into fail_log(id, time, biz, phase, status, error_msg, data,retry) values(?, ?, ?, ?, ?, ?, ?,?)");
            prep.setString(1, log.getId());
            prep.setLong(2, log.getTime());
            prep.setString(3, log.getBiz());
            prep.setInt(4, log.getPhase());
            prep.setInt(5, log.getStatus());
            prep.setString(6, log.getError_msg());
            prep.setString(7, log.getData());
            prep.setInt(8, 0);
            prep.execute();
        } catch (Exception e) {
            Dataflow.logger().error("DANGER!!! Failed to add log: " + log.toString(), e);
        }finally {
            try {
                prep.close();
            } catch (SQLException e) {
                Dataflow.logger().warn("Warn to close localdb error", e);
            }
            Dataflow.logger().debug("Add log: " + log.toString());
        }
    }


    public synchronized FailLog takeFailLog(int phase) {
        ResultSet rs = null;
        PreparedStatement prep = null;
        FailLog  log = null;
        Statement stat = null;
        try {
            stat = conn.createStatement();
            String sql = new StringBuffer()
                    .append("select * from fail_log where phase=")
                    .append(phase)
                    .append(" and status=")
                    .append(FailLog.FAILED)
                    .append(" order by time limit 1")
                    .toString();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                log = new FailLog();
                log.setId(rs.getString("id"));
                log.setBiz(rs.getString("biz"));
                log.setTime(rs.getLong("time"));
                log.setPhase(rs.getInt("phase"));
                log.setStatus(rs.getInt("status"));
                log.setError_msg(rs.getString("error_msg"));
                log.setData(rs.getString("data"));

                prep = conn.prepareStatement("update fail_log set status=? where id=?");
                prep.setInt(1, FailLog.TAKED);
                prep.setString(2, log.getId());
                prep.execute();
                prep.close();
                break;
            }
        } catch (Exception e) {
            Dataflow.logger().error("DANGER!!! Failed to take log at phase: " + phase, e);
        }finally {
            try {
                stat.close();
                rs.close();
                if(prep != null){
                    prep.close();
                }
            } catch (SQLException e) {
                Dataflow.logger().warn("Warn to close localdb error", e);
            }
            if (null!=log) {
            	 Dataflow.logger().debug("Take log: " + log.getId());
            }
           
        }
        return log;
    }

    public void returnFailLog(String id) {
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement("update fail_log set status=? , retry=retry+1 where id=?");
            prep.setInt(1, FailLog.FAILED);
            prep.setString(2, id);
            prep.execute();
        } catch (Exception e) {
            Dataflow.logger().error("DANGER!!! Failed to return log: " + id, e);
        }finally {
            try {
                prep.close();
            } catch (SQLException e) {
                Dataflow.logger().warn("Warn to close localdb error", e);
            }
            Dataflow.logger().debug("Return log: " + id);
        }

    }

    public void finishFailLog(String id) {
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement("update fail_log set status=?,ok_time=? where id=?");
            prep.setInt(1, FailLog.FINISHED);
            prep.setLong(2, (new java.util.Date()).getTime());
            prep.setString(3, id);
            prep.execute();
        } catch (Exception e) {
            Dataflow.logger().error("DANGER!!! Failed to finish log: " + id, e);
        }finally {
            try {
                prep.close();
            } catch (SQLException e) {
                Dataflow.logger().warn("Warn to close localdb error", e);
            }
            Dataflow.logger().debug("Finish log: " + id);
        }
    }

    public boolean hasFailLog(int phase) {
        ResultSet rs = null;
        PreparedStatement prep = null;
        Statement stat = null;
        try {
            stat = conn.createStatement();
            String sql = new StringBuffer()
                    .append("select * from fail_log where phase=")
                    .append(phase)
                    .append(" and status=")
                    .append(FailLog.FAILED)
                    .toString();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            Dataflow.logger().error("DANGER!!! Failed to check log at phase: " + phase, e);
            return true;
        }finally {
            try {
                stat.close();
                rs.close();
            } catch (SQLException e) {
                Dataflow.logger().warn("Warn to close localdb error", e);
            }
        }
    }
}
