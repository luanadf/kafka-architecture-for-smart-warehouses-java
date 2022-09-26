package service;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import deserializer.PedidoDeserializer;
import model.Pedido;

public class ProcessadorPedidos {

	public static void main(String[] args) {

		Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PedidoDeserializer.class.getName());
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "grupo-consumidor");
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		try (KafkaConsumer<String, Pedido> consumer = new KafkaConsumer<String, Pedido>(properties)) {
			consumer.subscribe(Arrays.asList("topic"));

			while (true) {
				ConsumerRecords<String, Pedido> pedidos = consumer.poll(Duration.ofMillis(200));

				for (ConsumerRecord<String, Pedido> record : pedidos) {
					Pedido pedido = record.value();

					System.out.println(pedido);
				}
			}
		}
	}

}
