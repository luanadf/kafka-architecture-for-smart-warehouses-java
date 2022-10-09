package service;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import deserializer.MensagemFinalizacaoDeserializer;
import model.MensagemFinalizacao;

public class MonitorMensagens {

	public static void main(String[] args) throws InterruptedException {

		Properties propriedadesConsumidorMensagens = getPropriedadesConsumidorMensagens();

		try (KafkaConsumer<String, MensagemFinalizacao> consumer = new KafkaConsumer<String, MensagemFinalizacao>(propriedadesConsumidorMensagens)) {
			consumer.subscribe(Arrays.asList("topico-monitoramento"));

			while (true) {
				// Faz o pool das mensagens a cada 0,1 segundo: Duration.ofMillis(100)
				ConsumerRecords<String, MensagemFinalizacao> mensagens = consumer.poll(Duration.ofMillis(100));

				// Para cada mensagem que ele recebeu durante o pool
				for (ConsumerRecord<String, MensagemFinalizacao> record : mensagens) {
					MensagemFinalizacao mensagemFinalizacao = record.value();

					System.out.println("Pedido " + mensagemFinalizacao.getIdPedido() + " finalizado: " + mensagemFinalizacao);
				}
			}
		}
	}

	// Cria as propriedades do kafka-consumer
	public static Properties getPropriedadesConsumidorMensagens() {
		Properties properties = new Properties();

		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, MensagemFinalizacaoDeserializer.class.getName());
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "grupo-consumidor-2");
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

		return properties;
	}

}
