package com.invitationService.invitationService;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.invitationService.tokenmaster.TokenService;

public class TokenServiceTest {

	private TokenService ts;
	
	@Before
	public void init() {
		ts = new TokenService();
		ReflectionTestUtils.setField(ts, "key", "secret");
		
		
	}
	
	@Test
	public void testeTestTokenService() {
		
		Assert.assertEquals(TokenService.class, ts.getClass());
	}
	
	@Test
	public void testeTokenParsing() {
		
		String testemail = "test@example.de";
		System.out.println(testemail);
		String token = ts.createUserJWT("", "IS", "invitation", testemail, "42");
		System.out.println(token);
		assertEquals(testemail, ts.parseJWT(token));
		
	}
	
	@Test
	public void testeCreatorJWTCreation() {
		
		String testemail = "creator@example.de";
		System.out.println(testemail);
		String token = ts.createCreatorJWT("", "IS", "invitation", testemail);
		System.out.println(token);
		assertEquals(testemail, ts.parseJWT(token));
		
		
		
	}
}
