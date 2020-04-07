package projets.safetynet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import projets.safetynet.dao.FireStationDao;
import projets.safetynet.dao.MedicalRecordDao;
import projets.safetynet.dao.PersonDao;
import projets.safetynet.model.core.FireStation;
import projets.safetynet.model.core.MedicalRecord;
import projets.safetynet.model.core.Person;

@SpringBootTest
public class DataCreateServiceTest {

	private Person p1 = new Person("f1", "l1", "a1", "c1", 11111L, "t1", "e1");
	private FireStation s1 = new FireStation( "a1", 1 );
	String[] medications1 = new String[] {};
	String[] allergies1 = new String[] {"allergy 1 a", "allergy 1 b", "allergy 1 c", "allergy 1 d"};
	Date date1 = Date.valueOf("1001-01-01");
	private MedicalRecord m1 = new MedicalRecord ("f1","l1",date1,medications1,allergies1);

	@Autowired
	private DataCreateService service;
	
	@MockBean
	private PersonDao personDao;

	@MockBean
	private FireStationDao stationDao;

	@MockBean
	private MedicalRecordDao recordDao;

	@BeforeEach
	private void initTestData()
	{
		try {
			when(personDao.save(p1)).thenReturn(p1);
			when(stationDao.save(s1)).thenReturn(s1);
			when(recordDao.save(m1)).thenReturn(m1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void givenNewP1_postPersonRequest_savesP1() throws Exception
	{
		// GIVEN
		// Test data prepared in initTestData
		// WHEN
		Person response = service.postPersonRequest(p1);
		// THEN
		assertEquals("f1", response.getFirstName());
		assertEquals("l1", response.getLastName());
	}

	@Test
	void givenNewS1_postFireStationRequest_savesS1() throws Exception
	{
		// GIVEN
		// Test data prepared in initTestData
		// WHEN
		FireStation response = service.postFireStationRequest(s1);
		// THEN
		assertEquals("a1", response.getAddress());
		assertEquals(1, response.getStation());
	}

	@Test
	void givenNewM1_postMedicalRecordRequest_savesM1() throws Exception
	{
		// GIVEN
		// Test data prepared in initTestData
		// WHEN
		MedicalRecord response = service.postMedicalRecordRequest(m1);
		// THEN
		assertEquals("f1", response.getFirstName());
		assertEquals("l1", response.getLastName());
		assertEquals(date1, response.getBirthdate());
	}

}
