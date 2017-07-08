package oob.cityworld.adapters.City;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.realm.RealmResults;
import oob.cityworld.models.City;

public class CityAdapter extends RecyclerView.Adapter<CityViewHolder> {

    private RealmResults<City> cities;
    private int layout;
    private Activity activity;
    private OnDeleteButtonClick listener;

    public CityAdapter(RealmResults<City> cities, int layout, Activity activity, OnDeleteButtonClick listener) {
        this.cities = cities;
        this.layout = layout;
        this.activity = activity;
        this.listener = listener;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.activity).inflate(this.layout, parent, false);
        return new CityViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(CityViewHolder cityViewHolder, int position) {
        cityViewHolder.bind(this.cities.get(position), this.listener);
    }

    @Override
    public int getItemCount() {
        return this.cities.size();
    }

    public interface OnDeleteButtonClick {
        void onClick(City city, int position);
    }

    Activity getActivity() {
        return activity;
    }
}
