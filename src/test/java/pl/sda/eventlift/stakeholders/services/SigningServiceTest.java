package pl.sda.eventlift.stakeholders.services;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SigningServiceTest {

    @Autowired
    SigningService signingService;

    @Test
    public void shouldRelateStakeholderWithEvent(){
        boolean isRelated = signingService.relateStakeholderWithEvent("przemek2829@wp.pl", "Z598xZQpZa1d1");
        Assertions.assertThat(isRelated).isEqualTo(true);
    }

}