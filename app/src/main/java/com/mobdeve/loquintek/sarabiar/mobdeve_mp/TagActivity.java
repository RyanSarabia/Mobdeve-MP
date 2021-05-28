package com.mobdeve.loquintek.sarabiar.mobdeve_mp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class TagActivity extends AppCompatActivity {

    private EditText tagNameEt;
    private Button tagAddBtn;
    private ChipGroup tagCg;

    private Database db;
    private List<Tag> tagList;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private Button popupConfirmBtn;
    private Button popupCancelBtn;
    private TextView popupPromptTv;

    private Button editTagCancelBtn;
    private Button editTagSaveBtn;
    private EditText editTagEt;
    private Chip editTagChp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        tagNameEt = findViewById(R.id.tagNameEt);
        tagAddBtn = findViewById(R.id.tagAddBtn);
        tagCg = findViewById(R.id.tagCg);

        db = new Database(this);
        tagList = db.getAllTags();

        populateChipGroup();

        tagAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = tagNameEt.getText().toString();

                Tag tag = new Tag(name);

                if (tagList.contains(tag)) {
                    Toast.makeText(
                            TagActivity.this,
                            "Tag name already exists, please choose another name.",
                            Toast.LENGTH_LONG
                    ).show();
                }

                else {
                    boolean truth = db.addTag(tagNameEt.getText().toString());

                    if(truth) {
                        Log.d("TAG", "has added! ");
                    }

                    else {
                        Log.d("TAG", "has not been added! ");
                    }

                    tagList.add(tag);
                    addChip(name);
                }
            }
        });
    }

    private void populateChipGroup() {
        for (int i = 0; i < tagList.size(); i++) {
            String tagName = tagList.get(i).getTagName();
            addChip(tagName);
        }

    }

    private void addChip(String tagName) {
        Chip tagChip = new Chip(this);
        tagChip.setText(tagName);
        tagChip.setTextSize(25);
        tagChip.setCloseIconVisible(true);
        tagChip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createConfirmDialog(v);
            }
        });

        tagChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEditDialog(v);
            }
        });

        tagCg.addView(tagChip);
    }

    private void createConfirmDialog(View chipView) {
        String name = ((Chip) chipView).getText().toString();

        dialogBuilder = new AlertDialog.Builder(this);
        final View promptPopupView = getLayoutInflater().inflate(R.layout.confirmation_popup_layout, null);

        popupConfirmBtn = promptPopupView.findViewById(R.id.popupConfirmBtn);
        popupCancelBtn = promptPopupView.findViewById(R.id.popupCancelBtn);
        popupPromptTv = promptPopupView.findViewById(R.id.popupPromptTv);

        popupPromptTv.setText("Are you sure you want to delete this tag?");

        dialogBuilder.setView(promptPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        popupCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        popupConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                db.deleteTag(name);
//                tagList.remove(name);
                tagCg.removeView(chipView);
            }
        });

    }

    private void createEditDialog(View chipView) {

        String name = ((Chip)chipView).getText().toString();
        dialogBuilder = new AlertDialog.Builder(this);
        final View editPopupView = getLayoutInflater().inflate(R.layout.tag_edit_popup_layout, null);

        editTagCancelBtn = editPopupView.findViewById(R.id.editTagCancelBtn);
        editTagSaveBtn = editPopupView.findViewById(R.id.editTagSaveBtn);
        editTagEt = editPopupView.findViewById(R.id.editTagEt);
        editTagChp = editPopupView.findViewById(R.id.editTagChp);

        editTagChp.setText(name);

        dialogBuilder.setView(editPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        editTagCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        editTagSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTagName = editTagEt.getText().toString();
                db.updateTagName(newTagName, name);
                db.updateReceiptsTagName(newTagName, name);
                ((Chip)chipView).setText(newTagName);
                dialog.dismiss();
            }
        });

    }
}