package utils;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

public class TesteConsumer {

	public static void main(String[] args) throws InterruptedException {

		Properties propriedadesConsumidorMensagens = getPropriedadesConsumidorMensagens();

		int n_mensagens_recebidas = 0;

		try (KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(propriedadesConsumidorMensagens)) {

			TopicPartition partitionToReadFrom = new TopicPartition("topico-teste", 0);
			consumer.assign(Arrays.asList(partitionToReadFrom));
			consumer.seek(partitionToReadFrom, 0);

			while (true) {
				// Faz o pool das mensagens a cada 0,1 segundo: Duration.ofMillis(100)
				ConsumerRecords<String, String> mensagens = consumer.poll(Duration.ofMillis(100));

				// Para cada mensagem que ele recebeu durante o pool
				for (ConsumerRecord<String, String> record : mensagens) {
					String mensagemFinalizacao = record.value();
					n_mensagens_recebidas++;

					System.out.println("\n Mensagem " + mensagemFinalizacao + " recebida.");
					System.out.println(" - " + n_mensagens_recebidas + " mensagens recebidas.");
				}
			}
		}
	}

	// Cria as propriedades do kafka-consumer
	public static Properties getPropriedadesConsumidorMensagens() {
		Properties properties = new Properties();

		// properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // local
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Arrays.asList("172.17.0.3:9092", "172.17.0.4:9093", "172.17.0.5:9094")); // docker
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "grupo-consumidor-mensagens");
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		return properties;
	}

}
