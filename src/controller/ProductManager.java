package controller;

import imp.Category;
import imp.Product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ProductManager {
    public ProductManager() {
    }


//    private boolean isInputMatching(Product product, String input) {
//        try {
//            int id = Integer.parseInt(input);
//            return product.getId() == id;
//        } catch (NumberFormatException e) {
//            return product.getName().equals(input);
//        }
//    }

    /**
     * Phương thức setup bảng và hiển thị toàn bộ sản phẩm ra bảng
     *
     * @param categoryList : danh sách chứa các danh mục
     */
    public void displayProduct(List<Category> categoryList) {
        // lọc và thêm toàn bộ sản phẩm vào sublist để hiển thị
        int countAvailable = 0;
        int countNoAvailable = 0;
        List<Product> subList = new ArrayList<>();
        for (Category cate : categoryList) {
            subList.addAll(cate.getProductList());
        }
        if (subList.isEmpty()) {
            System.err.println("Chưa có sản phẩm để hiển thị !");
            return;
        }
        try {
            System.out.println(DesignTable.statisticForTableProduct(subList.size()));
            // HEAD
            System.out.println(DesignTable.getBorderProductTable());
            System.out.println(DesignTable.getProductTitle());
            // BODY
            System.out.println(DesignTable.getBorderProductTable());
            for (Product item : subList) {
                item.displayData(categoryList);
                if (item.isStatus())
                    countAvailable++;
                else
                    countNoAvailable++;
            }
            System.out.println(DesignTable.getBorderProductTable());
            System.out.println();
            System.out.println(" * [ Có : " + ColorText.WHITE_BRIGHT
                    + countAvailable + ColorText.RESET + " sản phẩm "
                    + ColorText.GREEN_BRIGHT + "Còn hàng " + ColorText.RESET + ']');
            System.out.println(" * [ Có : " + ColorText.WHITE_BRIGHT
                    + countNoAvailable + ColorText.RESET + " sản phẩm "
                    + ColorText.YELLOW_BRIGHT + "Ngừng kinh doanh " + ColorText.RESET + ']');
            System.out.println();
        } catch (NullPointerException e) {
            System.err.println("Lỗi : Danh sách danh mục đang chọn bị NUll");
        } catch (Exception e) {
            System.err.println("Lỗi :" + e.getMessage());
        }
    }

    /**
     * Phương thức thêm sản phẩm
     *
     * @param selectedCategory : tham chiếu đên đối tượng danh mục chọn ở trong kho
     * @param scanner          : đối tượng scanner để lấy input
     */
    public void addProduct(Category selectedCategory, List<Category> categoryList, Scanner scanner) {
        System.out.print("-- Nhập số lượng cần thêm, hoặc gõ 'exit' để huỷ thêm: ");
        int number;
        while (true) {
            try {
                String input = scanner.nextLine().toLowerCase().trim();
                if (input.equals("exit")) {
                    System.out.println(ColorText.YELLOW_BRIGHT + "Đã huỷ lệnh thêm" + ColorText.RESET);
                    return;
                } else {
                    number = Integer.parseInt(input);
                }
                if (number <= 0) {
                    throw new RuntimeException("Số lượng không được <= 0");
                }
                break;
            } catch (NumberFormatException e) {
                System.err.println("Lỗi : Số lượng phải là số!");
            } catch (Exception e) {
                System.err.println("Lỗi : " + e.getMessage());
            }
        }

        System.out.println();
        System.out.println("*********** Tiến hành thêm sản phẩm ***********");
        // lấy danh sách allProducts
        List<Product> subProductList = new ArrayList<>();
        categoryList.forEach(category -> subProductList.addAll(category.getProductList()));
        // thêm theo số lượng
        for (int i = 0; i < number; i++) {
            System.out.println(ColorText.WHITE_BRIGHT + "Thêm sản phẩm thứ " + (i + 1) + ColorText.RESET);
            Product product = new Product(selectedCategory.getId());
            // inputData
            product.inputData(scanner, subProductList, categoryList);
            selectedCategory.getProductList().add(product);
            System.out.println();
            System.out.println(ColorText.GREEN_BRIGHT + " ^_^ Thêm thành công " + ColorText.RESET);
            System.out.println();
        }
    }

    /**
     * Cập nhật giá sản phẩm theo mã
     *
     * @param selectedCategory : tham chiếu đên đối tượng danh mục chọn ở trong kho
     * @param scanner          : đối tượng scanner để lấy input
     */
    public void updateProduct(Category selectedCategory, Scanner scanner, List<Category> categoryList) {
        // check empty products
        if (selectedCategory.getProductList().isEmpty()) {
            System.out.println(ColorText.YELLOW_BRIGHT + "Hiện :( không có sản phẩm nào cả ! " +
                    ColorText.RESET);
            return;
        }
        System.out.println();
        boolean isFound = false;
        System.out.print("-- Hãy nhập tên hoặc mã sản phẩm cần tìm để thực hiện cập nhật /" +
                " hoặc nhập 'exit' để thoát lệnh: ");
        while (!isFound) {
            // input
            String input = scanner.nextLine().toLowerCase().trim();

            if (input.equals("exit")) {
                System.out.println(ColorText.YELLOW_BRIGHT + "Đã thoát lệnh cập nhật" + ColorText.RESET);
                return;
            }
            // danh sách sp tổng
            List<Product> subProductList = new ArrayList<>();
            categoryList.forEach(category -> subProductList.addAll(category.getProductList()));

            for (Product item : selectedCategory.getProductList()) {
                if (item.getId().toLowerCase().equals(input) || item.getName().equalsIgnoreCase(input)) {
                    System.out.println(ColorText.WHITE_BRIGHT + "Đã tìm thấy sản phẩm : " +
                            ColorText.GREEN_BRIGHT + item.getName() + ColorText.RESET);
                    System.out.println();// tách một dòng ra
                    isFound = true; // kiểm tra tồn tại
                    // update Information
                    System.out.println("***** Tiến hành cập nhật *****");
                    item.inputData(scanner, subProductList, categoryList);
                    System.out.println(ColorText.GREEN_BRIGHT + " ^_^ Cập nhật thành công" + ColorText.RESET);
                    break;
                }
            }

            if (!isFound) {
                System.err.println(" :(  Sản phẩm không tìm thấy, " +
                        "vui lòng nhập chính xác id hoặc tên của sản phẩm !");
            }
        }
    }

    /**
     * xoá sản phẩm
     *
     * @param selectedCategory : tham chiếu đên đối tượng danh mục chọn ở trong kho
     * @param scanner          : đối tượng scanner để lấy input
     */
    public void deleteProduct(Category selectedCategory, Scanner scanner) {
        // Check empty products
        if (selectedCategory.getProductList().isEmpty()) {
            System.out.println(ColorText.YELLOW_BRIGHT
                    + "Hiện chưa có bất kì sản phẩm nào ở danh mục "
                    + selectedCategory.getName() + ColorText.RESET);
            return;
        }
        boolean isDone = false;
        System.out.print("-- Nhập 'Tên' hoặc 'Id' sản phẩm cần xoá, hoặc nhập 'exit' để thoát lệnh: ");

        while (!isDone) {
            // input
            String input = scanner.nextLine().toLowerCase().trim();
            if (input.equals("exit")) {
                System.out.println(ColorText.YELLOW_BRIGHT + "Đã thoát lệnh xoá" + ColorText.RESET);
                return;
            }
            //
            for (Product item : selectedCategory.getProductList()) {
                if (item.getId().equalsIgnoreCase(input) || item.getName().equalsIgnoreCase(input)) {
                    System.out.println(ColorText.WHITE_BRIGHT + "Đã tìm thấy sản phẩm "
                            + ColorText.GREEN_BRIGHT + item.getName() + ColorText.RESET);
                    // delete case
                    System.out.println(ColorText.YELLOW_BRIGHT +
                            "Bạn có chắc muốn xoá nhấn ( yes/y ) để xoá, hoặc gõ bất kì để thoát : " +
                            ColorText.RESET);
                    String command = scanner.nextLine().trim();
                    if (command.equalsIgnoreCase("yes") || command.equalsIgnoreCase("y")) {
                        selectedCategory.getProductList().remove(item);
                        System.out.println(ColorText.GREEN_BRIGHT + "Xoá thành công" + ColorText.RESET);
                    } else {
                        System.out.println(ColorText.GREEN_BRIGHT + "Đã huỷ lệnh xoá" + ColorText.RESET);
                    }
                    isDone = true;
                    break;
                }
            }
            // not found product
            if (!isDone) {
                System.err.println(" :( Sản phẩm không tìm thấy, " +
                        "vui lòng nhập chính xác id, hoặc tên của sản phẩm !");
            }
        }
    }

    /**
     * xắp xếp A-Z theo tên sản phẩm
     *
     * @param categoryList : danh sách category
     */
    public void sortProductByNameAToZ(List<Category> categoryList) {
        // danh sách tổng các sp
        List<Product> subList = new ArrayList<>();
        categoryList.forEach(category -> subList.addAll(category.getProductList()));
        if (subList.isEmpty()) {
            System.err.println("Hiện tại chưa có sản phẩm nào !");
            return;
        }
        // Table
        System.out.println(DesignTable.statisticForTableProduct(subList.size()));
        // sort
        subList.sort(Comparator.comparing(Product::getName));
        // Head
        System.out.println(DesignTable.getBorderProductTable());
        System.out.println(DesignTable.getProductTitle());
        System.out.println(DesignTable.getBorderProductTable());
        // Body
        for (Product product : subList) {
            product.displayData(categoryList);
        }
        System.out.println(DesignTable.getBorderProductTable());
        // Footer
        System.out.println();
        System.out.println(ColorText.YELLOW_BRIGHT + "[ Bảng sắp xếp các sản phẩm từ 'A-Z' ]" + ColorText.RESET);
        System.out.println();
    }

    /**
     * xắp xếp lợi nhuận cao đến thấp
     */
    public void sortProductByProfitHighToLow(List<Category> categoryList) {
        // danh sách tổng các sp
        List<Product> subList = new ArrayList<>();
        categoryList.forEach(category -> subList.addAll(category.getProductList()));
        if (subList.isEmpty()) {
            System.err.println("Hiện tại chưa có sản phẩm nào !");
            return;
        }
        // Table
        System.out.println(DesignTable.statisticForTableProduct(subList.size()));
        // sort
        subList.sort(Comparator.comparingDouble(Product::getProfit).reversed());
        // Head
        System.out.println(DesignTable.getBorderProductTable());
        System.out.println(DesignTable.getProductTitle());
        System.out.println(DesignTable.getBorderProductTable());
        // Body
        for (Product product : subList) {
            product.displayData(categoryList);
        }
        System.out.println(DesignTable.getBorderProductTable());
        // Footer
        System.out.println();
        System.out.println(ColorText.YELLOW_BRIGHT
                + "[ Bảng sắp xếp các sản phẩm theo lợi nhuận 'cao - thấp' ]"
                + ColorText.RESET);
        System.out.println();
    }


    /**
     * Tìm kiếm sản phẩm theo tên hoặc id
     *
     * @param categoryList : lấy danh sách các danh mục để bắt buộc truyền vào hàm displayData
     * @param scanner      : đối tượng scanner để lấy input
     */
    public void findProductByName(List<Category> categoryList, Scanner scanner) {
        // Hiển tị bảng tổng quát
        displayProduct(categoryList);
        // Danh sách tổng các sp
        List<Product> allProduct = new ArrayList<>();
        categoryList.forEach(category -> allProduct.addAll(category.getProductList()));
        // action
        System.out.print("-- Nhập từ khoá để tìm kiếm, hoặc nhập 'exit' để thoát lệnh lệnh :");
        while (true) {
            try {
                // input
                String input = scanner.nextLine().toLowerCase().trim();
                if (input.equals("exit")) {
                    System.out.println(ColorText.YELLOW_BRIGHT + "Đã thoát lệnh tìm kiếm" + ColorText.RESET);
                    return;
                } else if (input.isEmpty()) {
                    throw new RuntimeException("từ khoá không được để trống !");
                }
                // lọc các sp cần tìm
                List<Product> filteredListProduct = allProduct.stream()
                        .filter(product ->
                                String.valueOf(product.getId()).toLowerCase().contains(input) ||
                                        product.getName().toLowerCase().contains(input) ||
                                        String.valueOf(product.getExportPrice()).contains(input) ||
                                        String.valueOf(product.getImportPrice()).contains(input))
                        .toList();
                if (filteredListProduct.isEmpty()) {
                    throw new RuntimeException("Không có sản phẩm nào tên: " + input); // thông báo và yêu cầu nhập lại
                }
                // Table
                System.out.println(ColorText.WHITE_BRIGHT + "-- Sản phẩm tìm kiếm được là : " + ColorText.RESET);
                // Head
                System.out.println(DesignTable.getBorderProductTable());
                System.out.println(DesignTable.getProductTitle());
                System.out.println(DesignTable.getBorderProductTable());
                // Body
                for (Product product : filteredListProduct) {
                    product.displayData(categoryList);
                }
                System.out.println(DesignTable.getBorderProductTable());
                System.out.println(ColorText.WHITE_BRIGHT +
                        "Nhập tên khác để tìm kiếm, hoặc nhập 'exit' để thoát tìm kiếm: " +
                        ColorText.RESET);

            } catch (RuntimeException e) {
                System.err.println("Lỗi: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Lỗi: " + e.getMessage());
            }
        }
    }
}

