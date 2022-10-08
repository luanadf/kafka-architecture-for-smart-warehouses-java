package utils;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONTokener;

import components.LeitorPedidosJson;

public class Testes {

	public static void main(String[] args) throws InterruptedException {

		// Teste da função Gamma

		/*
		 * Orders were generated each 1 minute, and the number of orders followed a gamma distribution with parameters α = 6.34
		 * and β = 1.0 (more or less six orders every minute). double shape = 6.34; double scale = 1.0;
		 * 
		 * while (true) { double sample = new GammaDistribution(shape, scale).sample();
		 * 
		 * double n_pedidos = round(sample, 0);
		 * 
		 * double segundos_por_pedido = 60.0 / n_pedidos;
		 * 
		 * double x = round(segundos_por_pedido, 0);
		 * 
		 * System.out.println(n_pedidos + " pedidos = 1 pedido a cada: " + (int) x + " segundos");
		 * 
		 * System.out.println("em millisegundos: " + (int) x * 1000);
		 * 
		 * Thread.sleep(1000); }
		 */

		// Teste Leitor tempo e conversao para millisegundos

		String static_filepath = "../resources/time_of_orders_of_SIMU-i180-o6-r250-dHT03-d52-1.1.json";

		InputStream is = LeitorPedidosJson.class.getResourceAsStream(static_filepath);
		if (is == null) {
			throw new NullPointerException("Cannot find resource file " + static_filepath);
		}

		JSONTokener tokener = new JSONTokener(is);

		JSONArray array_tempos_pedidos_json = new JSONArray(tokener);

		ArrayList<BigDecimal> lista_tempos = new ArrayList<BigDecimal>();

		for (Object tempo : array_tempos_pedidos_json) {
			System.out.println("\nTempo do pedido:");

			BigDecimal tempo_pedido_segundos = (BigDecimal) tempo;
			System.out.println(" - Segundos: " + tempo_pedido_segundos);

			long tempo_pedido_millisegundos = bigDecimalSegundosToLongMillisegundos(tempo_pedido_segundos);
			System.out.println(" - Millisegundos: " + tempo_pedido_millisegundos);

			System.out.println(" - Millisegundos 10x mai rapido: " + tempo_pedido_millisegundos / 10);

			lista_tempos.add(tempo_pedido_segundos);
		}
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static long bigDecimalSegundosToLongMillisegundos(BigDecimal tempoBigDecimal) {
		BigDecimal tempoMillisegundos = tempoBigDecimal.multiply(new BigDecimal(1000));
		return tempoMillisegundos.longValue();
	}

}
