package service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import components.LeitorPedidosJson;
import components.ThreadColetaEntregaPedido;
import deserializer.PedidoDeserializer;
import model.Pedido;

public class ProcessadorPedidos {

	static LeitorPedidosJson leitorPedidos = new LeitorPedidosJson("../resources/orders_of_SIMU-i180-o6-r250-dHT03-d52-1.1.json");

	public static void main(String[] args) throws InterruptedException {

		Properties propriedadesConsumidorPedidos = getPropriedadesConsumidorPedidos();

		TransmissorMensagens transmissor = new TransmissorMensagens();
		transmissor.start();

		try (KafkaConsumer<String, Pedido> consumer = new KafkaConsumer<String, Pedido>(propriedadesConsumidorPedidos)) {
			consumer.subscribe(Arrays.asList("topico-pedidos"));

			while (true) {
				// Faz o pool das mensagens a cada 1 segundo: Duration.ofMillis(1000)
				ConsumerRecords<String, Pedido> pedidos = consumer.poll(Duration.ofMillis(1000));

				// Para cada mensagem que ele recebeu durante o pool
				for (ConsumerRecord<String, Pedido> record : pedidos) {
					Pedido pedido = record.value();
					BigDecimal tempo_pedido = leitorPedidos.getTempoByPedidoId(pedido.getIdPedido());

					new ThreadColetaEntregaPedido(pedido, tempo_pedido, transmissor);

				}
			}
		}
	}

	public static Properties getPropriedadesConsumidorPedidos() {
		Properties properties = new Properties();

		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PedidoDeserializer.class.getName());
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "grupo-consumidor");
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

		return properties;
	}

}
