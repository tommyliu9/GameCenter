package fall2018.csc207.menu;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Map;

import fall2018.csc207.database.UserDBHandler;
import fall2018.csc207.game.GameMainActivity;
import fall2018.csc207.slidingtiles.R;

/**
 * The Login Activity.
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * Dialog Boxes for Sign-up, and instructions for signup.
     */
    private Dialog signupDialog;
    private Dialog infoDialog;

    /**
     * Get a reference to the username Database.
     */
    private final UserDBHandler userDB = new UserDBHandler(this, UserDBHandler.DATABASE_NAME,
            null, UserDBHandler.DATABASE_VERSION);

    /**
     * Called when we create a LoginActivity.
     * @param savedInstanceState The activity's previously saved state, contained in a bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.usernameEditText);
        final EditText passwordEditText = findViewById(R.id.passwordEditText);
        CardView loginButton = findViewById(R.id.loginButton);
        CardView signupButton = findViewById(R.id.signupButton);
        signupDialog = new Dialog(this);


//      When Login button is clicked, check to see if the username/password match that which
//      is in the database. If so, login and start the next activity. If not, reject the login.

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(usernameEditText, passwordEditText);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignupDialogue();
            }
        });
    }

    /**
     * Attempt to login the user.
     *
     * @param usernameEditText The field which holds the user's username.
     * @param passwordEditText The field which holds the user's password.
     */
    private void loginUser(EditText usernameEditText, EditText passwordEditText) {
        Map<String, String> userMap = userDB.fetchDatabaseEntries();
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        String mapPass = userMap.get(username);

        //Check if mapPass is null first! dont want to compare null to password.
        if (mapPass != null && mapPass.equals(password)) {
            Toast.makeText(getApplicationContext(), "Welcome, " + username,
                    Toast.LENGTH_SHORT).show();
            Intent startingActivityInt = new Intent(LoginActivity.this,
                    GameCentreActivity.class);
            startingActivityInt.putExtra(GameMainActivity.USERNAME, username);
            startActivity(startingActivityInt);
        } else {
            Toast.makeText(getApplicationContext(), "Could not log in.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Show the Signup Dialog Box
     */
    private void showSignupDialogue(){
        TextView txtClose;
        CardView submitButton;
        final EditText usernameSignupET;
        final EditText passwordSignupET;

        signupDialog.setContentView(R.layout.dialog_signup);

        txtClose = signupDialog.findViewById(R.id.txtclose);
        submitButton = signupDialog.findViewById(R.id.submitButton);
        usernameSignupET = signupDialog.findViewById(R.id.usernameSignupEditText);
        passwordSignupET = signupDialog.findViewById(R.id.passwordSignupEditText);
        infoDialog = new Dialog(this);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser(usernameSignupET, passwordSignupET);
            }
        });

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupDialog.dismiss();
            }
        });
        signupDialog.show();
    }

    /**
     * Attempt to create the user.
     *
     * @param usernameSignupET The field which holds the user's username.
     * @param passwordSignupET The field which holds the user's password.
     */
    private void createUser(EditText usernameSignupET, EditText passwordSignupET) {
        Map<String, String> userMap = userDB.fetchDatabaseEntries();
        String username = usernameSignupET.getText().toString();
        String password = passwordSignupET.getText().toString();

        String mapPass = userMap.get(username);

        // Don't want empty username or pass
        if (username.equals("") || password.equals("")){
            Toast.makeText(getApplicationContext(), "One or more fields are empty!",
                    Toast.LENGTH_SHORT).show();
        }
        else if (password.length() <= 5){
            Toast.makeText(getApplicationContext(), "Passwords must be at least 6 characters.",
                    Toast.LENGTH_SHORT).show();
        }
        //mapPass is null if the username is not a valid key in the hashmap!
        else if (mapPass == null){
            userDB.addUser(username, password);
            Toast.makeText(getApplicationContext(), "Created user " + username,
                    Toast.LENGTH_SHORT).show();
            signupDialog.dismiss();
        }
        else{
            Toast.makeText(getApplicationContext(), "username taken.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Show the Signup Info Dialog Box
     *
     * @param v The View.
     */
    public void showInfo(View v){
        infoDialog.setContentView(R.layout.dialog_info);
        TextView infoTxtClose;
        infoTxtClose = infoDialog.findViewById(R.id.infoTxtClose);
        infoTxtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
    }
}
