package ucas.edu.android.mymail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.app.AlertDialog;
import android.content.DialogInterface;

import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    ListView mListView;
    private ActionMode mActionMode;
    MailAdapter mMailAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = findViewById(R.id.ListView);

        List<EmailData> emailDataList = new ArrayList<>();
        emailDataList.add(new EmailData("Sam", "Weekend adventure", "Let's go fishing with John and others. We will do some barbecue and have soo much fun", "10:42am"));
        emailDataList.add(new EmailData("Facebook", "James, you have 1 new notification", "A lot has happened on Facebook since", "16:04pm"));
        emailDataList.add(new EmailData("Google+", "Top suggested Google+ pages for you", "Top suggested Google+ pages for you", "18:44pm"));
        emailDataList.add(new EmailData("Twitter", "Follow T-Mobile, Samsung Mobile U", "James, some people you may know", "20:04pm"));
        emailDataList.add(new EmailData("Pinterest Weekly", "Pins you’ll love!", "Have you seen these Pins yet? Pinterest", "09:04am"));
        emailDataList.add(new EmailData("Josh", "Going lunch", "Don't forget our lunch at 3PM in Pizza hut", "01:04am"));

        mMailAdapter = new MailAdapter(MainActivity.this, emailDataList);
        mListView.setAdapter(mMailAdapter);

        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                // Update the selected items count here if needed
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.my_menu, menu);
                mActionMode = mode;
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (item.getItemId() == R.id.menu_add) {
                    showAddEmailDialog();
                    mode.finish();
                }
                return true;
            }


            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mActionMode = null;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            showAddEmailDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAddEmailDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_email, null);
        builder.setView(dialogView);

        final EditText editTextFrom = dialogView.findViewById(R.id.editTextFrom);
        final EditText editTextSubject = dialogView.findViewById(R.id.editTextSubject);
        final EditText editTextBody = dialogView.findViewById(R.id.editTextBody);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String from = editTextFrom.getText().toString();
                String subject = editTextSubject.getText().toString();
                String body = editTextBody.getText().toString();

                EmailData emailData = new EmailData(from, subject, body, "now");

                mMailAdapter.addItem(emailData);
            }
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
