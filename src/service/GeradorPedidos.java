package service;

import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import model.Pedido;
import serializer.PedidoSerializer;

public class GeradorPedidos {

	private static Random rand = new Random();
	private static long operacao = 0;

	public static void main(String[] args) throws InterruptedException {
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PedidoSerializer.class.getName());

		try (KafkaProducer<String, Pedido> producer = new KafkaProducer<String, Pedido>(properties)) {
			while (true) {

				// TODO Adaptar para modelo do George
				Pedido pedido = gerarPedido();

				ProducerRecord<String, Pedido> record = new ProducerRecord<String, Pedido>("topico-pedidos", pedido);
				System.out.println(record.value());
				producer.send(record);

				// TODO Adaptar para modelo do George
				// Tempo de chegada dos pedidos 1 a cada 5 segundos
				Thread.sleep(5000);
			}
		}
	}

	private static Pedido gerarPedido() {
		Integer n_itens = rand.nextInt(10) + 1;
		Integer warehouse_block = rand.nextInt(6) + 1;

		Pedido pedido = new Pedido(operacao++, n_itens, warehouse_block);

		return pedido;
	}

}
