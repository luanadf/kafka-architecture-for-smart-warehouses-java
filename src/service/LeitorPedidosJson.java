package service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import model.Pedido;
import model.Produto;

public class LeitorPedidosJson {

	private String filename;
	private ArrayList<Pedido> pedidos;
	private ArrayList<BigDecimal> tempo_pedidos;

	private int idPedidoAtual;

	public LeitorPedidosJson(String filename) {
		super();
		this.filename = filename;
		this.pedidos = lerPedidosJson();
		this.tempo_pedidos = lerTempoPedidos();
		this.idPedidoAtual = 0;
	}

	public ArrayList<Pedido> lerPedidosJson() {

		// TODO passar como argumento depois
		// String arquivo = getFilepath();
		String static_filepath = "../resources/orders_of_SIMU-i180-o6-r250-dHT03-d52-1.1.json";

		InputStream is = LeitorPedidosJson.class.getResourceAsStream(static_filepath);
		if (is == null) {
			throw new NullPointerException("Cannot find resource file " + static_filepath);
		}

		JSONTokener tokener = new JSONTokener(is);

		JSONArray array_pedidos_json = new JSONArray(tokener);

		ArrayList<Pedido> lista_pedidos = new ArrayList<Pedido>();
		Integer id_pedido = 0;

		for (Object objeto_pedido : array_pedidos_json) {

			JSONObject json_pedido = (JSONObject) objeto_pedido;

			Pedido pedido = jsonParaPedido(json_pedido, id_pedido);

			// System.out.println(pedido);
			lista_pedidos.add(pedido);
			id_pedido++;
		}

		return lista_pedidos;
	}

	public static Pedido jsonParaPedido(JSONObject json_pedido, Integer id_pedido) {

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

	public Pedido getProximoPedido() {
		int id = getIdPedidoAtual();

		ArrayList<Pedido> pedidos = getPedidos();
		Pedido pedido = pedidos.get(id);

		if (id + 1 == pedidos.size()) {
			setIdPedidoAtual(0);
		} else {
			id++;
			setIdPedidoAtual(id);
		}

		return pedido;
	}

	public ArrayList<BigDecimal> lerTempoPedidos() {

		String static_filepath = "../resources/time_of_orders_of_SIMU-i180-o6-r250-dHT03-d52-1.1.json";

		InputStream is = LeitorPedidosJson.class.getResourceAsStream(static_filepath);
		if (is == null) {
			throw new NullPointerException("Cannot find resource file " + static_filepath);
		}

		JSONTokener tokener = new JSONTokener(is);

		JSONArray array_tempos_pedidos_json = new JSONArray(tokener);

		ArrayList<BigDecimal> lista_tempos = new ArrayList<BigDecimal>();

		for (Object tempo : array_tempos_pedidos_json) {

			BigDecimal tempo_pedido = (BigDecimal) tempo;

			lista_tempos.add(tempo_pedido);
		}

		return lista_tempos;
	}

	public BigDecimal getTempoByPedidoId(int id_pedido) {
		ArrayList<BigDecimal> tempo_pedidos = this.getTempo_pedidos();
		return tempo_pedidos.get(id_pedido);
	}

	public String getFilepath() {
		return filename;
	}

	public void setFilepath(String filename) {
		this.filename = filename;
	}

	public ArrayList<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(ArrayList<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public int getIdPedidoAtual() {
		return idPedidoAtual;
	}

	public void setIdPedidoAtual(int idPedidoAtual) {
		this.idPedidoAtual = idPedidoAtual;
	}

	public ArrayList<BigDecimal> getTempo_pedidos() {
		return tempo_pedidos;
	}

	public void setTempo_pedidos(ArrayList<BigDecimal> tempo_pedidos) {
		this.tempo_pedidos = tempo_pedidos;
	}

}
