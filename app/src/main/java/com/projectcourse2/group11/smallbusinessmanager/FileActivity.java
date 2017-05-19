package com.projectcourse2.group11.smallbusinessmanager;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class FileActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listView;
    private FloatingActionButton fab;
    private ProgressDialog dialog;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private Uri fileUri;

    public static final String STORAGE_PATH = "file/";
    public static final String DATABASE_PATH = "file";
    public static final int REQUEST_CODE = 1234;

    private String projectUID;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarF);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Log.d("hehe","toolbar");
        if (getIntent()!=null){
            projectUID=getIntent().getStringExtra("PROJECT_UID");
            this.setTitle(getIntent().getStringExtra("PROJECT_NAME"));
        }

        Log.d("hehe","getIntent()");
        listView = (ListView) findViewById(R.id.listViewF);
        fab = (FloatingActionButton) findViewById(R.id.fabF);
        fab.setOnClickListener(this);

        /*
        Log.d("hehe","before refs");
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);


        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait loading file...");
        dialog.show();

        FirebaseListAdapter<FileUpload> adapter = new FirebaseListAdapter<FileUpload>(
                FileActivity.this,
                FileUpload.class,
                R.layout.file_item,
                mDatabaseRef
        ) {
            @Override
            protected void populateView(View v, FileUpload model, int position) {
                ImageView imageView = (ImageView) v.findViewById(R.id.image1);
                TextView textView = (TextView) v.findViewById(R.id.text1);

                Glide.with(FileActivity.this).load(model.getUri()).into(imageView);
                textView.setText(model.getName());
            }
        };

        listView.setAdapter(adapter);
        //dialog.dismiss();
        Log.d("hehe","after setAdapter");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final FileUpload file = (FileUpload) parent.getItemAtPosition(position);
                StorageReference islandRef = FirebaseStorage.getInstance().getReferenceFromUrl(file.getUri());

                //file stored at Local storage/Device storage/SBMFile
                File rootPath = new File(Environment.getExternalStorageDirectory(), "SBMFile");
                if (!rootPath.exists()) {
                    rootPath.mkdirs();
                } else {
                    String fileExtension = file.getUri().substring(file.getUri().lastIndexOf("."), file.getUri().lastIndexOf("?"));
                    final File localFile = new File(rootPath, file.getName() + fileExtension);

                    islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(FileActivity.this);
                            builder.setMessage("File *" + file.getName() + "* downloaded at Device storage/SBMFile")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    });
                }
            }
        });
        Log.d("hehe","after itemClickListener");
        */
    }


    @Override
    public void onClick(View v) {
        if (v == fab) {
            finish();
            startActivity(new Intent(FileActivity.this,AddFileActivity.class));
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUri = data.getData();
            Log.d("hehe","onActivityResult");
        }
    }

    private String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    private void showChangeLangDialog() {
        Log.d("hehe","dialog open");
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.file_name_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

        dialogBuilder.setTitle("File name dialog");
        dialogBuilder.setMessage("Enter file name");
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                fileName= edt.getText().toString();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
        Log.d("hehe","dialog end");
    }

}
