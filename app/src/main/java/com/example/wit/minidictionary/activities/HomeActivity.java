package com.example.wit.minidictionary.activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.example.wit.minidictionary.R;
import com.example.wit.minidictionary.models.Storage;
import com.example.wit.minidictionary.views.WordAdapter;
import com.example.wit.minidictionary.word.Word;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private WordAdapter wordAdapter;
    private GridView homeWordGrid;
    private Drawable iconOpenSearch;
    private Drawable iconCloseSearch;
    private EditText searchET;
    private MenuItem searchAction;

    private List<Word> words;
    private List<Word> wordsFiltered;
    private boolean isSearchOpened;
    private String searchQuery;
    private TextToSpeech tts;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, NewWordActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initializeComponent();
        initSearch();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initializeComponent() {
        // save;
        words = new ArrayList<Word>();
       /* try {
            FileOutputStream fos = openFileOutput("text",MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //load
        try {
            FileOutputStream fos = openFileOutput("text",MODE_PRIVATE);
            ObjectInputStream fis = new ObjectInputStream(fos);
            words = (List<Word>) fis.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        */


        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });

        words = new ArrayList<Word>();
        homeWordGrid = (GridView) findViewById(R.id.home_words_grid);
        wordAdapter = new WordAdapter(this, R.layout.word_cell, words);
        homeWordGrid.setAdapter(wordAdapter);
        homeWordGrid.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        homeWordGrid.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                if (checked) {
                    wordAdapter.setNewSelection(position, true);
                } else {
                    wordAdapter.removeSelected(position);
                }

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.contextual_home_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_delete:
                        deleteSelectedWord();
                        loadWords();
                        wordAdapter.clearSelection();
                        mode.finish();
                        return true;
                    case R.id.select_all:
                        for (int i = 0; i < words.size(); i++) {
                            wordAdapter.setNewSelection(i, true);
                        }
                }

                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                wordAdapter.clearSelection();
            }
        });
        homeWordGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tts.speak(words.get(position).getWord(),TextToSpeech.QUEUE_FLUSH,null,"speak");
                Intent intent = new Intent(HomeActivity.this,WordActivity.class);
                intent.putExtra("word", words.get(position) );
                startActivity(intent);
            }
        });
        homeWordGrid.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                Log.v("test ContextMenu", "menuInfo= " + menuInfo);
            }
        });

        homeWordGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("test onItemLongClick", "position= " + position + " id=" + id);
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, NewWordActivity.class);
                startActivity(intent);
            }
        });

        registerForContextMenu(homeWordGrid);

    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initSearch(){
        wordsFiltered = words;

        // get icon

        iconOpenSearch = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_search);
        iconCloseSearch = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_close);
        //init listView

    }
    private void deleteSelectedWord(){
        for(Integer i:wordAdapter.getCurrentCheckedPosition()){
            Storage.getInstance().removeWord(words.get(i));
        }
    }
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.word_menu, menu);
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        switch (item.getItemId()) {
//            case R.id.edit:
//                Log.v("test", "edit");
//                return true;
//            case R.id.delete:
//                Log.v("test", "delete");
//                Storage.getInstance().removeWord(words.get(info.position));
//                loadWords();
//                return true;
//            case R.id.share:
//                Log.v("test","share");
//        }
//        return super.onContextItemSelected(item);
//    }
    private void loadWords(){
        words.clear();
        for(Word w: Storage.getInstance().loadWord())
            words.add(w);
        wordAdapter.notifyDataSetChanged();
    }

    @Override
        public void onBackPressed () {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if(isSearchOpened){
                closeSearchBar();
            }
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.home, menu);
            return true;
        }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        searchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the HomeActivity/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }
            if(id == R.id.action_search){
                if(isSearchOpened){
                    closeSearchBar();
                    Log.v("test", "close");
                    ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
                }
                else{
                    openSearchBar(searchQuery);
                    Log.v("test", "open");
                    ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                            .showSoftInput(searchET, InputMethodManager.SHOW_FORCED);
                }
            }

            return super.onOptionsItemSelected(item);
        }
        private void openSearchBar(String text){
            //set custom view on action bar
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            assert actionBar != null;
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(R.layout.search_word_bar);
            actionBar.setDisplayShowTitleEnabled(false);
            // search edit text field setup.
            searchET = (EditText) actionBar.getCustomView().findViewById(R.id.search_word);
            searchET.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    searchQuery = searchET.getText().toString();
                    wordsFiltered = performSearch(words, searchQuery);
                    loadSelectedWord();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            searchET.setText(text);
            searchET.requestFocus();

            // change search icon
            searchAction.setIcon(iconCloseSearch);
            isSearchOpened = true;
        }
        private void loadSelectedWord(){
            words.clear();
            for(Word w: wordsFiltered)
                words.add(w);
            wordAdapter.notifyDataSetChanged();
        }
        private List<Word> performSearch(List<Word> words, String text){

            String query = text.toLowerCase();
            // output list
            List<Word> wordsFiltered = new ArrayList<Word>();
            for(Word w :Storage.getInstance().loadWord()){
                if(w.getWord().contains(query)){
                    wordsFiltered.add(w);
                }
            }
            return wordsFiltered;
        }
        private void closeSearchBar(){
            //remove custom view.
            getSupportActionBar().setDisplayShowCustomEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            searchAction.setIcon(iconOpenSearch);
            isSearchOpened =false;
            searchET.setText("");
            loadWords();
        }
        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected (MenuItem item){
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            if (id == R.id.nav_camera) {
                // Handle the camera action
            } else if (id == R.id.nav_gallery) {

            } else if (id == R.id.nav_slideshow) {

            } else if (id == R.id.nav_manage) {

            } else if (id == R.id.nav_share) {

            } else if (id == R.id.nav_send) {

            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }


    @Override
    public void onStart() {
        super.onStart();
        loadWords();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        /*client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "HomeActivity Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.wit.minidictionary/http/host/path")
        );

        AppIndex.AppIndexApi.start(client, viewAction);*/
    }


    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "HomeActivity Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.wit.minidictionary/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

}
