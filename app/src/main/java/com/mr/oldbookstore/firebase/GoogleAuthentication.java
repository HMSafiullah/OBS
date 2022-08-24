package com.mr.oldbookstore.firebase;

import static android.provider.Settings.System.getString;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mr.oldbookstore.R;
import com.mr.oldbookstore.Utils.Utils;
import com.mr.oldbookstore.activity.DashboardActivity;
import com.mr.oldbookstore.activity.MainActivity;
import com.mr.oldbookstore.activity.ProfileActivity;
import com.mr.oldbookstore.model.ModelUsersData;

import java.util.ArrayList;

public class GoogleAuthentication {
    /*private EditText username, password;
    private String user, pass;
    private FirebaseAuth mAuth;
    private ProgressDialog pd;*/

    private static Boolean[] check= {true};;
    private static final String TAG = "MainActivity";
    private static String name, email;
    private static GoogleSignInClient mSignInClient;
    private static String idToken;
    private static FirebaseAuth firebaseAuth;
    private static FirebaseAuth.AuthStateListener authStateListener;
    Button button;
    //ModelUsersData modelUsersData;
    private DatabaseReference database;
    private DatabaseReference databaseReference;
    private ModelUsersData modelUsersData;
    private DatabaseReference blockedDatabaseReference;
    private String blockedID="";
    private ModelUsersData blockedUsers;
    private ArrayList<ModelUsersData> blockeduserList;
    //private DatabaseReference myRef;

    public GoogleAuthentication(FirebaseAuth firebaseAuth,FirebaseAuth.AuthStateListener authStateListener,Activity activity,GoogleSignInClient mSignInClient){
        blockeduserList=new ArrayList<>();
        /*username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        Button registerButton = (Button) findViewById(R.id.registerButton);
        TextView login = (TextView) findViewById(R.id.login);*/
        /*pd = new ProgressDialog(this);*/





        database= FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        databaseReference= FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Users");
        blockedDatabaseReference= FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Blocked");
        if(blockedDatabaseReference!=null) {
            blockedDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        blockedUsers=dataSnapshot.getValue(ModelUsersData.class);
                        if(blockedUsers!=null)
                        blockeduserList.add(blockedUsers);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        //modelUsersData=new ModelUsersData();
        firebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
        //this is where we start the Auth state Listener to listen for whether the user is signed in or not
        authStateListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // Get signedIn user
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //if user is signed in, we call a helper method to save the user details to Firebase
                if (user != null) {
                    // User is signed in
                    // you could place other firebase code
                    //logic to save the user details to Firebase
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("43581612461-q1ikjfgb5qmssk4dajhf3ahnvnevsr3u.apps.googleusercontent.com")//you can also use R.string.default_web_client_id
                .requestEmail()
                .build();

        mSignInClient = GoogleSignIn.getClient(activity, gso);

        this.firebaseAuth=firebaseAuth;
        this.authStateListener=authStateListener;
        this.mSignInClient=mSignInClient;
        /*googleApiClient=new GoogleApiClient.Builder(activity.getApplicationContext())
                .enableAutoManage(activity.F,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();*/

    }





    public void handleSignInResult(GoogleSignInResult result, Activity activity,String name,String idToken,String email,String id){
        if(result.isSuccess()){
            Log.d("myApp","1");
            GoogleSignInAccount account = result.getSignInAccount();
            idToken = account.getIdToken();
            id=account.getAccount().type;
            name = account.getDisplayName();
            email = account.getEmail();
            this.name=name;
            this.email=email;
            this.idToken=idToken;


            for(int i=0;i<blockeduserList.size();i++){
                Log.d("myApp","userID: "+FirebaseAuth.getInstance().getCurrentUser().getUid()+"blockedID: "+blockeduserList.get(i).getUserID());
                if(blockeduserList.get(i).getUserID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    Intent intent=new Intent(activity,MainActivity.class);
                    activity.startActivity(intent);
                    Toast.makeText(activity.getApplicationContext(),"You are blocked",Toast.LENGTH_SHORT).show();
                }
            }

            /*mAuth = FirebaseAuth.getInstance();
            final DatabaseReference userRef = FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("/users");

            mAuth.createUserWithEmailAndPassword(name, id).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {
                        //pd.dismiss();
                        //Toast.makeText(Register.this, "FAILED! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        //pd.dismiss();
                        FirebaseUser tempUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (tempUser != null) {
                            userRef.child(tempUser.getUid()).setValue(tempUser.getEmail());
                            //Utils.intentWithClear(Register.this, Users.class);
                        }
                    }
                }
            });
*/
            Log.d("myApp","name: "+name+" email: "+email);
            String finalName = name;
            String finalEmail = email;



            //AddToFirebase("",name,email,"","5",FirebaseAuth.getInstance().getCurrentUser().getUid());




            // you can store user data to SharedPreference
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

            firebaseAuthWithGoogle(credential,activity);
        }else{
            // Google Sign In failed, update UI appropriately
            Log.e(TAG, "Login Unsuccessful. "+result);
            Toast.makeText(activity, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }
    private void firebaseAuthWithGoogle(AuthCredential credential, Activity activity){

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        if(task.isSuccessful()){
                            Log.d("myApp","2");
                        }else{
                            Log.w(TAG, "signInWithCredential" + task.getException().getMessage());
                            task.getException().printStackTrace();
                            Toast.makeText(activity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if(task.isComplete()){
                           // Toast.makeText(activity.getApplicationContext(),"Task is Completed",Toast.LENGTH_SHORT).show();
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                        modelUsersData=dataSnapshot.getValue(ModelUsersData.class);
                                        if(FirebaseAuth.getInstance().getCurrentUser().getUid().toString().equals(dataSnapshot.getKey())){
                                            check[0] =false;
                                            Log.d("myApp", String.valueOf(check[0]));
                                            break;
                                        }else{
                                            modelUsersData=null;
                                        }
                                    }
                                    if(modelUsersData!=null) {
                                        if (modelUsersData.getName().isEmpty()||modelUsersData.getCity().isEmpty()||modelUsersData.getPhoneNumber().isEmpty()||modelUsersData.getImageUri().isEmpty()) {
                                            if (check[0]) {
                                                Log.d("myApp", "getuid: " + "ran");
                                                AddToFirebase("", name, email, "", "5", FirebaseAuth.getInstance().getCurrentUser().getUid(), activity,"");
                                            } else {
                                                gotoProfile(activity);
                                            }
                                        } else {
                                            Intent intent = new Intent(activity, DashboardActivity.class);
                                            activity.startActivity(intent);
                                            Toast.makeText(activity, "Login successful", Toast.LENGTH_SHORT).show();
                                            activity.finish();
                                        }
                                    }
                                    else{
                                        AddToFirebase("", name, email, "", "5", FirebaseAuth.getInstance().getCurrentUser().getUid(), activity,"");
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else{
                            Log.w(TAG, "signInWithCredential" + task.getException().getMessage());
                            task.getException().printStackTrace();
                            Toast.makeText(activity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }




    private void gotoProfile(Activity activity){

        //myRef=database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        Log.d("myApp","test: "+FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        //AddToFirebase("123","!23","234","234");
        //myRef.setValue(modelUsersData);

        Intent intent = new Intent(activity, ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        Toast.makeText(activity, "Login successful", Toast.LENGTH_SHORT).show();
        //activity.finish();
    }
    public void onStart(FirebaseAuth firebaseAuth,FirebaseAuth.AuthStateListener authStateListener) {
        if (authStateListener != null){
            FirebaseAuth.getInstance().signOut();
        }
        firebaseAuth.addAuthStateListener(authStateListener);
    }
    public void onStop(FirebaseAuth firebaseAuth,FirebaseAuth.AuthStateListener authStateListener) {
        if (authStateListener != null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    public GoogleSignInClient getmSignInClient() {
        return mSignInClient;
    }

    public String getName() {
        return name;
    }

    public FirebaseAuth.AuthStateListener getAuthStateListener() {
        return authStateListener;
    }

    public Button getButton() {
        return button;
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public String getEmail() {
        return email;
    }

    public String getIdToken() {
        return idToken;
    }

    public static String getTAG() {
        return TAG;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthStateListener(FirebaseAuth.AuthStateListener authStateListener) {
        this.authStateListener = authStateListener;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirebaseAuth(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public void setmSignInClient(GoogleSignInClient mSignInClient) {
        this.mSignInClient = mSignInClient;
    }
    public void AddToFirebase(String phoneNumber,String name,String email,String city,String point,String userID,Activity activity,String imageUri){
        ModelUsersData modelUsersData=new ModelUsersData(phoneNumber,name,email,city,point,userID,imageUri);
        Log.d("myApp",modelUsersData.getCity());

        database.child("Users").child(userID).setValue(modelUsersData);
        Log.d("myApp","addTOfIREBASE: "+database.getRoot().toString());

        gotoProfile(activity);
    }

}
