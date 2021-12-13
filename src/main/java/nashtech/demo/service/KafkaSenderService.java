package nashtech.demo.service;

public interface KafkaSenderService {

    void send(String message);

    void sendStream(String message);
}
