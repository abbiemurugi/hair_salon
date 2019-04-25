import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;


public class ClientTest {

    @Before
    public void setUp() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", "kingcubby", "abbie");
    }

    @After
    public void tearDown() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM clients *;";
            con.createQuery(sql).executeUpdate();
        }
    }

    @Test
    public void get_theFirstName_String(){
        Client myClient = new Client("john", "mutua", "male", 1);
        assertEquals("john", myClient.getfirstName());
    }

    @Test
    public void get_theLastName_String(){
        Client myClient = new Client("john", "mutua", "male", 1);
        assertEquals("mutua", myClient.getlastName());
    }

    @Test
    public void get_theGender_String(){
        Client myClient = new Client("john", "mutua", "male", 1);
        assertEquals("male", myClient.getgender());
    }

    @Test
    public void save_returnsTrueIfIdAreTheSame_String() {
        Client myClient = new Client("john", "mutua", "male", 1);
        myClient.save();
        assertTrue(Client.all().get(0).equals(myClient));
    }

    @Test
    public void all_returnsAllInstancesOfClient_true() {
        Client firstClient = new Client("john", "mutua", "male", 1);
        firstClient.save();
        Client secondClient = new Client("christian", "grey", "female", 1);
        secondClient.save();
        assertEquals(true, Client.all().get(0).equals(firstClient));
        assertEquals(true, Client.all().get(1).equals(secondClient));
    }

    @Test
    public void save_assignsIdToObject() {
        Client myClient = new Client("john", "mutua", "male", 1);
        myClient.save();
        Client savedClient = Client.all().get(0);
        assertEquals(myClient.getId(), savedClient.getId());
    }

    @Test
    public void getId_ClientsInstantiateWithAnID() {
        Client myClient = new Client("john", "mutua", "male", 1);
        myClient.save();
        assertTrue(myClient.getId() > 0);
    }

    @Test
    public void find_returnsClientWithSameId_secondClient() {
        Client firstClient = new Client("john", "mutua", "male", 1);
        firstClient.save();
        Client secondClient = new Client("christian", "grey", "female", 1);
        secondClient.save();
        assertEquals(Client.find(secondClient.getId()), secondClient);
    }

    @Test
    public void update_updatesClientDescription_true() {
        Client myClient = new Client("caleb", "james", "male", 1);
        myClient.save();
        myClient.update("caleb", "james", "male", 1);
        assertEquals("caleb", Client.find(myClient.getId()).getfirstName());
    }

    @Test
    public void delete_deletesClient_true() {
        Client myClient = new Client("john", "mutua", "male", 1);
        myClient.save();
        int myClientId = myClient.getId();
        myClient.delete();
        assertEquals(null, Client.find(myClientId));
    }
}
