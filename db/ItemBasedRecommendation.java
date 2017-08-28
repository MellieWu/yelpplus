package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class ItemBasedRecommendation {
	static class ItemSimilarity{
		String itemB;
		double similarity;
		ItemSimilarity( String itemB, double similarity){
			this.itemB = itemB;
			this.similarity = similarity;
		}
	}
	private static final int RECOMMEND_LIMIT = 50;
	
	public static Set<String> getItemBasedRecommendation(Set<String> visitedRestaurants, Set<String> allRestaurants, 
														Map<String, String> user_preference){
		Set<String> recommendedRestaurants = new HashSet<>();
		int queue_size = 0;
		Queue<ItemSimilarity> similarity_queue = new PriorityQueue<ItemSimilarity>(RECOMMEND_LIMIT, 
				new Comparator<ItemSimilarity>(){
			public int compare(ItemSimilarity i1, ItemSimilarity i2){
				if (i1.similarity == i2.similarity){
					return 0;
				} else if (i1.similarity > i2.similarity){
					return 1;
				} else {
					return -1;
				}
			}
		});
		

		Map<String, Set<String>> business_user_map = new HashMap<>();
		
		for (String business_id : user_preference.keySet()){
			business_user_map.putIfAbsent(business_id, new HashSet<>());
			business_user_map.get(business_id).add(user_preference.get(business_id));
		}
		
		for (String itemB : allRestaurants){
			
			if (visitedRestaurants.contains(itemB)){
				continue;
			}
			
			int itemB_user = 0;
			
			if (business_user_map.containsKey(itemB)){
				itemB_user = business_user_map.get(itemB).size();
			}
			
			double max_relevance = 0.0;
			
			for (String itemA : visitedRestaurants){
				int itemA_user = 0;
				if (business_user_map.containsKey(itemA)){
					itemA_user = business_user_map.get(itemA).size();
				}
				int itemAandItemB_user = getCommonUser(business_user_map, itemA, itemB);
				double currt_relevance = 0.0;
				if (itemAandItemB_user != 0){
					currt_relevance = (double)(itemAandItemB_user/(itemA_user*itemB_user));
				}
				max_relevance = Math.max(currt_relevance, max_relevance);
			}
			
			if (max_relevance == 0.0){
				continue;
			}
			
			if (queue_size < RECOMMEND_LIMIT){
				similarity_queue.offer(new ItemSimilarity(itemB, max_relevance));
				queue_size++;
			} else {
				if (max_relevance > similarity_queue.peek().similarity){
					similarity_queue.poll();
					similarity_queue.offer(new ItemSimilarity(itemB, max_relevance));
					queue_size++;
				}
			}
		}
		
		while (!similarity_queue.isEmpty()){
			recommendedRestaurants.add(similarity_queue.poll().itemB);
		}
		return recommendedRestaurants;
	}
	
	
	private static int getCommonUser(Map<String, Set<String>> business_user_map, String itemA, String itemB){
		int num = 0;
		if (business_user_map.containsKey(itemA) || business_user_map.containsKey(itemB)){
			return num;
		}
		Set<String> itemA_users = business_user_map.get(itemA);
		Set<String> itemB_users = business_user_map.get(itemB);
		for (String user: itemA_users){
			if (itemB_users.contains(user)){
				num++;
			}
		}
		return num;
	}
}
