package com.assignment.furniturestore;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FurnitureAdapter extends BaseAdapter {

    private Context context;
    private List<FurnitureItem> furnitureList;

    private int[] imageResources = {
            R.drawable.ic_image,
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image8,
            R.drawable.image9,
            R.drawable.image10,
            R.drawable.image11,
            R.drawable.image12,
            R.drawable.image13,
            R.drawable.image14,
            R.drawable.image15,
            R.drawable.image16,
            R.drawable.image17,
            R.drawable.image18,
            R.drawable.image19,
            R.drawable.image20
    };

    public FurnitureAdapter(Context context, List<FurnitureItem> furnitureList) {
        this.context = context;
        this.furnitureList = furnitureList;
    }

    @Override
    public int getCount() {
        return furnitureList.size();
    }

    @Override
    public Object getItem(int position) {
        return furnitureList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FurnitureItem furnitureItem = furnitureList.get(position);

        // Set image resource based on position
        int imageIndex = position+1 % imageResources.length;
        int imageResource = imageResources[imageIndex];
        holder.imageViewFurniture.setImageResource(imageResource);
        holder.textViewName.setText(furnitureItem.getName());
        holder.textViewPrice.setText(String.format("Price: $%s", furnitureItem.getPrice()));
        holder.textViewQuantity.setText("Quantity: " + furnitureItem.getQuantity());

        // Set click listener for the Buy Button
        holder.buyButton.setOnClickListener(v -> {
            // Retrieve data from the current furniture item
            String name = furnitureItem.getName();
            String description = furnitureItem.getDescription();
            double price = furnitureItem.getPrice();
            int quantity = furnitureItem.getQuantity();

            // Create an Intent to start the BuyProduct activity
            Intent intent = new Intent(v.getContext(), BuyProduct.class);

            // Put the data into the Intent
            intent.putExtra("name", name);
            intent.putExtra("image", imageResource);
            intent.putExtra("description", description);
            intent.putExtra("quantity", quantity);
            intent.putExtra("price", price);

            // Start the BuyProduct activity
            v.getContext().startActivity(intent);
        });

        return convertView;
    }

    static class ViewHolder {
        ImageView imageViewFurniture;
        TextView textViewName;
        TextView textViewPrice;
        TextView textViewQuantity;
        Button buyButton;

        ViewHolder(View view) {
            imageViewFurniture = view.findViewById(R.id.imageViewFurniture);
            textViewName = view.findViewById(R.id.textViewName);
            textViewPrice = view.findViewById(R.id.textViewPrice);
            textViewQuantity = view.findViewById(R.id.textViewQuantity);
            buyButton = view.findViewById(R.id.buyButton);
        }
    }
}
