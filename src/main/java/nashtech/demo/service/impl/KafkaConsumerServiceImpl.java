package nashtech.demo.service.impl;

import nashtech.demo.service.KafkaConsumerService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    public static final String OUTPUT_TOPIC_NAME = "even-number-topic";
    public static final String INPUT_TOPIC_NAME = "number-topic";

    @Override
    @KafkaListener(topics = "mytopic", groupId = "mygroup")
    public void consumerFromTopic(String message) {
        System.out.println("Consumer :"+ message);
    }

    @Override
    @KafkaListener(topics = "hobbit")
    public void consumerFromHobbitTopic(String message) {

        System.out.println("Consumer hobbit :"+ message);
    }

    @KafkaListener(topics = OUTPUT_TOPIC_NAME)
    public void receive(String value) {
        System.out.println("Received number at OUTPUT_TOPIC_NAME: " + value);
    }

    @KafkaListener(topics = INPUT_TOPIC_NAME)
    public void receiveInput(String value) {
        System.out.println("Received number at INPUT_TOPIC_NAME: " + value);
    }


}
