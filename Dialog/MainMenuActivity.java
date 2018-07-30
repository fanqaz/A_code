package cn.com.geekplus.app.rf.module.mainmenu;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

import cn.com.geekplus.app.rf.Constants;
import cn.com.geekplus.app.rf.R;
import cn.com.geekplus.app.rf.databinding.MainMenuActivityBinding;
import cn.com.geekplus.app.rf.modle.MainMenuEntity;
import cn.com.geekplus.app.rf.modle.MainMenuEnum;
import cn.com.geekplus.app.rf.modle.OnItemClickCallback;
import cn.com.geekplus.app.rf.module.basic.BaseActivity;
import cn.com.geekplus.app.rf.module.onshelf.OnShelfActivity;
import cn.com.geekplus.app.rf.module.packagescan.PackageCarrierActivity;
import cn.com.geekplus.app.rf.module.pick.ManualPickActivity;
import cn.com.geekplus.app.rf.module.replenishpick.ReplenishGetTaskActivity;
import cn.com.geekplus.app.rf.module.stockquery.QueryStockActivity;
import cn.com.geekplus.app.rf.widgets.MenuDialogFragment;
import cn.com.geekplus.app.rf.widgets.MenuDialogFragment.ItemButton;

import static cn.com.geekplus.app.rf.modle.MainMenuEnum.MAINMENU_DOWN_SHELF;
import static cn.com.geekplus.app.rf.modle.MainMenuEnum.MAINMENU_ONSHELF;
import static cn.com.geekplus.app.rf.modle.MainMenuEnum.MAINMENU_PACKAGE_SCAN;
import static cn.com.geekplus.app.rf.modle.MainMenuEnum.MAINMENU_STOCK_CHECK;

/**
 * 主菜单
 */
public class MainMenuActivity extends BaseActivity {

    private MainMenuAdapter adapter;

    public static void start(Context activity) {
        Intent intent = new Intent(activity, MainMenuActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainMenuActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.main_menu_activity);
        binding.setCallback(this);

        adapter = new MainMenuAdapter(mOnClickCallback);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        binding.productsList.setLayoutManager(layoutManager);
//        binding.productsList.addItemDecoration(new RecycleViewDivider(
//                this, GridLayoutManager.HORIZONTAL, 10, getResources().getColor(R.color.color_black_p40)));
        binding.productsList.setAdapter(adapter);

        adapter.setProductList(setMainMenuIcon(Constants.getMainMenuData()));
    }


    /**
     * 上架
     */
    private void onShelf() {

        MenuDialogFragment dialogFragment = null;
        if (dialogFragment == null) {
            dialogFragment = (MenuDialogFragment) Fragment.instantiate(this,
                    MenuDialogFragment.class.getName());

            dialogFragment.setItems(getOnShelfDialogData(),
                    new MenuDialogFragment.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            if (position == 0) {

                                ActivityUtils.startActivity(OnShelfActivity.class);
                            }
                        }
                    });
        }
        dialogFragment.show(getSupportFragmentManager(), "MenuDialogFragment");
    }

    /**
     * 上架dialog
     *
     * @return
     */
    private List<ItemButton> getOnShelfDialogData() {

        List<ItemButton> list = new ArrayList<>();

        ItemButton itemButton1 = new ItemButton(this);
        itemButton1.setText(getResources().getString(R.string.random_on_shelf));
        list.add(itemButton1);

        return list;
    }


    /**
     * 下架
     */
    private void downShelf() {
        MenuDialogFragment dialogFragment = null;
        if (dialogFragment == null) {
            dialogFragment = (MenuDialogFragment) Fragment.instantiate(this,
                    MenuDialogFragment.class.getName());

            dialogFragment.setItems(getDownShelfDialogData(),
                    new MenuDialogFragment.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            if (position == 0) {
                                ManualPickActivity.start(MainMenuActivity.this);
                            } else if (position == 1) {
                                ReplenishGetTaskActivity.start(MainMenuActivity.this);
                            }
                        }
                    });
        }
        dialogFragment.show(getSupportFragmentManager(), "MenuDialogFragment");
    }

    /**
     * 下架dialog
     *
     * @return
     */
    private List<ItemButton> getDownShelfDialogData() {

        List<ItemButton> list = new ArrayList<>();
        list.add(new ItemButton(this).setText(getResources().getString(R.string.pick_up_goods)));
        list.add(new ItemButton(this).setText(getResources().getString(R.string.down_shelf_goods)));
        return list;
    }

    /**
     * 库存查询
     */
    private void stockCheck() {
        ActivityUtils.startActivity(QueryStockActivity.class);
    }

    /**
     * 包裹扫描
     */
    private void packageScan() {
        ActivityUtils.startActivity(PackageCarrierActivity.class);
    }

    /**
     * item点击
     */
    private final OnItemClickCallback mOnClickCallback = new OnItemClickCallback<MainMenuEntity>() {
        @Override
        public void onClick(MainMenuEntity entity) {
            switch (MainMenuEnum.getEnumByKey(entity.getId())) {
                case MAINMENU_ONSHELF:
                    onShelf();
                    break;
                case MAINMENU_DOWN_SHELF:
                    downShelf();
                    break;
                case MAINMENU_STOCK_CHECK:
                    stockCheck();
                    break;
                case MAINMENU_PACKAGE_SCAN:
                    packageScan();
                    break;
            }
        }
    };

    private List<MainMenuEntity> setMainMenuIcon(List<MainMenuEntity> data) {
        if (data == null || data.size() == 0) {
            //  当没有数据时显示
            if(data==null){
                data=new ArrayList<>();
            }
            MainMenuEntity entity = new MainMenuEntity();
            entity.setResImgId(R.drawable.on_shelf);
            entity.setResImgBackgroundId(R.drawable.shape_circle_on_shelf);
            entity.setContent(MAINMENU_ONSHELF.getValue());
            data.add(entity);
            return data;
        }

        for (MainMenuEntity entity : data) {
            switch (MainMenuEnum.getEnumByKey(entity.getId())) {
                case MAINMENU_ONSHELF:
                    entity.setResImgId(R.drawable.on_shelf);
                    entity.setResImgBackgroundId(R.drawable.shape_circle_on_shelf);
                    entity.setContent(MAINMENU_ONSHELF.getValue());
                    break;
                case MAINMENU_DOWN_SHELF:
                    entity.setResImgId(R.drawable.down_shelf);
                    entity.setResImgBackgroundId(R.drawable.shape_circle_down_shelf);
                    entity.setContent(MAINMENU_DOWN_SHELF.getValue());
                    break;
                case MAINMENU_STOCK_CHECK:
                    entity.setResImgId(R.drawable.stock_check);
                    entity.setResImgBackgroundId(R.drawable.shape_circle_stock_check);
                    entity.setContent(MAINMENU_STOCK_CHECK.getValue());
                    break;
                case MAINMENU_PACKAGE_SCAN:
                    entity.setResImgId(R.drawable.package_scan);
                    entity.setResImgBackgroundId(R.drawable.shape_circle_package_scan);
                    entity.setContent(MAINMENU_PACKAGE_SCAN.getValue());
                    break;
            }
        }

        return data;


    }

}
