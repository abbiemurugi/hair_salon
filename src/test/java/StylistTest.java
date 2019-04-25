import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;


public class StylistTest {

    @Before
    public void setUp() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/to_do_test", "kingcubby", "abbie");
    }

    @After
    public void tearDown() {
        try (Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM stylists *;";
            con.createQuery(sql).executeUpdate();
        }
    }
    
    @Test
    public void return_getFirstName_string(){
        Stylist myStylist = new Stylist("mary", "james", "wanjiku", "mwea", 25, "maryjames@gmail.com");
        assertEquals("mary", myStylist.getfirstName());
    }

    @Test
    public void return_getMiddleName_string(){
        Stylist myStylist = new Stylist("mary", "james", "wanjiku", "mwea", 25, "maryjames@gmail.com");
        assertEquals("james", myStylist.getmiddleName());
    }

    @Test
    public void return_getLastName_string(){
        Stylist myStylist = new Stylist("mary", "james", "wanjiku", "mwea", 25, "maryjames@gmail.com");
        assertEquals("wanjiku", myStylist.getlastName());
    }

    @Test
    public void return_getResidence_string(){
        Stylist myStylist = new Stylist("mary", "james", "wanjiku", "mwea", 25, "maryjames@gmail.com");
        assertEquals("mwea", myStylist.getresidence());
    }

    @Test
    public void return_getAge_Integer(){
        Stylist myStylist = new Stylist("mary", "james", "wanjiku", "mwea", 25, "maryjames@gmail.com");
        assertEquals(22, myStylist.getage());
    }

    @Test
    public void return_getEmail_string(){
        Stylist myStylist = new Stylist("mary", "james", "wanjiku", "mwea", 25, "maryjames@gmail.com");
        assertEquals("maryjames@gmail.com", myStylist.getemail());
    }

    @Test
    public void every_returnsEveryInstanceOfClient_String(){
        Stylist myStylist = new Stylist("mary", "james", "wanjiku", "mwea", 25, "maryjames@gmail.com");
        Client myClient = new Client("john", "mutua", "male", 1);
        myStylist.save();
        myClient.save();
        assertTrue( myStylist.every().contains(myClient));

    }

    @Test
    public void save_returnsTrueIfIdAreTheSame_String() {
        Stylist myStylist = new Stylist("mary", "james", "wanjiku", "mwea", 25, "maryjames@gmail.com");
        myStylist.save();
        assertTrue(Stylist.all().get(0).equals(myStylist));
    }

    @Test
    public void all_returnsAllInstancesOfStylist_true() {
        Stylist firstStylist = new Stylist("mary", "james", "wanjiku", "mwea", 25, "maryjames@gmail.com");
        firstStylist.save();
        Stylist secondStylist = new Stylist("mary", "james", "wanjiku", "mwea", 25, "maryjames@gmail.com");
        secondStylist.save();
        assertEquals(true, Stylist.all().get(0).equals(firstStylist));
        assertEquals(true, Stylist.all().get(1).equals(secondStylist));
    }

    @Test
    public void save_assignsIdToObject() {
        Stylist myStylist = new Stylist("mary", "james", "wanjiku", "mwea", 25, "maryjames@gmail.com");
        myStylist.save();
        Stylist savedStylist = Stylist.all().get(0);
        assertEquals(myStylist.getId(), savedStylist.getId());
    }

    @Test
    public void getId_StylistsInstantiateWithAnID() {
        Stylist myStylist = new Stylist("mary", "james", "wanjiku", "mwea", 25, "maryjames@gmail.com");
        myStylist.save();
        assertTrue(myStylist.getId() > 0);
    }

    @Test
    public void find_returnsStylistWithSameId_secondStylist() {
        Stylist firstStylist = new Stylist("mary", "james", "wanjiku", "mwea", 25, "maryjames@gmail.com");
        firstStylist.save();
        Stylist secondStylist = new Stylist("mary", "james", "wanjiku", "mwea", 25, "maryjames@gmail.com");
        secondStylist.save();
        assertEquals(Stylist.find(secondStylist.getId()), secondStylist);
    }

    @Test
    public void update_updatesStylistDescription_true() {
        Stylist myStylist = new Stylist("joy", "stone", "junior", "nairobi", 22, "joystone@gmail.com");
        myStylist.save();
        myStylist.update("joy", "stone", "junior", "nairobi", 22, "joystone@gmail.com",1);
        assertEquals("joy", Stylist.find(myStylist.getId()).getfirstName());
    }

    @Test
    public void delete_deletesStylist_true() {
        Stylist myStylist = new Stylist("joy", "stone", "junior", "nairobi", 22, "joystone@gmail.com");
        myStylist.save();
        int myStylistId = myStylist.getId();
        myStylist.delete();
        assertEquals(null, Stylist.find(myStylistId));
    }
}
