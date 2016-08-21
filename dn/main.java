
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
 * main.java
 * @author Sergio Cruz
 * @date 2014-10-13
 */

public class main {
	private static String _dbname = "test.db";

	/**
	 * @param args command line arguments.
	 */
	public static void
	main(String args[])
	{
		Connection c;
		ResultSet rs;
		Object res;

		try {
			/* Open connection */
			c = sqlite_jdbc.open_connection(_dbname);

			/* Table creation */
			/* By strange reasons (I don't understand why :( ), the 
			   SQL statement DEFAULT NULL doesn't works as far I
			   know. Java launch an Exception if fourth field is not
			   assigned on INSERT statement. */
			res = (Integer) sqlite_jdbc.execute_sql(c, 
				"CREATE TABLE IF NOT EXISTS persons (" +
				"id INTEGER PRIMARY KEY," +
				"name VARCHAR(20) NOT NULL," +
				"surname VARCHAR(50) NOT NULL," +
				"surname2 VARCHAR(200) DEFAULT NULL);");
			if (res != null)
				System.out.println("--TABLE CREATION OK --");

			/* Delete table data for program re-execution */
			sqlite_jdbc.execute_sql(c, "DELETE FROM persons;");

			/* Insert a pair of values */
			sqlite_jdbc.execute_sql(c,
				"INSERT INTO persons VALUES(1, 'Sergio', 'Cruz'," +
					"'Hernandez');");
			sqlite_jdbc.execute_sql(c,
				"INSERT INTO persons VALUES(2, 'Pepe'," +
					"'Lopez', 'N/A');");
			System.out.println("-- INSERTS OK --");

			/* Begin SELECT operation */
			rs = (ResultSet) sqlite_jdbc.execute_select(c,
					"SELECT * FROM persons;");
			if (rs != null) {
				System.out.println("\n--- OUTPUT TABLE DATA ---");
				while (rs.next()) {
					/* The string in each method of ResultSet is the
			   		field of table. */
					System.out.println("ID: " + rs.getInt("id"));
					System.out.println("NAME: " +
						rs.getString("name"));
					System.out.println("SURNAME: " + 
						rs.getString("surname"));
					System.out.println("SURNAME 2: " + 
						rs.getString("surname2"));
					System.out.println();
				}
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
