package com.projectcourse2.group11.smallbusinessmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.projectcourse2.group11.smallbusinessmanager.model.TestProject;


public class ProjectActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton fab;
    private ListView listView;
    private ListAdapter mAdapter;
    private ProgressDialog progressDialog;
    private String companyID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_home);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading projects");
        progressDialog.show();
        listView = (ListView) findViewById(R.id.listView);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        companyID=getIntent().getStringExtra("COMPANY_ID");

        //// TODO: 08/05/2017 get company(wait for company register to finish)
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("companyProjects").child(companyID);
        mAdapter = new FirebaseListAdapter<TestProject>(
                ProjectActivity.this,
                TestProject.class,
                android.R.layout.simple_list_item_single_choice,
                ref) {
            @Override
            protected void populateView(View v, TestProject model, int position) {
                TextView textView = (TextView) v.findViewById(android.R.id.text1);
                textView.setText(model.getName());
            }
        };
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(mAdapter);

        progressDialog.dismiss();

        listView.setOnItemClickListener(new DoubleClickListener() {
            @Override
            protected void onSingleClick(AdapterView<?> parent, View v, int position, long id) {
                final TestProject project = (TestProject) parent.getItemAtPosition(position);

                toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.nav_delete_project) {
                            FirebaseDatabase.getInstance().getReference().child("projectOrders").child(project.getId()).removeValue();
                            ref.child(project.getId()).removeValue();
                        }
                        return true;
                    }
                });
            }

            @Override
            protected void onDoubleClick(AdapterView<?> parent, View v, int position, long id) {
                TestProject project = (TestProject) parent.getItemAtPosition(position);
                Intent intent = new Intent(ProjectActivity.this, SingleProjectHomeActivity.class);
                intent.putExtra("projectUID", project.getId());
                intent.putExtra("name", project.getName());
                intent.putExtra("COMPANY_ID",companyID);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_project_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                //return true;
                finish();
                startActivity(new Intent(ProjectActivity.this, MainActivity.class).putExtra("COMPANY_ID",companyID));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == fab) {
            finish();
            startActivity(new Intent(ProjectActivity.this, ProjectCreatActivity.class).putExtra("COMPANY_ID",companyID));
        }
    }
}
