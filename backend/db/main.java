package db;

public class main {
	
	public static void main(String[] args) {
		MySQLDBConnection connection = new MySQLDBConnection();
		connection.itemBasedRecommendRestaurants("1111");
	}

}
