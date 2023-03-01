package com.example.ltandroid.listcat;

import android.annotation.SuppressLint;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltandroid.R;

import java.util.ArrayList;
import java.util.List;


public class CatActivity extends AppCompatActivity {
    private Spinner spinner;
    private EditText edtName;
    private EditText edtPrice;
    private EditText edtDescription;
    private Button btnAdd;
    private Button btnUpdate;
    private SearchView searchView;
    private RecyclerView rvMain;
    private CatAdapter catAdapter;
    private List<Integer> imgList;
    private long currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);
        initBinding();
        setUpSpinner();
        setUpAdapter();
        setOnClick();
    }

    private void initBinding() {
        spinner = findViewById(R.id.spinner);
        edtName = findViewById(R.id.edt_name);
        edtPrice = findViewById(R.id.edt_price);
        edtDescription = findViewById(R.id.edt_description);
        btnAdd = findViewById(R.id.btn_crudAdd);
        searchView = findViewById(R.id.searchView);
        btnUpdate = findViewById(R.id.btn_update);
        rvMain = findViewById(R.id.btn_rcv);
    }

    private void setOnClick() {
        btnAdd.setOnClickListener(view -> {
            try {
                Cat a = new Cat(System.currentTimeMillis(), edtName.getText().toString(), edtPrice.getText().toString(), edtDescription.getText().toString(), Integer.parseInt(spinner.getSelectedItem().toString()));
                catAdapter.insertList(a);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnUpdate.setOnClickListener(view -> {
            try {
                Cat a = new Cat(currentId, edtName.getText().toString(), edtPrice.getText().toString(), edtDescription.getText().toString(), Integer.parseInt(spinner.getSelectedItem().toString()));
                catAdapter.updateList(a);
                btnUpdate.setEnabled(false);
                btnAdd.setEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                catAdapter.searchList(query);
                if(query == null || query ==""){
                    catAdapter.searchList("");
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                catAdapter.searchList(newText);
                return false;
            }
        });
    }

    private void setUpSpinner() {
        imgList = new ArrayList<>();
        imgList.add(R.drawable.meo1);
        imgList.add(R.drawable.meo2);
        imgList.add(R.drawable.meo3);
        imgList.add(R.drawable.meo1);

        SpinnerAdapter sp = new SpinnerAdapter() {
            @Override
            public View getDropDownView(int i, View view, ViewGroup viewGroup) {
                LayoutInflater inflater = getLayoutInflater();
                View row = inflater.inflate(R.layout.item_spinner, viewGroup,
                        false);
                ImageView im = row.findViewById(R.id.iv_itemSpn);
                im.setImageResource(imgList.get(i));
                return row;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver dataSetObserver) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

            }

            @Override
            public int getCount() {
                return imgList.size();
            }

            @Override
            public Object getItem(int i) {
                return imgList.get(i);
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                LayoutInflater inflater = getLayoutInflater();
                @SuppressLint("ViewHolder") View row = inflater.inflate(R.layout.item_spinner, viewGroup,
                        false);
                ImageView im = row.findViewById(R.id.iv_itemSpn);
                im.setImageResource(imgList.get(i));
                return row;
            }

            @Override
            public int getItemViewType(int i) {
                return 1;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        };
        spinner.setAdapter(sp);
    }

    private void setUpAdapter() {
        catAdapter = new CatAdapter(new ArrayList(), this, new OnUpdateListener() {
            @Override
            public void invoke(Cat cat) {
                currentId = cat.getId();
                edtName.setText(cat.getName());
                edtPrice.setText(cat.getPrice());
                edtDescription.setText(cat.getDescription());
                int idImg = cat.getImg();
                for (int i = 0; i < imgList.size(); i++) {
                    if (imgList.get(i) == idImg) {
                        spinner.setSelection(i);
                        break;
                    }
                }
                btnAdd.setEnabled(false);
                btnUpdate.setEnabled(true);
            }
        });
        rvMain.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvMain.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvMain.setAdapter(catAdapter);

    }

}