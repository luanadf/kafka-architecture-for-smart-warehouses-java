package service;

import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import model.Pedido;
import model.Produto;

public class LeitorPedidosJson {

	public static void main(String[] args) {
		getPedidosDoJson("./pedidos/orders_of_SIMU-i180-o6-r250-dHT03-d52-1.1.json");
	}

	// public LeitorPedidosJson() {}

	public static void getPedidosDoJson(String filepath) {
		// TODO passar como argumento depois

		// TODO ARRUMAR PATH AQUI
		String static_filepath = "/kafka-architecture-for-smart-warehouses-java/pedidos/orders_of_SIMU-i180-o6-r250-dHT03-d52-1.1.json";

		InputStream is = LeitorPedidosJson.class.getResourceAsStream(static_filepath);
		if (is == null) {
			throw new NullPointerException("Cannot find resource file " + static_filepath);
		}

		JSONTokener tokener = new JSONTokener(is);

		JSONArray array_pedidos_json = new JSONArray(tokener);

		Long id_pedido = (long) 0;

		for (Object objeto_pedido : array_pedidos_json) {

			JSONObject json_pedido = (JSONObject) objeto_pedido;

			Pedido pedido = jsonParaPedido(json_pedido, id_pedido);

			System.out.println(pedido.toString());

			id_pedido++;

		}
	}

	public static Pedido jsonParaPedido(JSONObject json_pedido, Long id_pedido) {

		Pedido pedido = new Pedido();
		pedido.setId(id_pedido);

		ArrayList<Produto> produtos = new ArrayList<Produto>();

		json_pedido.keys().forEachRemaining(key -> {
			Object value = json_pedido.get(key);

			Produto produto = jsonParaProduto(key, value);

			produtos.add(produto);
		});

		pedido.setProdutos(produtos);

		return pedido;
	}

	public static Produto jsonParaProduto(String chave, Object valor) {
		Long id_produto = Long.parseLong(chave);

		JSONObject dados_produto = (JSONObject) valor;

		int coordenada_x = dados_produto.getInt("coord_x");
		int coordenada_y = dados_produto.getInt("coord_y");
		int altura_prateleira = dados_produto.getInt("altura_prateleira");
		float peso = dados_produto.getInt("peso_total");

		return new Produto(id_produto, coordenada_x, coordenada_y, altura_prateleira, peso);
	}

}
