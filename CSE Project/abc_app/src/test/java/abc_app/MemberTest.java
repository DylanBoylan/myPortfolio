package abc_app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberTest {

	private Member member;

	@BeforeEach
	public void setUp() throws Exception {
		member = new Member("Gupta", "sanayagupta@tus.ie", "SGupta");
	}

	@Test
	public void testConstructor() {
		assertEquals("Gupta", member.getCompany());
		assertEquals("sanayagupta@tus.ie", member.getEmail());
		assertEquals("SGupta", member.getUserName());
	}

	@Test
	public void testGetCompany() {
		assertEquals("Gupta", member.getCompany());
	}

	@Test
	public void testSetCompany() {
		member.setCompany("GUPTA");
		assertEquals("GUPTA", member.getCompany());
	}

	@Test
	public void testGetEmail() {
		assertEquals("sanayagupta@tus.ie", member.getEmail());
	}

	@Test
	public void testSetEmail() {
		member.setEmail("SanayaGupta@tus.ie");
		assertEquals("SanayaGupta@tus.ie", member.getEmail());
	}

	@Test
	public void testGetUserName() {
		assertEquals("SGupta", member.getUserName());
	}

	@Test
	public void testSetUserName() {
		member.setUserName("SGUPTA");
		assertEquals("SGUPTA", member.getUserName());
	}

	@Test
	public void testToString() {
		assertEquals("Member{" + "company='" + member.getCompany() + '\'' + ", email='" + member.getEmail() + '\''
				+ ", userName='" + member.getUserName() + '\'' + '}', member.toString());
	}

}