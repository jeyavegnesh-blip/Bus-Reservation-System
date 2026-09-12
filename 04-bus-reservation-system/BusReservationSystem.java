import java.sql.*;
import java.util.Scanner;

public class BusReservationSystem {
    static final String URL="jdbc:mysql://localhost:3306/bus_db";
    static final String USER="root";
    static final String PASS="YOUR_MYSQL_PASSWORD";

    public static void main(String[] args) {
        try(Connection con=DriverManager.getConnection(URL,USER,PASS);
            Scanner sc=new Scanner(System.in)) {

            System.out.println("=== BUS RESERVATION SYSTEM ===");
            System.out.print("Bus ID: "); int busId=sc.nextInt(); sc.nextLine();
            System.out.print("Passenger name: "); String name=sc.nextLine();
            System.out.print("Phone: "); String phone=sc.nextLine();
            System.out.print("Seat number: "); int seat=sc.nextInt();

            con.setAutoCommit(false);
            try {
                PreparedStatement check=con.prepareStatement(
                    "SELECT available_seats FROM buses WHERE id=? FOR UPDATE");
                check.setInt(1,busId);
                ResultSet rs=check.executeQuery();
                if(!rs.next() || rs.getInt(1)<=0) throw new Exception("No seats available");

                PreparedStatement insert=con.prepareStatement(
                    "INSERT INTO reservations(bus_id,passenger_name,phone,seat_no) VALUES(?,?,?,?)");
                insert.setInt(1,busId); insert.setString(2,name);
                insert.setString(3,phone); insert.setInt(4,seat);
                insert.executeUpdate();

                PreparedStatement update=con.prepareStatement(
                    "UPDATE buses SET available_seats=available_seats-1 WHERE id=?");
                update.setInt(1,busId); update.executeUpdate();

                con.commit();
                System.out.println("Reservation successful!");
            } catch(Exception e) {
                con.rollback();
                System.out.println("Reservation failed: "+e.getMessage());
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}