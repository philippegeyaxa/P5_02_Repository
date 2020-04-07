package projets.safetynet.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import projets.safetynet.dao.DuplicateFireStationCreationException;
import projets.safetynet.dao.FireStationNotFoundException;
import projets.safetynet.dao.MultipleFireStationWithSameValuesException;
import projets.safetynet.model.core.FireStation;
import projets.safetynet.model.url.FireStationResponse;
import projets.safetynet.service.DataCreateService;
import projets.safetynet.service.DataDeleteService;
import projets.safetynet.service.DataReadService;
import projets.safetynet.service.DataUpdateService;
import projets.safetynet.service.InvalidDeleteFireStationRequestException;
import projets.safetynet.service.LogService;
import projets.safetynet.service.ServerDataCorruptedException;

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
	public FireStationResponse getFireStationResponse(@RequestParam(value = "stationNumber") long id) 
			throws ServerDataCorruptedException {
	    LogService.logger.debug("getFireStationResponse() " + id);
		FireStationResponse response = readService.getFireStationResponse(id);
		return response;
	}

	@PostMapping("")
	public ResponseEntity<FireStation> postFireStationRequest(@RequestBody FireStation sNew) 
			throws ServerDataCorruptedException, DuplicateFireStationCreationException {
        LogService.logger.debug("postFireStationRequest() " + sNew.getAddress() +" & " + sNew.getStation());
        FireStation response = createService.postFireStationRequest(sNew);
	    return new ResponseEntity<FireStation>(response, HttpStatus.CREATED);
	}

	@PutMapping("")
	public ResponseEntity<FireStation> putFireStationRequest(@RequestBody FireStation sNew) 
			throws FireStationNotFoundException {
        LogService.logger.debug("putFireStationRequest() " + sNew.getAddress() +" & " + sNew.getStation());
        FireStation response = updateService.putFireStationRequest(sNew);
	    return new ResponseEntity<FireStation>(response, HttpStatus.OK);
	}

	@DeleteMapping("")
	public ResponseEntity<Boolean> deleteFireStationRequest(@RequestBody String request) 
			throws FireStationNotFoundException, ServerDataCorruptedException {
        LogService.logger.debug("deleteFireStationRequest() " + request);
        HttpStatus status = HttpStatus.ACCEPTED;
        boolean response = false;
		try {
			response = deleteService.deleteFireStationRequest(request);
			if (! response) status = HttpStatus.NOT_FOUND;
		} catch (JsonProcessingException | InvalidDeleteFireStationRequestException e) {
	        LogService.logger.error("deleteFireStationRequest() bad request " + request);
			status = HttpStatus.BAD_REQUEST;
		}
	    return new ResponseEntity<Boolean>(response, status);
	}

	@ResponseStatus(value=HttpStatus.FORBIDDEN, 
		reason="FireStation already exists !")
	@ExceptionHandler(DuplicateFireStationCreationException.class)
	public void duplicate() {
		return;
	}
	
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, 
			reason="Data corrupted : fix input file and restart server !")
	@ExceptionHandler(ServerDataCorruptedException.class)
	public void internalServerError() {
		return;
	}

	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="FireStation not found !")
	@ExceptionHandler(FireStationNotFoundException.class)
	public void notFound() {
		return;
	}

	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, 
			reason="Unknown error : revert to IT for investigation !")
	@ExceptionHandler(Exception.class)
	public void unknownError() {
		return;
	}
	
}
