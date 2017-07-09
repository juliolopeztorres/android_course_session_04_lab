package oob.cityworld.activities;

import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import io.realm.Realm;
import oob.cityworld.R;
import oob.cityworld.Utils.Utils;
import oob.cityworld.models.City;

public class CityDetailsActivity extends AppCompatActivity {

    private ImageView previewBackgroundCity;
    private EditText nameCity;
    private EditText descriptionCity;
    private EditText backgroundUrlCity;
    private RatingBar starsCity;
    private Button btnBackgroundPreviewCity;
    private FloatingActionButton fabDetailsCity;

    private Realm realm;
    private City city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_details);

        this.init();
    }

    private void init() {
        this.setVariables();
        this.setTitle();
        this.setListeners();
    }

    private void setTitle() {
        if (this.city == null) {
            setTitle(getString(R.string.add_city_title));
        } else {
            setTitle(getString(R.string.edit_city_title, this.city.getName()));
        }
    }

    private void setVariables() {
        this.realm = Realm.getDefaultInstance();
        this.previewBackgroundCity = (ImageView) findViewById(R.id.ImageViewCity);
        this.nameCity = (EditText) findViewById(R.id.inputName);
        this.descriptionCity = (EditText) findViewById(R.id.inputDescription);
        this.backgroundUrlCity = (EditText) findViewById(R.id.inputBackgroundUrl);
        this.starsCity = (RatingBar) findViewById(R.id.ratingBar);
        this.btnBackgroundPreviewCity = (Button) findViewById(R.id.btnPreviewImageViewCity);
        this.fabDetailsCity = (FloatingActionButton) findViewById(R.id.fabDetailCity);

        int idCity = this.getIntent().getIntExtra("id", -1);
        this.city = this.realm.where(City.class).equalTo("id", idCity).findFirst();

        if (this.city != null) {
            this.populateValues();
        }
    }

    private void populateValues() {
        this.nameCity.setText(this.city.getName());
        this.descriptionCity.setText(this.city.getDescription());
        this.backgroundUrlCity.setText(this.city.getBackground());
        this.starsCity.setRating(this.city.getStars());

        checkAndLoadUrlCityImage();
    }

    private void setListeners() {
        this.fabDetailsCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEmptyInputValues()) {
                    Utils.showToast(CityDetailsActivity.this, getString(R.string.message_empty_city_fields));
                } else {
                    saveCityOnRealm();
                    CityDetailsActivity.this.finish();
                }
            }
        });

        this.btnBackgroundPreviewCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndLoadUrlCityImage();
            }
        });
    }

    private boolean checkEmptyInputValues() {
        return this.nameCity.getText().toString().isEmpty() ||
                this.descriptionCity.getText().toString().isEmpty() ||
                this.backgroundUrlCity.getText().toString().isEmpty();
    }

    private void checkAndLoadUrlCityImage() {
        String url = backgroundUrlCity.getText().toString();
        Utils.checkAndLoadUrlImage(
                url,
                CityDetailsActivity.this,
                previewBackgroundCity,
                getString(R.string.message_empty_city_background_url));
    }

    private void saveCityOnRealm() {
        final String returnMessage = this.getReturnMessage();
        this.realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(
                    CityDetailsActivity.this.updateCityFromInputs()
                );
                Utils.showToast(CityDetailsActivity.this, returnMessage);
            }
        });
    }

    private City updateCityFromInputs() {
        City city;
        if (this.city == null) {
            city = new City(
                    nameCity.getText().toString(),
                    descriptionCity.getText().toString(),
                    backgroundUrlCity.getText().toString(),
                    starsCity.getRating()
            );
        } else {
            this.city.setName(this.nameCity.getText().toString());
            this.city.setDescription(this.descriptionCity.getText().toString());
            this.city.setBackground(this.backgroundUrlCity.getText().toString());
            this.city.setStars(this.starsCity.getRating());
            city = this.city;
        }

        return city;
    }

    private String getReturnMessage() {
        String name = this.nameCity.getText().toString();

        if (this.city == null) {
            return getString(R.string.message_add_city_successfully, name);
        }

        return getString(R.string.message_update_city_successfully, name);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        checkAndLoadUrlCityImage();
    }
}
