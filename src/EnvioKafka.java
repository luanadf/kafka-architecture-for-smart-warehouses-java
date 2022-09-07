import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class EnvioKafka {
	public static void main(String[] args) {
		
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		// Cria um producer que tem chave e valor do tipo strings
		try (KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {
			// Produtor de uma mensagem
			ProducerRecord<String, String> record = new ProducerRecord<String, String>("testejava", "Olá mundo!");
			producer.send(record);
		}

	}
}
