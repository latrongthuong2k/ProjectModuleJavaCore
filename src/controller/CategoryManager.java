package controller;


import imp.Category;
import imp.Product;

import java.util.List;
import java.util.Scanner;

public class CategoryManager {

    // NOTE : 1 Category Manager have 1 list Category

    /**
     * Constructor của categoryManager
     */
    public CategoryManager() {
    }

    private boolean isInputMatching(Category category, String input) {
        try {
            int id = Integer.parseInt(input);
            return category.getId() == id;
        } catch (NumberFormatException e) {
            return category.getName().toLowerCase().equals(input);
        }
    }

    private boolean isHasProducts(Category category) {
        boolean isExist = false;
        if (!category.getProductList().isEmpty()) {
            System.err.println(
                    "Không thể xoá danh mục hiện đang có sản phẩm !");
            System.out.println();
            isExist = true;
        }
        return isExist;
    }

    /**
     * Phương thức hiển thị toàn bộ các category có trong 1 danh sách của Inventory
     *
     * @param categoryList : tham chiếu đến một danh sách category nào đó của Inventory
     */

    public void displayCategory(List<Category> categoryList) {
        if (categoryList.isEmpty()) {
            System.err.println("Chưa có danh mục nào để hiển thị !");
            return;
        }
        int countActive = 0;
        int countInActive = 0;
        System.out.println(DesignTable.statisticForTableCategory(categoryList.size()));
        // HEAD
        System.out.println(DesignTable.getBorderCategoryTable());
        System.out.println(DesignTable.getCategoryTitle());
        System.out.println(DesignTable.getBorderCategoryTable());
        // BODY

        for (Category item : categoryList) {
            item.displayData();
            if (item.isStatus())
                countActive++;
            else
                countInActive++;
        }
        System.out.println(DesignTable.getBorderCategoryTable());
        System.out.println();
        System.out.println(" * [ Có : " + ColorText.WHITE_BRIGHT
                + countActive + ColorText.RESET + " danh mục "
                + ColorText.GREEN_BRIGHT + "Active " + ColorText.RESET + ']');
        System.out.println(" * [ Có : " + ColorText.WHITE_BRIGHT
                + countInActive + ColorText.RESET + " danh mục "
                + ColorText.YELLOW_BRIGHT + "InActive " + ColorText.RESET + ']');
        System.out.println();
        //
    }


    /**
     * Phương thức thêm category
     *
     * @param scanner      : Đối tượng scanner
     * @param categoryList : Danh sách các category của đối tượng Inventory ( Kho )
     */
    public void addCategory(Scanner scanner, List<Category> categoryList) {
        System.out.print("-- Nhập số lượng cần thêm, hoặc  gõ 'exit' để huỷ thêm: ");
        int number = 0;
        boolean isNumber = false;
        while (!isNumber) {
            try {
                String input = scanner.nextLine().toLowerCase().trim();
                // input
                if (input.equals("exit")) {
                    System.out.println(ColorText.YELLOW_BRIGHT + "Đã thoát lệnh thêm" + ColorText.RESET);
                    return;
                } else {
                    number = Integer.parseInt(input);
                }
                if (number <= 0) {
                    throw new Exception("Số lượng không được nhỏ hơn hoặc bằng 0 ");
                }
                isNumber = true;
                //error
            } catch (NumberFormatException e) {
                System.err.println("Lỗi: số lượng phải là số nguyên dương !");
            } catch (Exception e) {
                System.err.println("Lỗi " + e.getMessage());
            }
        }
        /*
         * Vòng lặp thêm dựa theo số lượng yêu cầu
         * -> Tạo mới
         * -> Thêm vào categoryList
         * -> Thông báo thành công
         */
        for (int i = 0; i < number; i++) {
            Category category = new Category();
            category.inputData(scanner, categoryList);
            categoryList.add(category);
            System.out.println();
            System.out.println(ColorText.GREEN_BRIGHT + "-- Thêm thành công ^_^ -- " + ColorText.RESET);
            System.out.println();
        }
    }


    /**
     * Phương thức Update category
     *
     * @param scanner      : tham chiếu đến scanner bên ngoài
     * @param categoryList : tham chiếu đến một danh sách category nào đó của Inventory
     */
    public void updateCategory(Scanner scanner, List<Category> categoryList) {
        if (categoryList.isEmpty()) {
            System.err.println("Chưa có danh mục nào để cập nhật !");
            return;
        }
        System.out.print("-- Nhập 'Tên' hoặc 'Id' của category để cập nhật, " +
                "hoặc nhập 'exit' để thoát lệnh: ");
        boolean found = false;
        while (!found) {
            String input = scanner.nextLine().toLowerCase().trim();
            try {
                // Thoát
                if (input.equals("exit")) {
                    System.out.println(ColorText.YELLOW_BRIGHT + "Đã thoát lệnh cập nhật" + ColorText.RESET);
                    return;
                } else if (input.isEmpty()) {
                    System.err.println("Không được để trống ! ");
                    continue;
                }
                // Dò tìm
                for (Category item : categoryList) {
                    if (isInputMatching(item, input)) {
                        System.out.println(ColorText.WHITE_BRIGHT + "Đã tìm thấy danh mục tên: "
                                + ColorText.GREEN_BRIGHT + item.getName() +
                                ", bắt đầu tiến hành cập nhật" + ColorText.RESET);
                        item.inputData(scanner, categoryList);
                        System.out.println();
                        System.out.println(ColorText.GREEN_BRIGHT + " ^_^ Cập nhật thành công" + ColorText.RESET);
                        System.out.println();
                        return;
                    }
                }
                if (!found)
                    System.err.println("Không có category 'Id' hoặc 'Tên': " + input + ", " +
                            "xin vui lòng nhập lại hoặc nhập 'exit' để thoát !");
                // không tìm được
            } catch (RuntimeException e) {
                System.err.println("Lỗi: " + e.getMessage());
            }
        }
    }

    /**
     * カテゴリを削除するメソッド
     *
     * @param scanner      : đối tượng scanner
     * @param categoryList : danh sách category được truyền vào từ Inventory bên ngoài
     */
    public boolean deleteCategory(Scanner scanner, List<Category> categoryList) {
        // Chạy các logic bên trong nếu categoryList không bị null, null thì thông báo ra
        if (categoryList.isEmpty()) {
            System.err.println("Chưa có danh mục nào để xoá ! :( ");
            return false;
        }
        System.out.print("-- Nhập 'Id' hoặc 'Tên' danh mục cần xoá, hoặc nhập 'exit' để thoát lệnh': ");
        String input;
        boolean productListOfCategoryIsEmpty = true; // không có sản phẩm
        boolean isFound = false;

        // 入力を受け取るループ : vòng lặp tìm kiếm
        while (!isFound) {
            input = scanner.nextLine().toLowerCase().trim();
            try {
                // input
                if (input.equals("exit")) {
                    System.out.println(ColorText.YELLOW_BRIGHT + "Đã thoát lệnh xoá" + ColorText.RESET);
                    return false;
                } else if (input.isEmpty()) {
                    System.err.println("Không được để trống ! ");
                    continue;
                }
                // find category
                for (Category item : categoryList) {
                    if (isInputMatching(item, input)) {
                        if (!isHasProducts(item)) { // không có sản phẩm nào -> xoá
                            askForDeleteCallBackFunc(item, scanner, categoryList);
                        } else // có sản phẩm -> no
                            productListOfCategoryIsEmpty = false; // có sp
                        isFound = true;
                    }
                    if (isFound)
                        break;
                }
                if (!isFound)
                    System.err.println(" :( Không tìm thấy danh mục :" + input + ", " +
                            "xin hãy nhập lại hoặc nhập 'exit' để thoát !");
                // error
            } catch (Exception e) {
                System.err.println("Error" + e.getMessage());
            }
        }
        return productListOfCategoryIsEmpty;
    }


    /**
     * Phương thức phụ trợ cho phương thức deleteCategory
     *
     * @param item         : tham chiếu đối tượng truyền vào để xoá
     * @param scanner      : tham chiếu scanner
     * @param categoryList : tham chiếu danh sách thực hiện xoá lên
     */
    public void askForDeleteCallBackFunc(Category item, Scanner scanner, List<Category> categoryList) {

        System.out.println(ColorText.WHITE_BRIGHT +
                "Đã tìm thấy danh mục tên: " + ColorText.GREEN_BRIGHT + item.getName() + ColorText.RESET);
        System.out.print(ColorText.YELLOW_BRIGHT +
                "Bạn có chắc muốn xoá nhấn ( yes ) để xoá, hoặc ( no ) để thoát lệnh: " + ColorText.RESET);
        do {
            String command = scanner.nextLine().toLowerCase().trim();

            if (command.equals("yes")) {
                categoryList.remove(item);
                System.out.println(ColorText.GREEN_BRIGHT +
                        "Đã xoá danh mục tên: " + ColorText.RESET + item.getName());
                break;
            } else if (command.equals("no")) {
                System.out.println(ColorText.GREEN_BRIGHT +
                        "Đã huỷ lệnh xoá : " + ColorText.RESET + item.getName());
                break;
            } else {
                System.err.println(" *_* Nhập không đúng lệnh, hãy nhập lại lệnh ( yes / no ) ");
            }
        } while (true);
    }


    /**
     * @param categoryList : カテゴリリスト : tham chiếu danh sách category
     * @param scanner      : 入力を受け取るスキャナー : tham chiếu đối tượng scanner
     * @return : 選択されたカテゴリのインスタンス : Khi kết thúc trả về một Category
     */
    public Category selectCategory(List<Category> categoryList, Scanner scanner) {
        if (categoryList.isEmpty()) {
            System.err.println("Chưa có danh mục nào để chọn! :(");
            return null;
        }
        // statistic
        System.out.println(DesignTable.statisticForTableCategory(categoryList.size()));
        // bảng
        System.out.println(DesignTable.getBorderCategoryTable());
        System.out.println(DesignTable.getCategoryTitle());
        System.out.println(DesignTable.getBorderCategoryTable());
        categoryList.forEach(Category::displayData);
        System.out.println(DesignTable.getBorderCategoryTable());
        System.out.println();
        //
        boolean isFound = false;
        Category foundCategory = null;
        System.out.println("Nhập 'Tên' hoặc 'Id' danh mục để thực hiện thao tác, hoặc nhập 'exit' để thoát lệnh :");
        while (!isFound) {
            String input = scanner.nextLine().toLowerCase().trim();
            try {
                // Khi nhập exit để thoát lệnh
                if (input.equals("exit")) {
                    System.out.println(ColorText.YELLOW_BRIGHT + "Đã thoát lệnh chọn danh mục" + ColorText.RESET);
                    return null;
                } else if (input.isEmpty()) {
                    System.err.println("Không được để trống ! ");
                    continue;
                }
                for (Category item : categoryList) {
                    if (isInputMatching(item, input)) {
                        // tham gán tham chiếu đến item trong list
                        foundCategory = item;
                        isFound = true;
                    }
                    if (isFound)
                        break;
                }
                if (!isFound)
                    System.err.println(":( Không tìm thấy danh mục : " + input + ", xin hãy nhập lại!");
            } catch (Exception e) {
                System.out.println("Error" + e.getMessage());
            }
            if (foundCategory != null) {
                // thông báo tìm được xong và check true để thoát khỏi vòng lặp
                System.out.println(ColorText.WHITE_BRIGHT + "Danh mục hiện tại đang chọn là : "
                        + ColorText.GREEN_BRIGHT + foundCategory.getName() + ColorText.RESET);
            }
        }
        //statistic
        String border = "-";
        System.out.println(ColorText.YELLOW_BOLD_BRIGHT + border.repeat(84) + " Hiện có tổng: "
                + foundCategory.getProductList().size() + ", sản phẩm thuộc danh mục "
                + foundCategory.getName().toUpperCase() + " " + border.repeat(84)
                + ColorText.RESET);
        // HEAD
        System.out.println(DesignTable.getBorderProductTable());
        System.out.println(DesignTable.getProductTitle());
        System.out.println(DesignTable.getBorderProductTable());
        // BODY
        for (Product prod : foundCategory.getProductList()) {
            prod.displayData(categoryList);
        }
        System.out.println(DesignTable.getBorderProductTable());
        System.out.println();
        return foundCategory;
    }

    // 2 phương thức tuỳ chọn dùng thêm để tìm obj
//    private Category findCategoryById(List<Category> categoryList, int id) {
//        return categoryList.stream()
//                .filter(category -> category.getId() == id)
//                .findFirst()
//                .orElse(null);
//    }

//    private Category findCategoryByName(List<Category> categoryList, String name) {
//        return categoryList.stream()
//                .filter(category -> category.getName().equalsIgnoreCase(name))
//                .findFirst()
//                .orElse(null);
//    }

    /**
     * Phương thức tìm kiếm category bằng tên ( cũng có thể bằng id )
     *
     * @param scanner      : đối tượng scanner
     * @param categoryList : danh sách các danh mục được lấy từ Inventory
     */
    public void findCategoryByNameAndDisplay(Scanner scanner, List<Category> categoryList) {
        // check có danh sách có trống không
        if (categoryList.isEmpty()) {
            System.err.println("Chưa có danh mục nào để hiển thị !");
            return;
        }
        // hiển thị bảng cho dễ tìm
        displayCategory(categoryList);
        System.out.println("Nhập 'Tên' danh mục để tìm kiếm, hoặc nhập 'exit' để thoát tìm kiếm :");
        while (true) {
            String input = scanner.nextLine().toLowerCase().trim();
            try {
                // input
                if (input.equals("exit")) {
                    System.out.println(ColorText.YELLOW_BRIGHT + "Đã thoát lệnh tìm kiếm" + ColorText.RESET);
                    return;
                } else if (input.isEmpty()) {
                    System.err.println("Lỗi : 'Tên' không được để trống !");
                    continue;
                }
                // logic
                List<Category> filteredListCategory;
                filteredListCategory = categoryList.stream()
                        .filter(category -> category.getName().toLowerCase().contains((input))).toList();

                // Thông báo nếu không tìm thấy
                if (filteredListCategory.isEmpty())
                    throw new RuntimeException("Không tìm thấy danh mục : " + input);
                // kẻ bảng
                System.out.println(ColorText.WHITE_BRIGHT + "Đã tìm thấy "
                        + filteredListCategory.size()
                        + " danh mục trong kho" + ColorText.RESET);
                System.out.println(DesignTable.getBorderCategoryTable());
                System.out.println(DesignTable.getCategoryTitle());
                System.out.println(DesignTable.getBorderCategoryTable());
                filteredListCategory.forEach(Category::displayData);
                System.out.println(DesignTable.getBorderCategoryTable());
                System.out.println();
                System.out.println(ColorText.WHITE_BRIGHT
                        + "Bạn có thể nhập 'Tên' danh mục khác để tìm kiếm, hoặc nhập 'exit' để thoát :"
                        + ColorText.RESET);
                System.out.println();
                // error
            } catch (NumberFormatException e) {
                System.err.println("Lỗi input :" + e.getMessage() + " ?  xin hãy nhập lại");
            } catch (NullPointerException e) {
                System.err.println("Lỗi: " + e.getMessage() + " !");
            } catch (RuntimeException e) {
                System.err.println("Lỗi: " + e.getMessage() + " !");
            } catch (Exception e) {
                System.err.println("Lỗi: " + e.getMessage() + " !");
            }
        }
    }
}
