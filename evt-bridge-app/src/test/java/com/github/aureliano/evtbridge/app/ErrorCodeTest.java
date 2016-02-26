package com.github.aureliano.evtbridge.app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.aureliano.evtbridge.app.ErrorCode;

public class ErrorCodeTest {

	@Test
	public void testGetCode() {
		Integer expected = 100;
		Integer actual = ErrorCode.COMMAND_NOT_FOUND.getCode();
		
		assertEquals(expected, actual);
	}
}