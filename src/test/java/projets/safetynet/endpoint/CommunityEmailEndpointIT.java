package projets.safetynet.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class CommunityEmailEndpointIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void givenAddress_whenGetCommunityEmailEndPoint_thenReturnsExpectedResponse() throws Exception {
		// GIVEN
		// Test data provided by test.json
		// WHEN & THEN
		String responseString = mockMvc.perform(get("/communityEmail?city=TestCity"))
				.andReturn().getResponse().getContentAsString();
		ArrayList response = objectMapper.readValue(responseString, ArrayList.class);
		// THEN
		assertEquals(5, response.size());
	}

}