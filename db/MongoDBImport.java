package db;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class MongoDBImport {
	public static void main(String[] args) throws ParseException {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(DBUtil.DB_NAME);
		db.getCollection("users").insertOne(
				new Document()
						.append("first_name", "MENG")
						.append("last_name", "WU")
						.append("password", "3229c1097c00d497a0fd282d586be050")
						.append("user_id", "2222"));
		IndexOptions indexOptions = new IndexOptions().unique(true);
		
		db.getCollection("users").createIndex(new Document("user_id", 1), indexOptions);
		
		db.getCollection("restaurants").createIndex(new Document("business_id", 1), indexOptions);
		
		db.getCollection("restaurants").createIndex(
				new Document()
					.append("categories", "text")
					.append("full_address", "text")
					.append("name", "text"));
		mongoClient.close();
	}

}
