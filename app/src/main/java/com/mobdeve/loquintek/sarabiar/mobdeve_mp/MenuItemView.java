package com.mobdeve.loquintek.sarabiar.mobdeve_mp;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuItemView extends LinearLayout {

    private ImageView menuItemImg;
    private TextView menuItemDescTv;
    private TextView menuItemNameTv;


    public MenuItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.menu_item_layout, this);

        this.menuItemImg = findViewById(R.id.menuItemImg);
        this.menuItemNameTv = findViewById(R.id.menuItemNameTv);
        this.menuItemDescTv = findViewById(R.id.menuItemDescTv);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.MenuItemView);
        menuItemImg.setImageDrawable(attributes.getDrawable(R.styleable.MenuItemView_menuItemImg));
        menuItemDescTv.setText(attributes.getString(R.styleable.MenuItemView_menuItemDescTv));
        menuItemNameTv.setText(attributes.getString(R.styleable.MenuItemView_menuItemNameTv));

        attributes.recycle();

    }

    public void setMenuItemImg(ImageView menuItemImg) {
        this.menuItemImg = menuItemImg;
    }

    public void setMenuItemDescTv(TextView menuItemDescTv) {
        this.menuItemDescTv = menuItemDescTv;
    }

    public void setMenuItemNameTv(TextView menuItemNameTv) {
        this.menuItemNameTv = menuItemNameTv;
    }
}
