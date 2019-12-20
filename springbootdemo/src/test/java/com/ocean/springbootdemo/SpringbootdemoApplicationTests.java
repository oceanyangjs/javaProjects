package com.ocean.springbootdemo;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.constraints.AssertTrue;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SpringbootdemoApplicationTests {

	@Test
	public void contextLoads() {
        assertEquals(2, 100,4);
//        assertTrue(2 == 3, () -> "Assertion messages can be lazily evaluated -- "
//                + "to avoid constructing complex messages unnecessarily.");
	}

}
