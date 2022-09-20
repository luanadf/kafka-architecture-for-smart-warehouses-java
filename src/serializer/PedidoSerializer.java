package serializer;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Pedido;

public class PedidoSerializer implements Serializer<Pedido> {

	@Override
	public byte[] serialize(String topic, Pedido pedido) {
		try {
			return new ObjectMapper().writeValueAsBytes(pedido);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return null;
	}

}
