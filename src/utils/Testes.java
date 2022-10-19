package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

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

		/*
		 * String static_filepath = "../resources/time_of_orders_of_SIMU-i180-o6-r250-dHT03-d52-1.1.json";
		 * 
		 * InputStream is = LeitorPedidosJson.class.getResourceAsStream(static_filepath); if (is == null) { throw new
		 * NullPointerException("Cannot find resource file " + static_filepath); }
		 * 
		 * JSONTokener tokener = new JSONTokener(is);
		 * 
		 * JSONArray array_tempos_pedidos_json = new JSONArray(tokener);
		 * 
		 * ArrayList<BigDecimal> lista_tempos = new ArrayList<BigDecimal>();
		 * 
		 * for (Object tempo : array_tempos_pedidos_json) { System.out.println("\nTempo do pedido:");
		 * 
		 * BigDecimal tempo_pedido_segundos = (BigDecimal) tempo; System.out.println(" - Segundos: " + tempo_pedido_segundos);
		 * 
		 * long tempo_pedido_millisegundos = bigDecimalSegundosToLongMillisegundos(tempo_pedido_segundos);
		 * System.out.println(" - Millisegundos: " + tempo_pedido_millisegundos);
		 * 
		 * System.out.println(" - Millisegundos 10x mai rapido: " + tempo_pedido_millisegundos / 10);
		 * 
		 * lista_tempos.add(tempo_pedido_segundos); }
		 */

		// Teste script de monitoramento

		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Arrays.asList("172.17.0.3:9092", "172.17.0.4:9093", "172.17.0.5:9094"));
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		properties.put(ProducerConfig.ACKS_CONFIG, "0");
		properties.put(ProducerConfig.RETRIES_CONFIG, "0");

		int count = 0;
		int mensagens_enviadas = 0;

		try (KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties)) {
			while (count < 5000) {
				ProducerRecord<String, String> record = new ProducerRecord<String, String>("teste", "mensagem-teste");
				producer.send(record);
				// System.out.println(record.value());
				mensagens_enviadas++;
				Thread.sleep(10);
				count++;
			}
		}

		System.out.println(mensagens_enviadas + " mensagens enviadas.");

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
