package oob.cityworld.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import oob.cityworld.R;
import oob.cityworld.Utils.Utils;
import oob.cityworld.adapters.City.CityAdapter;
import oob.cityworld.models.City;

public class CityActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<City>> {

    private CityAdapter cityAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Realm realm;
    private RealmResults<City> cities;
    private FloatingActionButton fabAddCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        this.init();
    }

    private void init() {
        this.setVariables();

        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(this.layoutManager);
        this.recyclerView.setAdapter(this.cityAdapter);

        this.setListeners();
    }

    private void setVariables() {
        this.realm = Realm.getDefaultInstance();

        this.cities = this.getAllCities();

        this.cityAdapter = new CityAdapter(cities, R.layout.card_view_item, this, new CityAdapter.OnDeleteButtonClick() {
            @Override
            public void onClick(final City city, final int position) {
                Utils.showAlertForRemovingCity(
                        CityActivity.this,
                        getString(R.string.title_delete_city_dialog),
                        getString(R.string.message_delete_city_dialog, city.getName()),
                        getString(R.string.positive_action_button_text_delete_city),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                deleteCity(city, position);
                            }
                        },
                        getString(R.string.negative_action_button_text_standard),
                        null
                );
            }
        });
        this.layoutManager = new LinearLayoutManager(this);

        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        this.fabAddCity = (FloatingActionButton) findViewById(R.id.fabAddCity);
    }

    private void deleteCity(final City city, int position) {
        this.realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                city.deleteFromRealm();
            }
        });

        this.cityAdapter.notifyItemRemoved(position);
    }

    private void setExampleCity() {
        this.realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                City cityExample = new City(
                        "Example City",
                        "Example description",
                        "https://raw.githubusercontent.com/AL-Android-Course/Seccion_04_Realm_Lab/master/app/src/main/res/drawable/city.jpg",
                        (float) 4.5);
                realm.copyToRealm(cityExample);
            }
        });
    }

    private RealmResults<City> getAllCities() {
        return this.realm.where(City.class).findAll();
    }

    private void deleteAllCities() {
        this.realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
    }

    private void setListeners() {
        this.fabAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDetailsCityActivity();
            }
        });

        this.cities.addChangeListener(this);
    }

    private void loadDetailsCityActivity() {
        Intent it = new Intent(this, CityDetailsActivity.class);
        this.startActivity(it);
    }

    @Override
    public void onChange(RealmResults<City> element) {
        this.cityAdapter.notifyDataSetChanged();
    }
}
