package com.docmgmt.server.db.dbcp;

import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;

public class ConnectionManager {

	private static final Log LOG = LogFactory.getLog(ConnectionManager.class);

	public static DataSource ds = null;
	private static GenericObjectPool _pool = null;

	/**
	 * @param config
	 *            configuration from an XML file.
	 */
	public ConnectionManager(Configuration config) {
		try {
			if (ds == null)
				connectToDB(config);
		} catch (Exception e) {
			LOG.error("Failed to construct ConnectionManager", e);
			// System.out.println("Failed to construct ConnectionManager"+ e);
		}
	}

	/**
	 * destructor
	 */
	@Override
	protected void finalize() {
		// LOG.debug("Finalizing ConnectionManager");
		// System.out.println("Finalizing ConnectionManager");
		try {
			super.finalize();
		} catch (Throwable ex) {
			// LOG.error(
			// "ConnectionManager finalize failed to disconnect from mysql: ",
			// ex );
			System.out
					.println("ConnectionManager finalize failed to disconnect from sql: "
							+ ex);
		}
	}

	/**
	 * connectToDB - Connect to the DB!
	 */
	private void connectToDB(Configuration config) {

		try {
			java.lang.Class.forName(config.getDbDriverName()).newInstance();
		} catch (Exception e) {
			/*
			 * LOG.error("Error when attempting to obtain DB Driver: " +
			 * config.getDbDriverName() + " on " + new Date().toString(), e);
			 */

			System.out.println("Error when attempting to obtain DB Driver: "
					+ config.getDbDriverName() + " on " + new Date().toString()
					+ e);
		}

		// LOG.debug("Trying to connect to database...");
		// System.out.println("Trying to connect to database...");
		try {
			ConnectionManager.ds = setupDataSource(config.getDbURI(), config
					.getDbUser(), config.getDbPassword(), config
					.getDbPoolMinSize(), config.getDbPoolMaxSize(), config
					.getDbDestroyProcessTime(), config
					.getDbDestroyIdleConnTime());

			// LOG.debug("Connection attempt to database succeeded.");

			// System.out.println("Connection attempt to database succeeded.");
		} catch (Exception e) {
			LOG.error("Error when attempting to connect to DB ", e);
			// System.out.println("Error when attempting to connect to DB "+ e);
		}
	}

	/**
	 * 
	 * @param connectURI
	 *            - JDBC Connection URI
	 * @param username
	 *            - JDBC Connection username
	 * @param password
	 *            - JDBC Connection password
	 * @param minIdle
	 *            - Minimum number of idel connection in the connection pool
	 * @param maxActive
	 *            - Connection Pool Maximum Capacity (Size)
	 * @throws Exception
	 * 
	 */
	public static DataSource setupDataSource(String connectURI,
			String username, String password, int minIdle, int maxActive,
			long destroyProcessTime, long destroyConnTime) throws Exception {
		//
		// First, we'll need a ObjectPool that serves as the
		// actual pool of connections.
		//
		// We'll use a GenericObjectPool instance, although
		// any ObjectPool implementation will suffice.
		//
		GenericObjectPool connectionPool = new GenericObjectPool(null);

		connectionPool.setMinIdle(minIdle);
		// connectionPool.setMaxIdle( minIdle );
		connectionPool.setMaxActive(maxActive);
		// connectionPool.setTimeBetweenEvictionRunsMillis(destroyProcessTime);
		// connectionPool.setMinEvictableIdleTimeMillis(destroyConnTime);

		ConnectionManager._pool = connectionPool;
		// we keep it for two reasons
		// #1 We need it for statistics/debugging
		// #2 PoolingDataSource does not have getPool()
		// method, for some obscure, weird reason.

		//
		// Next, we'll create a ConnectionFactory that the
		// pool will use to create Connections.
		// We'll use the DriverManagerConnectionFactory,
		// using the connect string from configuration
		//
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
				connectURI, username, password);

		//
		// Now we'll create the PoolableConnectionFactory, which wraps
		// the "real" Connections created by the ConnectionFactory with
		// the classes that implement the pooling functionality.
		//
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(
				connectionFactory, connectionPool, null, null, false, true);

		PoolingDataSource dataSource = new PoolingDataSource(connectionPool);

		return dataSource;
	}

	public static void printDriverStats() throws Exception {
		ObjectPool connectionPool = ConnectionManager._pool;
		// LOG.info("NumActive: " + connectionPool.getNumActive());
		// LOG.info("NumIdle: " + connectionPool.getNumIdle());
		
		
		
		System.out.println("------------------DBCP START-------------------");
		if (connectionPool != null) {
			System.out.println("NumActive: " + connectionPool.getNumActive());
			System.out.println("NumIdle: " + connectionPool.getNumIdle());
		} else {
			System.out.println("Connection Pool is NULL....");
		}
		System.out.println(new Date());
		System.out.println("------------------DBCP END---------------------");
	}

}
