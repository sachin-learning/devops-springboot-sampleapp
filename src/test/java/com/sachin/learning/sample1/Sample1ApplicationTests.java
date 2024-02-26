package com.sachin.learning.sample1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Sample1ApplicationTests {

	@Test
	public void SampleTestHappyFlow(){
		assertEquals("Hello".length(), 5);
	}

}
