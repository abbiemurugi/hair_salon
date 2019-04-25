import java.util.List;
import org.sql2o.*;

public class Client {
    private String firstName;
    private String lastName;
    private String gender;
    private int stylistId;
    private int id;

public Client(String firstName, String lastName, String gender, int stylistId){
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        //this.id = id;
        this.stylistId = stylistId;
        }

    public String getfirstName() {
        return firstName;
    }

    public String getlastName() {
        return lastName;
    }

    public String getgender() {
        return gender;
    }

    public int getstylistId() {
        return stylistId;
    }

    public int getId() {
        return id;
    }


    public static List<Client> all(){
        String sql = "SELECT id, firstname FROM clients";
        try(Connection con = DB.sql2o.open()){
            return con.createQuery(sql).executeAndFetch(Client.class );
        }
    }

    @Override
    public boolean equals(Object otherClient) {
        if (!(otherClient instanceof Client)) {
            return false;
        } else {
            Client newClient = (Client) otherClient;
//            return this.getfirstName().equals(newClient.getfirstName()) &&
//                    this.getlastName().equals(newClient.getlastName()) &&
//                    this.getgender().equals(newClient.getgender()) &&
                  return   this.getId() == newClient.getId();
        }
    }

    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO clients (firstname, lastname, gender) VALUES (:firstname, :lastname, :gender)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("firstname", this.firstName)
                    .addParameter("lastname", this.lastName)
                    .addParameter("gender", this.gender)
                    .executeUpdate()
                    .getKey();

        }
    }

    public static Client find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM Clients where id=:id";
            Client Client = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Client.class);
            return Client;
        }
    }

    public void update(String firstname, String lastname, String gender, int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "UPDATE Clients SET firstname = :firstname, lastname = :lastname, gender = :gender WHERE id = :id";
            con.createQuery(sql)
                    .addParameter("firstname", firstname)
                    .addParameter("lastname", lastname)
                    .addParameter("gender", gender)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    public void delete() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM clients WHERE id = :id;";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }
}
