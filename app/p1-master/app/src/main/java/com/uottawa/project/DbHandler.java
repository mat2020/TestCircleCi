package com.uottawa.project;




import java.util.List;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class DbHandler {

    public DbHandler (){
                }

    public Account getData(String username, List<Account> users){
            //These two lines showed up in the merge but they aren't in my copy, not sure if they are supposed to be here
            //@Override
            //public void onCancelled(DatabaseError databaseError){

            for (int i = 0; i< users.size(); i++){
                if (users.get(i).getUsername().equals(username)){
                    return users.get(i);
                }
            }

        return null;
    }

    public boolean exists(String data, String dataToSerach,List<Account> users){
        if(data == null){
            return (false);
        }
        if (dataToSerach.equals("Username")){
            for (int i = 0; i< users.size(); i++){
                if (users.get(i).getUsername().equals(data)){
                    return true;
                }
            }
        }
        else if (dataToSerach.equals("Email")){
            for (int i = 0; i< users.size(); i++){
                if (users.get(i).getEmail() != null && users.get(i).getEmail().equals(data)){
                    return true;
                }
            }
        }
        return false;
    }


    public void add(Account account, DatabaseReference database ) {
        String id = database.push().getKey();
        account.setID(id);
        database.child(id).setValue(account);
    }


    public void addAdmin(DatabaseReference database,List<Account> users ) {

        if (!exists("admin", "Username",users)) {
            Account newAccount = new Admin("36e0d001bb89f979bf685399fea558db3c269155a02c77416fa06d79c03f3d39", "admin", "ADMIN", "ADMIN", "no@noo.ca");
            this.add(newAccount, database);
        }
    }
}
