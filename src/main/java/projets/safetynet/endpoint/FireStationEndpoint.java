package projets.safetynet.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import projets.safetynet.model.core.FireStation;
import projets.safetynet.model.url.FireStationResponse;
import projets.safetynet.service.DataCreateService;
import projets.safetynet.service.DataDeleteService;
import projets.safetynet.service.DataReadService;
import projets.safetynet.service.DataUpdateService;
import projets.safetynet.service.InvalidDeleteFireStationRequestException;
import projets.safetynet.service.LogService;

@RestController
@RequestMapping("/firestation")
public class FireStationEndpoint {

	@Autowired 
	private DataReadService readService;

    @Autowired
    private DataCreateService createService;

    @Autowired
    private DataUpdateService updateService;

    @Autowired
    private DataDeleteService deleteService;

	@GetMapping("")
	public FireStationResponse getFireStationResponse(@RequestParam(value = "stationNumber") long id) {
		FireStationResponse response = readService.getFireStationResponse(id);
		return response;
	}

	@PostMapping("")
	public ResponseEntity<FireStation> postFireStationRequest(@RequestBody FireStation sNew) {
        LogService.logger.info("postFireStationRequest() " + sNew.getAddress() +" & " + sNew.getStation());
        FireStation response = createService.postFireStationRequest(sNew);
	    return new ResponseEntity<FireStation>(response, HttpStatus.CREATED);
	}

	@PutMapping("")
	public ResponseEntity<FireStation> putFireStationRequest(@RequestBody FireStation sNew) {
        LogService.logger.info("putFireStationRequest() " + sNew.getAddress() +" & " + sNew.getStation());
        FireStation response = updateService.putFireStationRequest(sNew);
	    return new ResponseEntity<FireStation>(response, HttpStatus.OK);
	}

	@DeleteMapping("")
	public ResponseEntity<Boolean> deleteFireStationRequest(@RequestBody String request) {
        LogService.logger.info("deleteFireStationRequest() " + request);
        boolean response = false;
		try {
			response = deleteService.deleteFireStationRequest(request);
		} catch (JsonProcessingException | InvalidDeleteFireStationRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return new ResponseEntity<Boolean>(response, HttpStatus.ACCEPTED);
	}

}
