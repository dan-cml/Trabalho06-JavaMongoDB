package trabMongoProject;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoMain {

	public static void main(String[] args) {
		System.out.println("Conectando...");
		
		MongoClient client = MongoClients.create("mongodb://localhost");
		
		MongoDatabase db = client.getDatabase("test");
		
		System.out.println("colecoes da base test");
		Iterable<Document> collections = db.listCollections();
		for (Document col : collections) {
			System.out.println(col.get("name"));
		}
		
		MongoCollection<Document> collection = db.getCollection("produtos");
		System.out.println("*----*----*----*----*----*----*----*----*");
		System.out.println("Imprimindo Produtos (Antes do incremento)");
		
		Iterable<Document> produtos = collection.find();
		for (Document produto : produtos) {
			String nome = produto.getString("nome");
			String descricao = produto.getString("descricao");
			String valor = produto.getString("valor");
			String estado = produto.getString("estado");
			System.out.println(nome + " -- " + descricao + " -- " + valor + " -- " + estado);
		}
				
		Document novoProduto = new Document();
		novoProduto.put("nome", "Produto Teste");
		novoProduto.put("descricao", "Descricao generica");
		novoProduto.put("valor", "1500.0");
		novoProduto.put("estado", "novo");
		collection.insertOne(novoProduto);
		System.out.println("*----*----*----*----*----*----*----*----*");
		System.out.println("Imprimindo Produtos (Após incremento de um novo produto)");
		
		for (Document produto : produtos) {
			String nome = produto.getString("nome");
			String descricao = produto.getString("descricao");
			String valor = produto.getString("valor");
			String estado = produto.getString("estado");
			System.out.println(nome + " -- " + descricao + " -- " + valor + " -- " + estado);
		}
		
		
		Document query = new Document();
		query.put("nome", "Produto Teste");
		
		Document novoValor = new Document();
		novoValor.put("valor", "1950.0");
		
		Document updateObject = new Document();
		updateObject.put("$set", novoValor);
		
		collection.updateOne(query, updateObject);
		System.out.println("*----*----*----*----*----*----*----*----*");
		System.out.println("Imprimindo Produtos (Valor do ultimo elemento objeto alterado)");
		
		for (Document produto : produtos) {
			String nome = produto.getString("nome");
			String descricao = produto.getString("descricao");
			String valor = produto.getString("valor");
			String estado = produto.getString("estado");
			System.out.println(nome + " -- " + descricao + " -- " + valor + " -- " + estado);
		}
		
		Document deletarProduto = new Document();
		deletarProduto.put("estado", "novo");
		
		collection.deleteOne(deletarProduto);
		System.out.println("*----*----*----*----*----*----*----*----*");
		System.out.println("Imprimindo Produtos (Sem ultimo elemento)");
		
		for (Document produto : produtos) {
			String nome = produto.getString("nome");
			String descricao = produto.getString("descricao");
			String valor = produto.getString("valor");
			String estado = produto.getString("estado");
			System.out.println(nome + " -- " + descricao + " -- " + valor + " -- " + estado);
		}
		System.out.println("*----*----*----*----*----*----*----*----*");
		System.out.println("Fechando conexão...");
		client.close();
		
	}

}
