package com.orangapps.githubclient4lightsoft.ui;

import android.app.Dialog;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.orangapps.githubclient4lightsoft.R;
import com.orangapps.githubclient4lightsoft.data.User;
import com.orangapps.githubclient4lightsoft.githubApi.AsyncRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.orangapps.githubclient4lightsoft.utils.httpUtils.processStrToArray;

/**
 * Created by rurik on 30.09.14.
 */
public class UserDetailsDialog extends Dialog {

    public UserDetailsDialog(Context context, User user) {
        super(context);
        setTitle("Login: " + user.getLogin());
        setContentView(R.layout.user_details_dialog);

        ListView listview = (ListView) findViewById(R.id.repos_list);
        List<String> values = new ArrayList<String>();

        try {
            String reposStr = new AsyncRequest(user.getRepos_url()).execute().get();
            String[] repos = processStrToArray(reposStr);
            for (String repoStr : repos) {
                JSONObject repoJson = new JSONObject(repoStr);
                values.add(repoJson.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, values);
        listview.setAdapter(adapter);

    }
}
