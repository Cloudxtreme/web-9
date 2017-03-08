package cn.netkiller;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.netkiller.kafka.consumer.Consumer;
import cn.netkiller.kafka.producer.Producer;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringKafkaApplicationTests {

	public SpringKafkaApplicationTests() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
    private Producer sender;

    @Autowired
    private Consumer receiver;

    @Test
    public void testReceiver() throws Exception {
        sender.sendMessage("helloworld.t", "Hello Spring Kafka!");

        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        assertThat(receiver.getLatch().getCount()).isEqualTo(0);
    }
}
