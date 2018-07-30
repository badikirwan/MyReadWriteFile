package com.badikirwan.dicoding.myreadwritefile;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnNew, btnOpen, btnSave;
    private EditText edtContent, edtTitle;
    private File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNew = findViewById(R.id.btn_new);
        btnOpen = findViewById(R.id.btn_open);
        btnSave = findViewById(R.id.btn_save);
        edtContent = findViewById(R.id.edt_file);
        edtTitle = findViewById(R.id.edt_title);

        btnNew.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        path = getFilesDir();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_new:
                newFile();
                break;
            case R.id.btn_open:
                openFile();
                break;
            case R.id.btn_save:
                saveFile();
                break;
        }
    }

    private void newFile() {
        edtTitle.setText("");
        edtContent.setText("");

        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show();
    }

    private void loadData(String title) {
        String text = FileHelper.readFromFile(this, title);
        edtTitle.setText(title);
        edtContent.setText(text);
        Toast.makeText(this, "Loading " + title + " data", Toast.LENGTH_SHORT).show();
    }

    private void openFile() {
        showList();
    }

    private void showList() {
        final ArrayList<String> arrayList = new ArrayList<String>();
        for (String file : path.list()) {
            arrayList.add(file);
        }
        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang diinginkan");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadData(items[which].toString());
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveFile() {
        if (edtTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else {
            String title = edtTitle.getText().toString();
            String text = edtContent.getText().toString();
            FileHelper.writeToFile(title, text, this);
            Toast.makeText(this, "Saving " + edtTitle.getText().toString() + " file", Toast.LENGTH_SHORT).show();
        }
    }

}
