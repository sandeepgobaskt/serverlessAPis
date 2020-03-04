package com.gobaskt.offers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { GobasktserverlessApplication.class })
@WebAppConfiguration
class GobasktserverlessApplicationTests {

	//private MockLambdaContext lambdaContext;
	@Test
	void contextLoads() {
	}

}
