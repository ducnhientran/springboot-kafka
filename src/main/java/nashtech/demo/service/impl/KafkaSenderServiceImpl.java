package nashtech.demo.service.impl;

import lombok.RequiredArgsConstructor;
import nashtech.demo.service.KafkaSenderService;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaSenderServiceImpl implements KafkaSenderService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public static final String INPUT_TOPIC_NAME = "number-topic";

    private String kafkaTopic = "mytopic";
    private String hobbitTopic = "hobbit";

    @Override
    public void send(String message) {
        System.out.println("Producer :" + message);
        TopicBuilder.name("sas").partitions(5).replicas(4);
        for (int i = 0; i < 100; i++ ) {
            kafkaTemplate.send(kafkaTopic, message+" "+i);
//            kafkaTemplate.send(new ProducerRecord<>(kafkaTopic, i,String.valueOf(i),message+" "+i ));
        }

        for (int i = 0; i < 100; i++ ) {
            kafkaTemplate.send(hobbitTopic, message+" "+i);
        }
    }

    @Override
    public void sendStream(String message) {
        for (int i = 0; i < 100; i++ ) {
            String s = "Odd";
            if (i % 2 == 0) s = "Even";
            kafkaTemplate.send(INPUT_TOPIC_NAME,s, String.valueOf(i));
        }
    }
}
