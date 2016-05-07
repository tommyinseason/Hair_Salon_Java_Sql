import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class StylistTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteClientQuery = "DELETE FROM clients *;";
      String deleteStylistQuery = "DELETE FROM stylists *;";
      con.createQuery(deleteClientQuery).executeUpdate();
      con.createQuery(deleteStylistQuery).executeUpdate();
    }
  }

  @Test
  public void stylist_instantiatesCorrectly_true() {
    Stylist myStylist = new Stylist("Mary");
    assertEquals(true, myStylist instanceof Stylist);
  }

  @Test
  public void getName_StylistInstantiatesWithName_String() {
    Stylist myStylist = new Stylist("Mary");
    assertEquals("Mary", myStylist.getName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Stylist.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Stylist firstStylist = new Stylist("Mary");
    Stylist secondStylist = new Stylist("Mary");
    assertTrue(firstStylist.equals(secondStylist));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Stylist myStylist = new Stylist("Mary");
    myStylist.save();
    assertTrue(Stylist.all().get(0).equals(myStylist));
  }
  @Test
  public void save_assignsIdToObject() {
    Stylist myStylist = new Stylist("Mary");
    myStylist.save();
    Stylist savedStylist = Stylist.all().get(0);
    assertEquals(myStylist.getId(), savedStylist.getId());
  }
  @Test
  public void find_findStylistInDatabase_true() {
    Stylist myStylist = new Stylist("Mary");
    myStylist.save();
    Stylist savedStylist = Stylist.find(myStylist.getId());
    assertTrue(myStylist.equals(savedStylist));
  }

  @Test
  public void getClients_retrievesAllClientsFromDataBase_Clients() {
    Stylist myStylist = new Stylist("Mary");
    myStylist.save();

    System.out.println("myStylistid " + myStylist.getId());

    Client firstClient = new Client("Mary", myStylist.getId());
    firstClient.save(); //name, c id, location, price
    Client secondClient = new Client("Mark", myStylist.getId());
    secondClient.save();

    System.out.println("myStylist Clients " + myStylist.getClients());
    Client[] clients = new Client[] { firstClient, secondClient };
    assertTrue(myStylist.getClients().containsAll(Arrays.asList(Clients)));
  }
}
