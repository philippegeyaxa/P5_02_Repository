package projets.safetynet.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import projets.safetynet.model.core.FireStation;
import projets.safetynet.service.LogService;

@Repository
public class FireStationDao {

	private ArrayList<FireStation> stations;

	public FireStationDao() {
	}

	public FireStationDao(ArrayList<FireStation> stations) {
		LogService.logger.debug("FireStationDao() size = " + stations.size());
		this.stations = new ArrayList<FireStation>();
		for (FireStation s: stations) {
			save(s);
		}		
	}

	public void set(ArrayList<FireStation> stations) {
		this.stations = new FireStationDao(stations).stations;
	}
	
    public void save(FireStation s)
    {
		LogService.logger.debug("save() " + s.getAddress() + " & " + s.getStation());
    	FireStation sNew = new FireStation(s.getAddress(), s.getStation());
    	stations.add(sNew);
    }

	public ArrayList<FireStation> getAll() {
		LogService.logger.debug("getAll() size = " + stations.size());
    	return stations;
	}

	public FireStation getByAddress(String address) throws FireStationNotFoundException {
		LogService.logger.debug("getByAddress() " + address);
		for (FireStation s: stations) {
			if (s.getAddress().equals(address)) return s;
		}
		LogService.logger.error("getByAddress() returns FireStationNotFoundException");
		throw new FireStationNotFoundException();
	}

	public FireStation getByStation(long station) throws FireStationNotFoundException {
		LogService.logger.debug("getByStation() " + station);
		for (FireStation s: stations) {
			if (s.getStation() == station) return s;
		}
		LogService.logger.error("getByStation() returns FireStationNotFoundException");
		throw new FireStationNotFoundException();
	}

	public void updateByAddress(FireStation sNew) throws FireStationNotFoundException {
		LogService.logger.debug("updateByAddress() " + sNew.getAddress());
		for (FireStation s: stations) {
			if (s.getAddress().equals(sNew.getAddress())) {
				s.setStation(sNew.getStation());
				return;
			}
		}
		LogService.logger.error("updateByAddress() returns FireStationNotFoundException");
    	throw new FireStationNotFoundException();
	}

	public void updateByStation(FireStation sNew) throws FireStationNotFoundException {
		LogService.logger.debug("updateByStation() " + sNew.getStation());
		for (FireStation s: stations) {
			if (s.getStation() == sNew.getStation()) {
				s.setAddress(sNew.getAddress());
				return;
			}
		}
		LogService.logger.error("updateByStation() returns FireStationNotFoundException");
    	throw new FireStationNotFoundException();
	}

	public void delete(FireStation s) // Does not throw any exception if the station is not found
	{
		LogService.logger.debug("delete() " + s.getAddress() + " or " + s.getStation());
		stations.removeIf( station -> station.getAddress().equals(s.getAddress()) ||
    			station.getStation() == s.getStation() );
	}
	
}
