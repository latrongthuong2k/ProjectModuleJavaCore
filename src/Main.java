import controller.*;
import imp.Category;

import java.io.*;
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
        String projectDirectory = System.getProperty("user.dir");
        // /Users/latrongthuong/Downloads/DataSave/1-WorkSpace/git/JavaCore/LMS-BT/big-project-module-2

        //System.out.println("Thư mục làm việc hiện tại: " + projectDirectory);

        // folder để lưu các productList của đối tượng Category ở dạng .txt
//        File targetDirectory = new File(projectDirectory + File.separator + "productListRepository");
//        targetDirectory.mkdir(); // Tạo thư mục nếu chưa tồn tại

        // Nạp dữ liệu vào categoryList trong InventoryManagement, từ thư mục ( categoryList.txt ) nếu có
        fileManager.readDataCategory(projectDirectory +
                File.separator + "repository.txt", inventoryManagement);

        //  Run program
        inventoryManagerMenu(scanner,
                inventoryManagement,
                categoryManager,
                productManager,
                fileManager,
                categoryList,
                projectDirectory);
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
            Scanner scanner,
            InventoryManagement inventoryManagement,
            CategoryManager categoryManager,
            ProductManager productManager,
            FileManager fileManager,
            List<Category> categoryList,
            String projectDirectory) {
        int choice;
        do {
            System.out.println(ColorText.YELLOW_BRIGHT + "************ MENU INVENTORY ************");
            System.out.println("* 1. Quản lý danh mục sản phẩm");
            System.out.println("* 2. Quản lý sản phẩm");
            System.out.println("* 3. Thoát");
            System.out.println("******************************");
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
                case 1 -> {
                    goToCategoryManager(scanner,
                            categoryManager,
                            fileManager,
                            categoryList,
                            projectDirectory
                    );
                }
                case 2 -> {
                    goToProductMenu(scanner,
                            inventoryManagement,
                            productManager,
                            categoryManager,
                            fileManager,
                            categoryList,
                            projectDirectory
                    );
                }
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
     * @param scanner         : ref scanner obj in main
     * @param categoryManager : ref categoryManager obj in main
     * @param categoryList    : ref categoryList in main
     */

    public static void goToCategoryManager(
            Scanner scanner,
            CategoryManager categoryManager,
            FileManager fileManager,
            List<Category> categoryList,
            String projectDirectory
    ) {
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
                    "* 6. Thoát (Quay lại kho)");
            System.out.println("*****************************************************************");
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
                case 1 ->
                // Thêm danh mục
                {
                    categoryManager.addCategory(scanner, categoryList);
                    fileManager.writeFileCategory(categoryList, projectDirectory);
                }
                case 2 ->
                    // Hiển thị bảng danh mục
                        categoryManager.displayCategory(categoryList);
                case 3 ->
                // Cập nhật danh mục
                {
                    categoryManager.updateCategory(scanner, categoryList);
                    fileManager.writeFileCategory(categoryList, projectDirectory);
                }
                case 4 ->
                //  Xoá danh mục
                {
                    categoryManager.deleteCategory(scanner, categoryList);
                    fileManager.writeFileCategory(categoryList, projectDirectory);
                }
                case 5 -> {
                    // tìm kiếm danh mục theo tên
                    categoryManager.findCategoryByNameAndDisplay(scanner, categoryList);
                }

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
            Scanner scanner,
            InventoryManagement inventoryManagement,
            ProductManager productManager,
            CategoryManager categoryManager,
            FileManager fileManager,
            List<Category> categoryList,
            String projectDirectory
    ) {

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
                     * 0. Chọn danh mục muốn vào
                     * 1. Thêm mới sản phẩm
                     * 2. Hiển thị thông tin sản phẩm
                     * 3. Cập nhật sản phẩm 
                     * 4. Xóa sản phẩm 
                     * 5. Hiển thị sản phẩm theo A-Z
                     * 6. Hiển thị sản phẩm theo lợi nhuận cao-thấp
                     * 7. Tìm kiếm sản phẩm
                     * 8. Thoát (Quay lại kho)
                    -------------------------------------------------
                     """);
            System.out.print("-- Chọn một chức năng (0-8): ");
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
                }
            }
            scanner.nextLine(); // xoá bộ nhớ đệm scanner
            switch (productChoice) {
                case 0 -> {
                    System.out.println("------------- Danh sách danh mục -------------");
                    /**
                     * selectedCategory sẽ tham chiếu đến một item bên trong categoryList
                     * mà hàm selectCategory đã trả về sau khi lọc từ categoryList
                     */
                    selectedCategory = categoryManager.selectCategory(categoryList, scanner);
                }
                case 1 ->
                // Thêm sản phẩm
                {
                    productManager.addProduct(selectedCategory, scanner);
                    fileManager.writeFileCategory(categoryList, projectDirectory);
                }

                case 2 ->
                    // Hiển thị san phẩm
                        productManager.displayProduct(selectedCategory, categoryList);

                case 3 -> {
                    // Cập nhật sản phẩm
                    productManager.updateProduct(selectedCategory, scanner);
                    fileManager.writeFileCategory(categoryList, projectDirectory);
                }
                case 4 -> {
                    // xoá sản phẩm
                    productManager.deleteProduct(selectedCategory, scanner);
                    fileManager.writeFileCategory(categoryList, projectDirectory);
                }
                case 5 -> {
                    // sắp xếp sản phẩm từ A-Z
                    productManager.sortProductByNameAToZ(selectedCategory);
                }
                case 6 -> {
                    // sắp xếp sản phẩm theo lợi nhuận từ thấp đến cao
                    productManager.sortProductByProfitHighToLow(selectedCategory);

                }
                case 7 -> {
                    // Tìm sản phẩm theo tên
                    productManager.findProductByName(selectedCategory, categoryList, scanner);
                }
                case 8 ->
                    // trở về kho
                        System.out.println(ColorText.GREEN_BRIGHT +
                                " Đã quay về kho" + ColorText.RESET);
                default -> System.out.println(" Lựa chọn không hợp lệ. Vui lòng chọn lại ! ");
            }
        }
        while (productChoice != 8);
    }
}
