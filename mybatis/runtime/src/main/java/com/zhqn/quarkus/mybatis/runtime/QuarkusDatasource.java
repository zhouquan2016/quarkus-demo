package com.zhqn.quarkus.mybatis.runtime;


import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.runtime.DataSources;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class QuarkusDatasource implements DataSource {

    private String ds;

    public QuarkusDatasource(String ds) {
        this.ds = ds;
    }

    private AgroalDataSource getAgroalDatasource () {
        return DataSources.fromName(ds);
    }
    @Override
    public Connection getConnection() throws SQLException {
        return getAgroalDatasource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getAgroalDatasource().getConnection(username, password);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return getAgroalDatasource().getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        getAgroalDatasource().setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        getAgroalDatasource().setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return getAgroalDatasource().getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return getAgroalDatasource().getParentLogger();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return getAgroalDatasource().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return getAgroalDatasource().isWrapperFor(iface);
    }
}
