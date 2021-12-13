package nashtech.demo.controller;

import nashtech.demo.service.KafkaSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaSenderService kafkaSenderService;

    @GetMapping("/producer")
    public ResponseEntity<String> producer(@RequestParam("message") String message) {
        kafkaSenderService.send(message);
        return ResponseEntity.ok("Kafka send message successful!");
    }


    @GetMapping("/producer-stream")
    public ResponseEntity<String> producerStream(@RequestParam("message") String message) {
        kafkaSenderService.sendStream(message);
        return ResponseEntity.ok("Kafka send message successful!");
    }

}
