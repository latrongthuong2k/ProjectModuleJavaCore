package controller;

import imp.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * 在庫管理クラス
 */
public class InventoryManagement {

    private final List<Category> categoryList;

    public List<Category> getCategoryList() {
        return categoryList;
    }

    /**
     * コンストラクターメソッド
     * 新しい在庫管理オブジェクトを作成します。
     */
    public InventoryManagement() {
        categoryList = new ArrayList<>();
    }

}
