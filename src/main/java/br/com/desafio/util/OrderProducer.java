package br.com.desafio.util;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import br.com.desafio.model.CustomerOrder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderProducer {

	private static Properties properties() {
	    var properties = new Properties();
	    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
	    properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
	    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
	    return properties;
	}

	public void publishKafka(CustomerOrder customerOrder) throws InterruptedException, ExecutionException {
		var producer = new KafkaProducer<String,CustomerOrder>(properties());
		
		var key = "PEDIDOS";
	    var value = customerOrder;
	    var registro = new ProducerRecord<String, CustomerOrder>("ORDER_TOPICO", key, value);
	    
	    try{
		    Callback callback = (data, ex) -> {
		        if (ex != null) {
		            ex.getMessage();
		            return;
		        }
		        log.info("Mensagem enviada com sucesso para: " + data.topic() + " | partition " + data.partition() + "| offset " + data.offset() + "| tempo " + data.timestamp());
		    };
		    producer.send(registro, callback).get();

	    } finally {
	    	producer.close();
	   }
	}

	public OrderProducer() {
		super();
	}
}