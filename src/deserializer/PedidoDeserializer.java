package deserializer;

import java.io.IOException;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Pedido;

public class PedidoDeserializer implements Deserializer<Pedido> {

	@Override
	public Pedido deserialize(String topic, byte[] pedido) {
		try {
			return new ObjectMapper().readValue(pedido, Pedido.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
