package oob.cityworld.adapters.City;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import oob.cityworld.R;
import oob.cityworld.models.City;

class CityViewHolder extends RecyclerView.ViewHolder{

    private TextView cityName;
    private TextView cityDescription;
    private ImageView cityBackground;
    private TextView cityStars;
    private CityAdapter adapter;

    CityViewHolder(View view, CityAdapter adapter) {
        super(view);

        this.cityName = (TextView) view.findViewById(R.id.cityName);
        this.cityDescription = (TextView) view.findViewById(R.id.cityDescription);
        this.cityBackground = (ImageView) view.findViewById(R.id.cityBackground);
        this.cityStars = (TextView) view.findViewById(R.id.cityStars);
        this.adapter = adapter;
    }

    void bind(City city) {
        this.cityName.setText(city.getName());
        this.cityDescription.setText(city.getDescription());
        Picasso.with(this.adapter.getActivity()).load(city.getBackground()).fit().into(this.cityBackground);
        this.cityStars.setText(String.valueOf(city.getStars()));
    }
}
