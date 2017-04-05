package test;

import static org.junit.Assert.*;

import org.junit.Test;

import util.StringUtil;

public class TestTest {

	@Test
	public void testName() throws Exception {

		assertFalse(StringUtil.checkIfEmpty("asdf"));

	}

}
