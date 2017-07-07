package oob.cityworld.application;

import android.app.Application;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import oob.cityworld.models.City;

public class MyApplication extends Application {

    public static AtomicInteger cityId;

    @Override
    public void onCreate() {
        setUpRealmConfiguration();

        Realm realm = Realm.getDefaultInstance();
        cityId = getIdByTable(realm, City.class);
        realm.close();
    }

    private void setUpRealmConfiguration() {
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
    }

    private <O extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<O> anyClass) {
        RealmResults<O> results = realm.where(anyClass).findAll();
        return (results.size() > 0) ? new AtomicInteger(results.max("id").intValue()) : new AtomicInteger();
    }
}
