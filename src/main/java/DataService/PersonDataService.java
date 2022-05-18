package DataService;

import DataModel.Person;
import DataSource.MySQL;

import java.util.ArrayList;

public class PersonDataService {
    private MySQL dbh = new MySQL();

    public ArrayList<Person> getAllPersons(){
        return dbh.getALLPersons();
    }

    public Person addPerson(Person person ){
        return dbh.addPerson(person);
    }

    public Person getonePerson(int persId) {return dbh.getPersonById(persId);}

    public Person updatePerson(Person person, int persId) {return dbh.updatePerson(person,persId);}

    public String deletedPerson(int persid) { return dbh.deletedPerson(persid);}
}
