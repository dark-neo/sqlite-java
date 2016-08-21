
/**
 * Copyright (c) 2014, dark_neo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package sch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * sqlite_jdbc.java
 * @author Sergio Cruz
 * @date 2014-10-13
 */

public abstract class sqlite_jdbc {
/**
 * CLASS METHODS - PROTOTYPES
 * ==========================
 *
 * Connection	open_connection(String dbname);
 * void		close_connection(Connection c);
 * ResultSet	execute_select(Connection c, String sql);
 * int		execute_sql(Connection c, String sql);
 */
	/**
	 * Create a new database file or open existing file.
	 * @param dbname database filename
	 * @return (1) a <b>java.sql.Connection</b> object OR
	 *	(2) <b>null</b> if failure.
	 */
	public static Connection
	open_connection(String dbname)
	{
		Connection c = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + 
				dbname);
			if (c == null)
				return null;

			return c;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Close current database connection.
	 * @param c	a non-null java.sql.Connection object which is an opened
	 *		connection.
	 */
	public static void
	close_connection(Connection c)
	{
		try {
			if (c != null)
				c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Launch a SQL Select query.
	 * @param c opened connection.
	 * @param sql string with valid SQL query. The method determines if it's
	 *	a valid query.
	 * @return (1) <b>ResultSet</b> object if SQL is valid OR
	 *	(2) <b>null</b> if something was wrong.
	 */
	public static Object
	execute_select(Connection c, String sql)
	{
		Statement stmt;

		try {
			/* Check if SQL has not SELECT on it */
			if (!sql.contains("select") && !sql.contains("SELECT")) {
				System.err.println("ERROR. THIS METHOD ONLY\n" +
				"WORKS WITH SELECT SQL QUERYS! PLEASE USE\n" +
				"execute_sql() METHOD FOR OTHER THAN SELECT\n" +
				"SQL QUERYS.");
				return null;
			}
			if (c == null)
				return null;

			stmt = c.createStatement();
			return stmt.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Launch a Non-Select SQL query (UPDATE, INSERT, DELETE, CREATE, DROP,
	 * etc).
	 * @param c opened connection.
	 * @param sql string with valid SQL query. The method determines if it's
	 * 	a valid query.
	 * @return (1) <b>Integer</b> object with affected rows OR
	 *	(2) <b>null</b> if something was wrong.
	 */
	public static Integer
	execute_sql(Connection c, String sql)
	{
		int res;
		Statement stmt;

		try {
			/* Check if SQL has SELECT on it */
			if (sql.contains("select") && sql.contains("SELECT")) {
				System.err.println("ERROR. THIS METHOD ONLY\n" +
				"WORKS WITH NON-SELECT SQL QUERYS! PLEASE\n" +
				"USE execute_select() METHOD FOR SELECT SQL\n" +
				"QUERYS.");
				return null;
			}
			if (c == null)
				return null;

			stmt = c.createStatement();
			res = stmt.executeUpdate(sql);
			stmt.close();
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
