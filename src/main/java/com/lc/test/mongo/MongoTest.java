package com.lc.test.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BasicBSONObject;
import org.bson.Document;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * mongodb测试
 */
public class MongoTest {

	/**创建链接*/
	static MongoClient client = new MongoClient("192.168.20.138:27017");

	public static void main(String[] args) {
//		mongoAdd();
		mongoQuery();
	}

	public static void mongoQuery() {

		MongoCollection<Document> spit = getMongoCollection();

		//查询记录获取文档集合
//		FindIterable<Document> documents = spit.find();
		//构建查询条件
//		BasicDBObject bson = new BasicDBObject("userid","1013");
		//BasicDBObject就是类似于{}，会写原生的查询方式，这个组合就行。
		BasicDBObject bson = new BasicDBObject("visits", new BasicDBObject("$gt", 1000));
		FindIterable<Document> documents = spit.find(bson);

		for (Document document:documents){
			System.out.println("内容："+document.getString("content"));
			System.out.println("用户id："+document.getString("userid"));
			System.out.println("浏览量："+document.getInteger("visits"));
		}
		//关闭连接
		client.close();
	}

	public static void mongoAdd(){
		MongoCollection<Document> spit = getMongoCollection();

		Map<String,Object> dataMap = new HashMap<>();
		dataMap.put("_id","10");
		dataMap.put("content","按照文档操作还是很快的嘛");
		dataMap.put("userid","1015");
		dataMap.put("visits",20000);
		dataMap.put("publishtime",new Date());

		Document document = new Document(dataMap);
		spit.insertOne(document);
		client.close();
	}

	public static MongoCollection<Document> getMongoCollection(){
		//打开数据库
		MongoDatabase database = client.getDatabase("spitdb");
		//获取集合
		MongoCollection<Document> spit = database.getCollection("spit");

		return spit;
	}

}
