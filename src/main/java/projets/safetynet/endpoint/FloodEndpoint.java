package projets.safetynet.endpoint;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import projets.safetynet.model.url.FloodAddressResponse;
import projets.safetynet.service.DataReadService;

@RestController
@RequestMapping("/flood")
public class FloodEndpoint {

	@Autowired 
	private DataReadService service;

	@GetMapping("/stations")
	public ArrayList<FloodAddressResponse> getFloodByStationResponse(@RequestParam(value = "stations") ArrayList<Long> stations) {
		ArrayList<FloodAddressResponse> response = service.getFloodByStationResponse(stations);
		return response;
	}

}