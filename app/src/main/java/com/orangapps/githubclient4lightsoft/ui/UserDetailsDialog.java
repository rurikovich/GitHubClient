package com.orangapps.githubclient4lightsoft.ui;

import android.app.Dialog;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.orangapps.githubclient4lightsoft.R;
import com.orangapps.githubclient4lightsoft.data.User;
import com.orangapps.githubclient4lightsoft.githubApi.AsyncRequest;

import org.json.JSONException;
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

        final ListView listview = (ListView) findViewById(R.id.repos_list);
        final List<String> values = new ArrayList<String>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, values);
        listview.setAdapter(adapter);

        new AsyncRequest(user.getRepos_url()) {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                String[] repos = processStrToArray(s);
                for (String repoStr : repos) {
                    try {
                        JSONObject repoJson = new JSONObject(repoStr);
                        values.add(repoJson.getString("name"));
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.execute();


    }
}
