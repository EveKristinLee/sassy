package com.example.sassydesign;

import android.app.AlertDialog;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import lib.kingja.switchbutton.SwitchMultiButton;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    static ArrayList<Item> items = new ArrayList<Item>();


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int ViewType){
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_daily_list, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Item item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Item item){
        items.add(item);
    }

    public void removeItem(){
        items.clear();
        this.notifyDataSetChanged();
    }

    public void setItems(ArrayList<Item> items){
        this.items = items;
    }

    public Item getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, Item item){
        items.set(position, item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        TextView itemTitle;
        TextView itemCacheOrCard;
        TextView itemDetail;
        TextView itemCategory;
        TextView itemPrice;
        TextView itemQuantity;

        public ViewHolder(View itemView){
            super(itemView);

            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemCacheOrCard = itemView.findViewById(R.id.itemCacheOrCard);
            itemDetail = itemView.findViewById(R.id.itemDetail);
            itemCategory = itemView.findViewById(R.id.itemCategory);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
            
            //리스너 구현
            itemView.setOnCreateContextMenuListener(this);
        }


        //꾹 누르면 수정, 삭제 메뉴 생성
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "수정");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");

            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1001: //수정
                        AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());

                        // 수정 화면 넘어가기
                        /*
                        View view = LayoutInflater.from(itemView.getContext()).inflate(R.layout.hand_add, null, false);
                        builder.setView(view);

                        final SwitchMultiButton switchInOutButton = (SwitchMultiButton) view.findViewById(R.id.switchInOutButton);
                        final SwitchMultiButton switchCCButton = (SwitchMultiButton) view.findViewById(R.id.switchCCButton);
                        final Button dateButton = (Button) view.findViewById(R.id.dateButton);
                        final EditText itemDetailTitle = (EditText) view.findViewById(R.id.itemDetailTitle);

                        final EditText productName = (EditText) view.findViewById(R.id.productName);
                        final EditText productCost = (EditText) view.findViewById(R.id.productCost);
                        final EditText productQuantity = (EditText) view.findViewById(R.id.productQuantity);

                        switchInOutButton.setSelectedTab(items.get(getAdapterPosition()).get)

                        for(int i=0; i<items.size(); i++) {
                            productName.setText(items.get(getAdapterPosition()).get);

                        }
                         */


                        final AlertDialog dialog = builder.create();

                        //수정 버튼을 누르면 현재 UI에 입력되어있는 내용으로

                        dialog.show();
                        break;

                    case 1002://삭제
                        items.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), items.size());

                        break;

                }
                return true;
            }
        };



        public void setItem(Item item){

            String items="";
            String categories = "";
            String quantities = "";
            String price = "";

            itemTitle.setText(item.getTitle());
            itemCacheOrCard.setText(item.getCacheOrCard());
            for(int i = 0; i < item.subCursor; i++){

                if (i != item.subCursor) {
                    items += item.getItemList().get(i)+"\n";
                    categories += item.getCategoryList().get(i)+"\n";
                    quantities += item.getQuantityList().get(i)+"\n";
                    price += item.getPriceList().get(i)+"\n";
                }
                else{
                    items += item.getItemList().get(i);
                    categories += item.getCategoryList().get(i);
                    quantities += item.getQuantityList().get(i);
                    price += item.getPriceList().get(i);
                }

                itemDetail.setText(items);
                itemCategory.setText(categories);
                itemPrice.setText(price);
                itemQuantity.setText(quantities);

            }

        }

    }

}