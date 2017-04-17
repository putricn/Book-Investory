package id.sch.smktelkom_mlg.bookinventory;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.sch.smktelkom_mlg.bookinventory.activity.BookFormActivity;
import id.sch.smktelkom_mlg.bookinventory.adapter.BooksAdapter;
import id.sch.smktelkom_mlg.bookinventory.adapter.DividerDecoration;
import id.sch.smktelkom_mlg.bookinventory.helper.HelperFunction;
import id.sch.smktelkom_mlg.bookinventory.model.Book;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    public int TO_FORM = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerBook)
    RecyclerView recyclerBook;
    @BindView(R.id.fab)
    FloatingActionButton btnAdd;
    private List<Book> bookList = new ArrayList<Book>();
    private BooksAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Kumpulan Novel Luna Torashyngu");

        mAdapter = new BooksAdapter(this, bookList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerBook.setLayoutManager(mLayoutManager);
        recyclerBook.setItemAnimator(new DefaultItemAnimator());
        recyclerBook.addItemDecoration(new DividerDecoration(this));

        recyclerBook.setAdapter(mAdapter);
        recyclerBook.addOnItemTouchListener(new HelperFunction.
                RecyclerTouchListener(this, recyclerBook,
                new HelperFunction.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent i = new Intent(MainActivity.this, BookFormActivity.class);
                        i.putExtra("bookEdit", bookList.get(position));
                        startActivity(i);
                    }

                    @Override
                    public void onLongClick(View view, final int position) {
                        final Book book = bookList.get(position);
                        AlertDialog dialog = new AlertDialog.
                                Builder(MainActivity.this).setTitle("Delete")
                                .setMessage("Yakin menghapus buku " + book.getBook_title() + " ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                        bookList.remove(book);
                                        mAdapter.notifyItemRemoved(position);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                                .create();
                        dialog.show();
                    }
                }));

        prepareBookData();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, BookFormActivity.class);
                startActivityForResult(i, TO_FORM);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
                SearchView searchView = (SearchView) item.getActionView();

                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
                searchView.setOnQueryTextListener(MainActivity.this);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == TO_FORM) {
            Book bookForm = (Book) data.getExtras().getSerializable("book");
            bookList.add(bookForm);
            Toast.makeText(this, "Book " + bookForm.getBook_title() + " successfully added", Toast.LENGTH_SHORT).show();
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mAdapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private void prepareBookData() {
        Book book = new Book("9780439064873", "D'ANGEL 3", "Luna Torashyngu", 2015, "Fantasy, Teenlith, Mystery", "This is some synopsis", R.drawable.hp_chamber);
        bookList.add(book);

        book = new Book("9780316015844", "LOVASKET 1", "Luna Torashyngu", 2014, "Fantasy, Drama, Teenlith", "This is some synopsis", R.drawable.twilight1);
        bookList.add(book);

        book = new Book("9781484724989", "Mawar Merah - Mosaik", "Luna Torashyngu", 2015, "Fantasy, Drama, Teenlith", "This is some synopsis", R.drawable.star_wars);
        bookList.add(book);

        book = new Book("9780439136365", "Mawar Merah - Matahari", "Luna Torashyngu", 2015, "Fantasy, Drama, Teenlith", "This is some synopsis", R.drawable.hp_azkaban);
        bookList.add(book);

        book = new Book("9780439136365", "D'Angel", "Luna Torashyngu", 2013, "Fantasy, Drama, Teenlith", "This is some synopsis", R.drawable.hp_azkaban);
        bookList.add(book);

        book = new Book("9780439136365", "Victory", "Luna Torashyngu", 2015, "Fantasy, Drama, Teenlith", "This is some synopsis", R.drawable.hp_azkaban);
        bookList.add(book);

        book = new Book("9780439136365", "Love Detective", "Luna Torashyngu", 2014, "Fantasy, Drama, Teenlith", "This is some synopsis", R.drawable.hp_azkaban);
        bookList.add(book);

        book = new Book("9780439136365", "LOVASKET 7 - Final Game", "Luna Torashyngu", 2013, "Fantasy, Drama, Teenlith", "This is some synopsis", R.drawable.hp_azkaban);
        bookList.add(book);

        mAdapter.notifyDataSetChanged();
    }
}
