package home.filecopier;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class DBProcessor {

	public String DBDirectory;

	public DBProcessor(){

	}

	public static void main(String[] args) {
		//Query
		String query="CREATE KEYSPACE tmp1 WITH replication "+ 
				"= {'class':'SimpleStrategy','replication_factor':1};";
		//creating Cluster object
		Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		//Creating Session object
		Session session=cluster.connect();
		//Executing the query
		session.execute(query);
		//using the KeySpace
		session.execute("USE tmp1");
		System.out.println("Keyspace created");

	}

}
