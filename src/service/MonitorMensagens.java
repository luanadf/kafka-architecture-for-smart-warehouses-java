package service;

public class MonitorMensagens {

	/*
	 * public static void main(String[] args) throws InterruptedException {
	 * 
	 * Properties properties = new Properties(); properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	 * properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PedidoDeserializer.class.getName());
	 * properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
	 * properties.put(ConsumerConfig.GROUP_ID_CONFIG, "grupo-consumidor");
	 * properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
	 * 
	 * TransmissorMensagens transmissor = new TransmissorMensagens();
	 * 
	 * try (KafkaConsumer<String, Pedido> consumer = new KafkaConsumer<String, Pedido>(properties)) {
	 * consumer.subscribe(Arrays.asList("topico-pedidos"));
	 * 
	 * while (true) { // Faz o pool das mensagens a cada 1 segundo: Duration.ofMillis(1000) ConsumerRecords<String, Pedido>
	 * pedidos = consumer.poll(Duration.ofMillis(1000));
	 * 
	 * // Para cada mensagem que ele recebeu durante o pool for (ConsumerRecord<String, Pedido> record : pedidos) { Pedido
	 * pedido = record.value(); BigDecimal tempo_pedido = leitorPedidos.getTempoByPedidoId(pedido.getId());
	 * 
	 * new ThreadColetaEntregaPedido(pedido, tempo_pedido, transmissor);
	 * 
	 * } } } }
	 */

}
