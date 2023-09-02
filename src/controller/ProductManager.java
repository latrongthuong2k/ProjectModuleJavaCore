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
            }
            System.out.println(DesignTable.getBorderProductTable());

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
        if (selectedCategory == null) {
            System.err.println("Chưa chọn danh mục để thêm !");
            return;
        }
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


        System.out.println("*********** Tiến hành thêm sản phẩm ***********");

        for (int i = 0; i < number; i++) {
            System.out.println(ColorText.WHITE_BRIGHT + "Thêm sản phẩm thứ " + (i + 1) + ColorText.RESET);
            Product product = new Product(selectedCategory.getId());
            List<Product> subProductList = new ArrayList<>();
            for (Category cate : categoryList) {
                subProductList.addAll(cate.getProductList());
            }
            product.inputData(scanner, subProductList, categoryList);
            selectedCategory.getProductList().add(product);
            System.out.println(ColorText.GREEN_BRIGHT + " ^_^ Thêm thành công" + ColorText.RESET);
        }
    }

    /**
     * Cập nhật giá sản phẩm theo mã
     *
     * @param selectedCategory : tham chiếu đên đối tượng danh mục chọn ở trong kho
     * @param scanner          : đối tượng scanner để lấy input
     */
    public void updateProduct(Category selectedCategory, Scanner scanner, List<Category> categoryList) {
        if (selectedCategory == null) {
            System.err.println("Danh mục chưa được chọn !, chọn lệnh '1' để chọn danh mục trước");
            return;
        }
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

            for (Product item : selectedCategory.getProductList()) {
                if (item.getId().toLowerCase().equals(input) || item.getName().equalsIgnoreCase(input)) {
                    System.out.println(ColorText.WHITE_BRIGHT + "Đã tìm thấy sản phẩm : " +
                            ColorText.GREEN_BRIGHT + item.getName() + ColorText.RESET);

                    isFound = true;
                    // update Information
                    System.out.println("***** Tiến hành cập nhật *****");
                    item.inputData(scanner, selectedCategory.getProductList(), categoryList);
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
        if (selectedCategory == null) {
            System.err.println("Danh mục chưa được chọn !, chọn lệnh '1' để chọn danh mục trước");
            return;
        }
        // find product
        if (selectedCategory.getProductList().isEmpty()) {
            System.err.println("Hiện chưa có bất kì sản phẩm nào");
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
        if (categoryList.isEmpty()) {
            System.err.println(ColorText.YELLOW_BRIGHT + "Hiện tại chưa có sản phẩm nào" + ColorText.RESET);
            return;
        }
        List<Product> subList = new ArrayList<>();
        for (Category cate : categoryList) {
            subList.addAll(cate.getProductList());
        }
        System.out.println(DesignTable.statisticForTableProduct(subList.size()));
        // sort
        subList.sort(Comparator.comparing(Product::getName));
        // table
        System.out.println(DesignTable.getBorderProductTable());
        System.out.println(DesignTable.getProductTitle());
        System.out.println(DesignTable.getBorderProductTable());
        for (Product product : subList) {
            product.displayData(categoryList);
        }
        System.out.println(DesignTable.getBorderProductTable());
    }

    /**
     * xắp xếp lợi nhuận cao đến thấp
     */
    public void sortProductByProfitHighToLow(List<Category> categoryList) {
        if (categoryList.isEmpty()) {
            System.err.println(ColorText.YELLOW_BRIGHT + "Hiện tại chưa có sản phẩm nào" + ColorText.RESET);
            return;
        }
        List<Product> subList = new ArrayList<>();
        for (Category cate : categoryList) {
            subList.addAll(cate.getProductList());
        }

        System.out.println(DesignTable.statisticForTableProduct(subList.size()));
        // sort
        subList.sort(Comparator.comparingDouble(Product::getProfit).reversed());
        // table
        System.out.println(DesignTable.getBorderProductTable());
        System.out.println(DesignTable.getProductTitle());
        System.out.println(DesignTable.getBorderProductTable());
        for (Product product : subList) {
            product.displayData(categoryList);
        }
        System.out.println(DesignTable.getBorderProductTable());
    }


    /**
     * Tìm kiếm sản phẩm theo tên hoặc id
     *
     * @param selectedCategory : tham chiếu đên đối tượng danh mục chọn ở trong kho
     * @param categoryList     : lấy danh sách các danh mục để bắt buộc truyền vào hàm displayData
     * @param scanner          : đối tượng scanner để lấy input
     */
    public void findProductByName(Category selectedCategory, List<Category> categoryList, Scanner scanner) {
        if (selectedCategory == null) {
            System.err.println("Danh mục chưa được chọn !, chọn lệnh '1' để chọn danh mục trước");
            return;
        }
        if (categoryList.isEmpty()) {
            System.err.println("Chưa có sản phẩm nào để hiển thị !");
            return;
        }
        System.out.print("-- Nhập từ khoá để tìm kiếm, hoặc nhập 'exit' để thoát lệnh lệnh :");
        while (true) {
            try {
                // input
                String input = scanner.nextLine().toLowerCase().trim();
                if (input.equals("exit")) {
                    System.out.println(ColorText.YELLOW_BRIGHT + "Đã thoát lệnh tìm kiếm" + ColorText.RESET);
                    return;
                }
                List<Product> filteredListProduct = selectedCategory.getProductList().stream()
                        .filter(product ->
                                String.valueOf(product.getId()).toLowerCase().contains(input) ||
                                        product.getName().toLowerCase().contains(input) ||
                                        String.valueOf(product.getExportPrice()).contains(input) ||
                                        String.valueOf(product.getImportPrice()).contains(input))
                        .toList();
                if (filteredListProduct.isEmpty()) {
                    System.err.println("Không có sản phẩm nào tên: " + input);
                }
                System.out.println("-- Sản phẩm tìm kiếm được là :");
                System.out.println(DesignTable.getBorderProductTable());
                System.out.println(DesignTable.getProductTitle());
                System.out.println(DesignTable.getBorderProductTable());
                for (Product product : filteredListProduct) {
                    product.displayData(categoryList);
                }
                System.out.println(DesignTable.getBorderProductTable());
                System.out.println(ColorText.WHITE_BRIGHT +
                        "Nhập tên khác để tìm kiếm, hoặc nhập 'exit' để thoát tìm kiếm: " +
                        ColorText.RESET);

            } catch (Exception e) {
                System.err.println("Lỗi: " + e.getMessage());
            }
        }
    }
}

