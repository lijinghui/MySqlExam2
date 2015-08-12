package com.hand.Exam2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("请输入CustmentID");
		Scanner sc = new Scanner(System.in);
		int id = sc.nextInt();
		System.out.println(getName(id)+"租用的电影有：");
		query(id);
		
	}

	public static Connection getConn() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sakila?characterEncoding=utf8", "root", "");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

	public static void query(int id) {
		Connection conn = getConn();
		String sql = "select a.first_name ,b.rental_date,c.film_id,d.title from customer AS a ,rental AS b, inventory AS c ,film AS d WHERE "
				+ "a.customer_id = b.customer_id AND a.customer_id = " + id
				+ " AND b.inventory_id = c.inventory_id AND c.film_id = d.film_id ORDER BY b.rental_date desc;";
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				System.out.println(rs.getString("film_id")+ "\t"+rs.getString("title") + "\t" + rs.getString("rental_date"));
			}

			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getName(int id) {
		Connection conn = getConn();
		String sql = "select first_name,last_name from customer where  customer_id =  " + id;
		String country = null;
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			country = rs.getString("first_name")+"\t"+rs.getString("last_name");
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return country;
	}
}
