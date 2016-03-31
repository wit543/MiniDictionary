package com.example.wit.minidictionary.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wit.minidictionary.R;
import com.example.wit.minidictionary.views.TranslationAdapter;
import com.example.wit.minidictionary.word.Definition;
import com.example.wit.minidictionary.models.Storage;
import com.example.wit.minidictionary.word.PartOfSpeech;
import com.example.wit.minidictionary.word.Word;

import java.util.ArrayList;
import java.util.List;

public class NewWordActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button addWordButton,cancelButton , editButton;
    private Button loadImgButton,addTranslationButton;
    private TextView wordField;
    private TextView pronunciationField;
    private ListView translationListView;

    private List<Definition> definitions;
    private TranslationAdapter translationAdapter;

//    private int addTranslationState = 0
//    private TextView translations_list_view;
//    private Spinner spinner;
//    private TextView meaningField, posSub , meaningSub;
    private Word word;
    private int mode;

    private static final int REQUEST_ADD_NEW_DEFINITION = 0;
    private static final int REQUEST_LOAD_IMAGE = 1;
    private static final int NEW_WORD_MODE = 0;
    private static final int EDIT_WORD_MODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        word = (Word)getIntent().getSerializableExtra("word");
        this.setMode();
        this.initComponent();
    }

    private void setMode(){
        if(word == null)
            mode = NEW_WORD_MODE;
        else
            mode = EDIT_WORD_MODE;
    }

    private void initComponent(){
        //imageView = (ImageView) findViewById(R.id.imgView);
        wordField = (TextView)findViewById(R.id.wordField);
        pronunciationField = (TextView)findViewById(R.id.pronunciationField);
        //loadImgButton = (Button)findViewById(R.id.buttonLoadPicture);
        addWordButton = (Button)findViewById(R.id.addWordButton);
        cancelButton = (Button)findViewById(R.id.cancelButton);
        editButton = (Button)findViewById(R.id.editButton);
        addTranslationButton = (Button)findViewById(R.id.addTranslationButton);
        translationListView = (ListView)findViewById(R.id.transaltions_list_view);

        definitions = new ArrayList<Definition>();
        translationAdapter = new TranslationAdapter(this,R.layout.translation_cell,definitions);
        translationListView.setAdapter(translationAdapter);

//        loadImgButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(i, REQUEST_LOAD_IMAGE);
//            }
//        });


        addWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWord("" + wordField.getText(), "" + pronunciationField.getText(), definitions);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWord(word, "" + wordField.getText(), "" + pronunciationField.getText() , definitions);
                finish();
            }
        });

        addTranslationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewWordActivity.this,NewDefinitionActivity.class);
                startActivityForResult(intent, REQUEST_ADD_NEW_DEFINITION);
            }
        });

        this.setComponentFromMode();
    }

    private void setComponentFromMode(){
        switch (mode){
            case NEW_WORD_MODE :
                editButton.setVisibility(View.GONE);
                break;
            case EDIT_WORD_MODE:
                wordField.setText(word.getWord());
                pronunciationField.setText(word.getPronunciation());
                addWordButton.setVisibility(View.GONE);
                for(Definition d : word.getDefinition())
                    definitions.add(d);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data==null)
            return;

        if (requestCode == REQUEST_ADD_NEW_DEFINITION&& resultCode == Activity.RESULT_OK) {
            Definition definition = (Definition)data.getSerializableExtra("definition");
            if(definition!=null){
                definitions.add(definition);
            }
        }

        if (requestCode == REQUEST_LOAD_IMAGE&& resultCode == Activity.RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//            Cursor cursor = getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//            Bitmap img = BitmapFactory.decodeFile(picturePath);
//            imageView.setImageBitmap(img);
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    private void saveWord(String word,String pronunciation,List<Definition> definitionss){
        Word newWord = new Word(word,pronunciation,definitionss);
        Storage.getInstance().addWord(newWord);
    }

    private void editWord(Word original ,String word,String pronunciation,List<Definition> definitionss){

        Storage.getInstance().editWord(original, word, pronunciation, definitionss);
    }



    protected void onStart(){
        super.onStart();
        translationAdapter.notifyDataSetChanged();
    }

}