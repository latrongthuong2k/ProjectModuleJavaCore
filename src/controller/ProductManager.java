package controller;

import imp.Category;
import imp.Product;

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
     * @param selectedCategory :  tham chiếu đên đối tượng danh mục chọn ở trong kho
     */
    public void displayProduct(Category selectedCategory, List<Category> categoryList) {
        try {
            // selected category must not Null
            System.out.println("***** Danh sách sản phẩm ở danh mục " + selectedCategory.getName() + " *****");
            // HEAD
            System.out.println(DesignTable.getBorderProductTable());
            System.out.println(DesignTable.getProductTable());
            // BODY
            System.out.println(DesignTable.getBorderProductTable());
            for (Product item : selectedCategory.getProductList()) {
                item.displayData(categoryList);
            }
            System.out.println(DesignTable.getBorderProductTable());

        } catch (NullPointerException e) {
            System.err.println("Lỗi : *_* Danh mục đang chọn bị NUll");
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    /**
     * Phương thức thêm sản phẩm
     *
     * @param selectedCategory : tham chiếu đên đối tượng danh mục chọn ở trong kho
     * @param scanner          :đối tượng scanner để lấy input
     */
    public void addProduct(Category selectedCategory, Scanner scanner) {
        if (selectedCategory != null) {
            System.out.print("-- Nhập số lượng cần thêm /  gõ 'exit' để huỷ thêm:");
            String input = scanner.nextLine().toLowerCase();
            int number;
            while (true) {
                try {
                    if (input.equals("exit")) {
                        System.out.println("Đã huỷ lệnh thêm");
                        return;
                    } else {
                        number = Integer.parseInt(input);
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.err.println("Lỗi: Nhập phải là số!");
                } catch (Exception e) {
                    System.out.println("Error" + e.getMessage());
                }
            }
            if (number < 0) {
                System.err.println("Số nhập không được nhỏ hơn 1");
                return;
            }

            System.out.println("*********** Tiến hành thêm sản phẩm ***********");

            for (int i = 0; i < number; i++) {
                Product product = new Product(selectedCategory.getId());
                product.inputData(scanner, selectedCategory.getProductList());
                selectedCategory.getProductList().add(product);
                System.out.println(ColorText.GREEN_BRIGHT + " ^_^ Thêm thành công" + ColorText.RESET);
            }
        } else {
            System.err.println("Bạn chưa chọn danh mục! Nhập lệnh (0) để chọn danh mục trước.");
        }
    }

    /**
     * Cập nhật giá sản phẩm theo mã
     *
     * @param selectedCategory : tham chiếu đên đối tượng danh mục chọn ở trong kho
     * @param scanner          : đối tượng scanner để lấy input
     */
    public void updateProduct(Category selectedCategory, Scanner scanner) {
        boolean isFound = false;

        while (!isFound) {
            System.out.print("-- Hãy nhập tên hoặc mã sản phẩm cần tìm để thực hiện cập nhật /" +
                    " hoặc nhập 'exit' để thoát lệnh: ");

            if (selectedCategory.getProductList().isEmpty()) {
                System.out.println(ColorText.YELLOW_BRIGHT + "Hiện :( không có sản phẩm nào cả ! " +
                        ColorText.RESET);
                return;
            }
            // input
            String input = scanner.nextLine().toLowerCase();

            if (input.equals("exit")) {
                System.out.println(ColorText.YELLOW_BRIGHT + "Đã thoát lệnh cập nhật" + ColorText.RESET);
                return;
            }

            for (Product item : selectedCategory.getProductList()) {
                if (item.getId().toLowerCase().equals(input) || item.getName().equalsIgnoreCase(input)) {
                    System.out.println(ColorText.GREEN_BRIGHT + "Đã tìm thấy sản phẩm : " +
                            item.getName() + ColorText.RESET);

                    isFound = true;
                    // update Information
                    System.out.println("***** Tiến hành cập nhật *****");
                    item.inputData(scanner, selectedCategory.getProductList());
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
        if (selectedCategory != null) {
            boolean isDone = false;
            while (!isDone) {
                System.out.print("-- Nhập tên hoặc mã sản phẩm cần xoá / hoặc nhập 'exit' để thoát lệnh: ");

                // find product
                if (selectedCategory.getProductList().isEmpty()) {
                    System.out.println(ColorText.YELLOW_BRIGHT +
                            "Hiện :( không có sản phẩm nào cả" +
                            ColorText.RESET);
                    return;
                }
                // input
                String input = scanner.nextLine().toLowerCase();
                if (input.equals("exit")) {
                    System.out.println(ColorText.YELLOW_BRIGHT + "Đã thoát lệnh xoá" + ColorText.RESET);
                    return;
                }
                //
                for (Product item : selectedCategory.getProductList()) {
                    if (item.getId().equalsIgnoreCase(input) || item.getName().equalsIgnoreCase(input)) {
                        System.out.println(ColorText.GREEN_BRIGHT +
                                "Đã tìm thấy sản phẩm " +
                                item.getName() + ColorText.RESET);

                        // delete case
                        System.out.println("Bạn có chắc muốn xoá nhấn ( yes/y ) để xoá, hoặc gõ bất kì để thoát");
                        String command = scanner.nextLine();
                        if (command.equalsIgnoreCase("yes") || command.equalsIgnoreCase("y")) {
                            selectedCategory.getProductList().remove(item);
                            System.out.println(ColorText.GREEN_BRIGHT + "^_^ Xoá thành công" + ColorText.RESET);
                        } else {
                            System.out.println(ColorText.GREEN_BRIGHT + "Đã huỷ lệnh xoá ^_^ " + ColorText.RESET);
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
        } else
            System.err.println("Lỗi : *_* Danh mục đang chọn bị NUll");
    }

    /**
     * xắp xếp A-Z theo tên sản phẩm
     *
     * @param selectedCategory : tham chiếu đên đối tượng danh mục chọn ở trong kho
     */
    public void sortProductByNameAToZ(Category selectedCategory) {
        if (selectedCategory != null) {
            selectedCategory.getProductList().sort(Comparator.comparing(Product::getName));
            System.out.println(ColorText.GREEN_BRIGHT + "-- Xắp xếp sản phẩm A-Z thành công ^_^ --" + ColorText.RESET);
        } else
            System.err.println("Lỗi : *_* Danh mục đang chọn bị NUll !");

    }


    /**
     * xắp xếp lợi nhuận cao đến thấp
     *
     * @param selectedCategory : tham chiếu đên đối tượng danh mục chọn ở trong kho
     */
    public void sortProductByProfitHighToLow(Category selectedCategory) {
        if (selectedCategory != null) {
            selectedCategory.getProductList().sort(Comparator.comparingDouble(Product::getProfit).reversed());
            System.out.println(ColorText.GREEN_BRIGHT +
                    "-- Xắp xếp lợi nhuận cao-thấp thành công ^_^ --" + ColorText.RESET);
        } else
            System.err.println("Lỗi : *_* Danh mục đang chọn bị NUll");

    }


    /**
     * Tìm kiếm sản phẩm theo tên hoặc id
     *
     * @param selectedCategory : tham chiếu đên đối tượng danh mục chọn ở trong kho
     * @param categoryList     : lấy danh sách các danh mục để bắt buộc truyền vào hàm displayData
     * @param scanner          : đối tượng scanner để lấy input
     */
    public void findProductByName(Category selectedCategory, List<Category> categoryList, Scanner scanner) {
        if (selectedCategory != null) {
            System.out.print("-- Nhập tên hoặc id sản phẩm cần tìm kiếm / hoặc nhập 'exit' để thoát lệnh lệnh :");
            // input
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                System.out.println(ColorText.YELLOW_BRIGHT + "Đã thoát lệnh tìm kiếm" + ColorText.RESET);
                return;
            }
            boolean found = false;
            for (Product item : selectedCategory.getProductList()) {
                if (item.getName().equals(input) || item.getId().equals(input)) {
                    System.out.println("-- Sản phẩm tìm kiếm được là :");
                    System.out.println(DesignTable.getBorderProductTable());
                    System.out.println(DesignTable.getProductTable());
                    System.out.println(DesignTable.getBorderProductTable());
                    item.displayData(categoryList);
                    System.out.println(DesignTable.getBorderProductTable());
                    found = true;
                    break;
                }
            }
            if (!found)
                System.err.println(" :( Sản phẩm không tồn tại !");
        } else
            System.err.println("Lỗi : *_* Danh mục đang chọn bị NUll");
    }
}

