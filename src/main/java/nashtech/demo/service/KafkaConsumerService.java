package nashtech.demo.service;

public interface KafkaConsumerService {

    void consumerFromTopic(String message);

    void consumerFromHobbitTopic(String message);


}
