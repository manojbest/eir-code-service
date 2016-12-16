package com.eircode;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest("eureka.client.enabled:false")
public abstract class AbstractTest {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

}
