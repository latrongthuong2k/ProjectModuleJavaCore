package view;

import controller.*;
import imp.Category;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Instance inventoryManagement
        InventoryManagement inventoryManagement = new InventoryManagement();

        // Instance of inventoryManagement
        List<Category> categoryList = inventoryManagement.getCategoryList();

        // Instance managers
        ProductManager productManager = new ProductManager();
        CategoryManager categoryManager = new CategoryManager();
        FileManager fileManager = new FileManager();

        // Selected category : tham chiếu đên đối tượng cần tìm trong category list
        Scanner scanner = new Scanner(System.in);

        // đường dẫn tương đối đến thư mục gốc của dự án ( trên máy khác thì đường dẫn sẽ khác một chút )
        // String projectDirectory = System.getProperty("user.dir");
        // /Users/latrongthuong/Downloads/DataSave/1-WorkSpace/git/JavaCore/LMS-BT/big-project-module-2

        // Nạp dữ liệu vào categoryList trong InventoryManagement, từ thư mục ( categoryList.txt ) nếu có
        // fileManager.readDataCategory(projectDirectory +
        // File.separator + "repository.txt", inventoryManagement);

        //

        fileManager.readAll(inventoryManagement);

        //  Run program
        inventoryManagerMenu(scanner,
                inventoryManagement,
                categoryManager,
                productManager,
                fileManager,
                categoryList);
    }

    /**
     * -------------------------------------------------------------------------
     */

    /**
     * Phương thức khởi động MENU Inventory management
     *
     * @param scanner             : tham chiếu của đối tượng scanner
     * @param inventoryManagement : tham chiếu của inventoryManagement obj
     * @param categoryManager     : tham chiếu của categoryManager obj
     * @param productManager      : tham chiếu của productManager obj
     * @param fileManager         : tham chiếu của fileManager obj
     * @param categoryList        : tham chiếu của categoryList obj
     */
    public static void inventoryManagerMenu(
            Scanner scanner, InventoryManagement inventoryManagement,
            CategoryManager categoryManager, ProductManager productManager,
            FileManager fileManager, List<Category> categoryList) {
        int choice;
        do {
            System.out.println(ColorText.YELLOW_BRIGHT +
                    "************ MENU INVENTORY ************\n" +
                    "* 1. Quản lý danh mục sản phẩm\n" +
                    "* 2. Quản lý sản phẩm\n" +
                    "* 3. Thoát\n" +
                    "****************************************");
            System.out.println();
            System.out.print("Chọn một chức năng (1-3): " + ColorText.RESET);
            /**
             * Check lỗi Input cho choice
             */
            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    break;
                } else {
                    System.err.println("Lệnh chọn không hợp lệ! Vui lòng nhập số.");
                    scanner.nextLine();
                }
            }
            switch (choice) {
                case 1 -> goToCategoryManager(scanner,
                        categoryManager,
                        fileManager,
                        categoryList
                );
                case 2 -> goToProductMenu(scanner,
                        inventoryManagement,
                        productManager,
                        categoryManager,
                        fileManager,
                        categoryList
                );
                case 3 -> {
                    System.out.println("Đã thoát menu.");
                    System.exit(0);
                }
                default -> System.err.println(" Lựa chọn không hợp lệ. Vui lòng chọn lại!");
            }
        }
        while (true);
    }

    /**
     * Phương thức mở MENU Category
     *
     * @param scanner         : đối tượng Scanner
     * @param categoryManager : đổi tượng quản lý categories
     * @param fileManager     : đối tượng quản lý đọc ghi file
     * @param categoryList    : ref categoryList in main
     */
    public static void goToCategoryManager(
            Scanner scanner, CategoryManager categoryManager, FileManager fileManager,
            List<Category> categoryList) {
        int categoryChoice;
        // Imp
        do {
            System.out.println(ColorText.CYAN_BRIGHT +
                    "*********************** CATEGORY MENU ***********************\n" +
                    "* 1. Thêm mới danh mục\n" +
                    "* 2. Hiển thị thông tin các danh mục\n" +
                    "* 3. Cập nhật danh mục\n" +
                    "* 4. Xóa danh mục (Danh mục chưa chứa sản phẩm)\n" +
                    "* 5. Tìm kiếm danh mục theo tên\n" +
                    "* 6. Thoát (Quay lại kho)\n" +
                    "*************************************************************");

            System.out.println();
            System.out.print("Chọn một chức năng (1-6): " + ColorText.RESET);

            /**
             * Check input để không nhập gì ngoài số
             */
            while (true) {
                if (scanner.hasNextInt()) {
                    categoryChoice = scanner.nextInt();
                    break;
                } else {
                    System.err.println(" * Lệnh chọn không hợp lệ! Vui lòng nhập số.");
                    scanner.nextLine();
                }
            }
            scanner.nextLine();
            switch (categoryChoice) {
                case 1 -> {
                    // Thêm danh mục / ghi file
                    categoryManager.addCategory(scanner, categoryList);
                    fileManager.writeFileCategory2(categoryList);
                }
                case 2 ->
                    // Hiển thị bảng danh mục
                        categoryManager.displayCategory(categoryList);
                case 3 -> {
                    // Cập nhật danh mục / ghi file
                    categoryManager.updateCategory(scanner, categoryList);
                    fileManager.writeFileCategory2(categoryList);
                }
                case 4 -> {
                    //  Xoá danh mục / ghi file
                    if (categoryManager.deleteCategory(scanner, categoryList))
                        fileManager.writeFileCategory2(categoryList);
                }
                case 5 ->
                    // tìm kiếm danh mục theo tên
                        categoryManager.findCategoryByNameAndDisplay(scanner, categoryList);
                case 6 ->
                    // Quay về kho
                        System.out.println(ColorText.GREEN_BRIGHT +
                                " Đã quay về kho" + ColorText.RESET);
                default -> System.err.println(" * Lựa chọn không hợp lệ. Vui lòng chọn lại !");
            }
        } while (categoryChoice != 6);
    }

    //------------------------------------------------------------------------

    /**
     * Phương thức mở MENU product
     *
     * @param scanner             : tham chiếu đối tượng scanner trong main
     * @param inventoryManagement : tham chiếu đối tượng inventoryManagement trong main
     * @param productManager      : tham chiếu productManager được tạo trong main
     * @param categoryManager     : tham chiếu categoryManager được tạo trong main
     * @param categoryList        : tham chiếu categoryList được tạo trong main
     */
    public static void goToProductMenu(
            Scanner scanner, InventoryManagement inventoryManagement,
            ProductManager productManager, CategoryManager categoryManager,
            FileManager fileManager, List<Category> categoryList) {
        // Instance selectedCategory
        Category selectedCategory = null;
        /**
         * Quay về menu kho nếu chưa có danh mục nào để thêm sản phẩm
         */
        if (inventoryManagement.getCategoryList().isEmpty()) {
            System.err.println("Truy cập bị từ chối vì hiện tại chưa có danh mục nào !");
            return;
        }
        int productChoice;
        /**
         * MENU PRODUCT MANAGEMENT
         */
        do {
            System.out.println("""
                     ----------------- PRODUCT MENU -----------------
                     * 1. Thêm mới sản phẩm
                     * 2. Hiển thị thông tin sản phẩm
                     * 3. Cập nhật sản phẩm
                     * 4. Xóa sản phẩm
                     * 5. Hiển thị tên sản phẩm theo A-Z
                     * 6. Hiển thị giá sản phẩm theo lợi nhuận cao-thấp
                     * 7. Tìm kiếm sản phẩm
                     * 8. Thoát (Quay lại kho)
                    -------------------------------------------------
                     """);
            System.out.print("-- Chọn một chức năng (1-8): ");
            /**
             * Nhập lệnh
             * -> check phải là số
             */
            while (true) {
                if (scanner.hasNextInt()) {
                    productChoice = scanner.nextInt();
                    break;
                } else {
                    System.err.println(" * Lệnh chọn không hợp lệ! Vui lòng nhập số.");
                    scanner.nextLine();
                }
            }
            scanner.nextLine(); // xoá bộ nhớ đệm scanner
            switch (productChoice) {
                case 1 -> {
                    // chọn danh mục để thêm
                    selectedCategory = categoryManager.selectCategory(categoryList, scanner);
                    // Thêm sản phẩm / ghi file
                    productManager.addProduct(selectedCategory, categoryList, scanner);
                    fileManager.writeFileCategory2(categoryList);
                }
                case 2 ->
                    // Hiển thị sản phẩm
                        productManager.displayProduct(categoryList);
                case 3 -> {
                    // chọn danh mục để cập nhật
                    selectedCategory = categoryManager.selectCategory(categoryList, scanner);
                    // Cập nhật sản phẩm / ghi file
                    productManager.updateProduct(selectedCategory, scanner, categoryList);
                    fileManager.writeFileCategory2(categoryList);
                }
                case 4 -> {
                    // chọn danh mục để xoá
                    selectedCategory = categoryManager.selectCategory(categoryList, scanner);
                    // xoá sản phẩm / ghi file
                    productManager.deleteProduct(selectedCategory, scanner);
                    fileManager.writeFileCategory2(categoryList);
                }
                case 5 ->
                    // sắp xếp tên sản phẩm từ A-Z
                        productManager.sortProductByNameAToZ(categoryList);
                case 6 ->
                    // sắp xếp giá sản phẩm theo lợi nhuận từ thấp đến cao
                        productManager.sortProductByProfitHighToLow(categoryList);
                case 7 ->
                    // Tìm sản phẩm theo tên
                        productManager.findProductByName(selectedCategory, categoryList, scanner);
                case 8 ->
                    // trở về kho
                        System.out.println(ColorText.GREEN_BRIGHT +
                                " Đã quay về kho" + ColorText.RESET);
                default -> System.err.println(" Lựa chọn không hợp lệ. Vui lòng chọn lại ! ");
            }
        }
        while (productChoice != 8);
    }
}
