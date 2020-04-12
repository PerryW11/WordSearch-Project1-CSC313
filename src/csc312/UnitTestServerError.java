package csc312;

import static org.junit.Assert.*;

import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class UnitTestServerError {

	/**
	 * Unit test the SC_INTERNAL_SERVER_ERROR
	 */
	@Test
	public void testInteralError() {
		String query = "https://wordfinder-001.appspot.com/wordfinder?game=1&pos=Z99";

		try {
			HttpURLConnection con = (HttpURLConnection) new URL(query).openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			assertEquals(con.getResponseCode(), HttpURLConnection.HTTP_INTERNAL_ERROR);
		} catch (Exception e) {
			fail("Cannot reach server.");
		}
	}

	/**
	 * Unit test that the SC_FORBIDDEN
	 */
	@Test
	public void testForbiddenError() {
		String query = "https://wordfinder-001.appspot.com/wordfinder?game=1&pos=Z88";

		try {
			HttpURLConnection con = (HttpURLConnection) new URL(query).openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			assertEquals(con.getResponseCode(), HttpURLConnection.HTTP_FORBIDDEN);
		} catch (Exception e) {
			fail("Cannot reach server.");
		}
	}
}
