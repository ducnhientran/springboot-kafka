package nashtech.demo.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafkaStreams
public class KafkaConfiguration {

    public static final String OUTPUT_TOPIC_NAME = "even-number-topic";
    public static final String INPUT_TOPIC_NAME = "number-topic";

    @Bean
    NewTopic hobbit(){
        return TopicBuilder.name("hobbit").partitions(12).replicas(1).build();
    }

    @Bean
    public KStream<String, String> evenNumbersStream(StreamsBuilder kStreamBuilder) {
        KStream<String, String> input = kStreamBuilder.stream(INPUT_TOPIC_NAME);
        KStream<String, String> output = input.filter((key, value) -> key.equals("Even"));
        output.to(OUTPUT_TOPIC_NAME);
        return output;
    }
}
