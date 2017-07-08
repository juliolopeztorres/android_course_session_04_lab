package oob.cityworld.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;
import oob.cityworld.R;
import oob.cityworld.adapters.City.CityAdapter;
import oob.cityworld.models.City;

public class CityActivity extends AppCompatActivity {

    private CityAdapter cityAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Realm realm;
    private RealmResults<City> cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        this.realm = Realm.getDefaultInstance();

        this.cities = this.getAllCities();

        this.cityAdapter = new CityAdapter(cities, R.layout.card_view_item, this, new CityAdapter.OnDeleteButtonClick() {
            @Override
            public void onClick(City city, int position) {
                deleteCity(city, position);
            }
        });
        this.layoutManager = new LinearLayoutManager(this);

        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(this.layoutManager);
        this.recyclerView.setAdapter(this.cityAdapter);
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
                City cityExample = new City("Example City", "Example description", R.drawable.city, 5);
                realm.copyToRealm(cityExample);
            }
        });
    }

    public RealmResults<City> getAllCities() {
        return this.realm.where(City.class).findAll();
    }
}
