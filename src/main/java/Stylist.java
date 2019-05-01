import java.util.List;
import org.sql2o.*;


public class Stylist {
    private String firstName;
    private String middleName;
    private String lastName;
    private String residence;
    private int age;
    private String email;
    private int id;

    public Stylist(String firstName, String middleName, String lastName, String residence, int age, String email){
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.residence = residence;
        this.age = age;
        this.email= email;
    }

    public String getfirstName() {
        return firstName;
    }

    public String getmiddleName() {
        return middleName;
    }

    public String getlastName() {
        return lastName;
    }

    public String getresidence() {
        return residence;
    }

    public int getage() {
        return age;
    }

    public String getemail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public static List<Stylist> all() {
        String sql = "SELECT * FROM stylists";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Stylist.class);
        }
    }

    public  List<Client> every() {
        String sql = "SELECT * FROM clients WHERE stylistid = :id";
        try (Connection con = DB.sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id",this.id)
                    .executeAndFetch(Client.class);
        }
    }

    @Override
    public boolean equals(Object otherStylist) {
        if (!(otherStylist instanceof Stylist)) {
            return false;
        } else {
            Stylist newStylist = (Stylist) otherStylist;
//            return this.getfirstName().equals(newStylist.getfirstName()) &&
//                    this.getlastName().equals(newStylist.getlastName()) &&
//                    this.getgender().equals(newStylist.getgender()) &&
            return   this.getId() == newStylist.getId();
        }
    }

    public void save() {
        try(Connection con = DB.sql2o.open())  {
            String sql = "INSERT INTO stylists (firstname, middlename, lastname, residence, age, email) VALUES (:firstname, :middlename, :lastname, :residence, :age, :email )";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("firstname", this.firstName)
                    .addParameter("middlename", this.middleName)
                    .addParameter("lastname",this.lastName)
                    .addParameter("residence", this.residence)
                    .addParameter("age", this.age)
                    .addParameter("email",this.email)
                    .executeUpdate()
                    .getKey();
        }
    }

    public static Stylist find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM Stylists where id=:id";
            Stylist Stylist = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Stylist.class);
            return Stylist;
        }
    }

    public void update(String firstname, String middlename, String lastname, String residence, int age, String email, int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "UPDATE Stylists SET firstname = :firstname, middlename = :middlename, lastname = :lastname, residence = :residence, age = :age, email = :email WHERE id = :id";
            con.createQuery(sql)
                    .addParameter("firstname", firstname)
                    .addParameter("middlename", middlename)
                    .addParameter("lastname", lastname)
                    .addParameter("residence", residence)
                    .addParameter("age", age)
                    .addParameter("email", email)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    public void delete() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM Stylists WHERE id = :id;";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }
}
