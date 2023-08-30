package controller;


import imp.Category;

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
            System.out.println(ColorText.YELLOW_BRIGHT +
                    "Không thể xoá danh mục hiện có sản phẩm !" + ColorText.RESET);
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
        System.out.println("-- Hiện có tổng: " + categoryList.size() + ", danh mục trong kho --");
        // HEAD
        System.out.println(DesignTable.getBorderCategoryTable());
        System.out.println(DesignTable.getCategoryTitle());
        System.out.println(DesignTable.getBorderCategoryTable());
        // BODY


        for (Category item : categoryList) {
            item.displayData();
        }
        System.out.println(DesignTable.getBorderCategoryTable());
        //
    }


    /**
     * Phương thức thêm category
     *
     * @param scanner      : Đối tượng scanner
     * @param categoryList : Danh sách các category của đối tượng Inventory ( Kho )
     */
    public void addCategory(Scanner scanner, List<Category> categoryList) {
        System.out.print("-- Nhập số lượng cần thêm, hoặc  gõ 'exit' để huỷ thêm:");
        String input = scanner.nextLine().toLowerCase();
        int number;
        while (true) {
            try {
                // input
                if (input.equals("exit")) {
                    System.out.println(ColorText.YELLOW_BRIGHT + "Đã thoát lệnh thêm" + ColorText.RESET);
                    return;
                } else {
                    number = Integer.parseInt(input);
                }
                break;
                //error
            } catch (NumberFormatException e) {
                System.err.println("Lỗi: input phải là số !");
            } catch (Exception e) {
                System.out.println("Error" + e.getMessage());
            }
        }
        if (number < 0) {
            System.err.println("Số nhập không được nhỏ hơn 1");
            return;
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
            System.out.println(ColorText.GREEN_BRIGHT + "Thêm thành công ^_^ " + ColorText.RESET);
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
        System.out.print("-- Nhập tên hoặc id của category để cập nhật, " +
                "hoặc nhập 'exit' để thoát lệnh: ");
        String input = scanner.nextLine().toLowerCase();
        if (input.equals("exit")) {
            System.out.println(ColorText.GREEN_BRIGHT + "Đã thoát lệnh cập nhật" + ColorText.RESET);
            return;
        }

        for (Category item : categoryList) {
            if (isInputMatching(item, input)) {
                System.out.println(ColorText.GREEN_BRIGHT + "Đã tìm thấy danh mục, " +
                        "tiến hành cập nhật" + ColorText.RESET);
                item.inputData(scanner, categoryList);
                return;
            }
        }

        System.err.println("Không có category Id hoặc tên: " + input + ", xin vui lòng nhập lại !");
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
        // 削除対象のカテゴリのIDまたは名前を入力 : Nhập tên hoặc id để tìm kiếm rồi xoá
        System.out.print("-- Nhập Id hoặc tên danh mục cần xoá, hoặc nhập 'exit' để thoát lệnh': ");
        String input;
        boolean productListOfCategoryIsEmpty = true; // không có sản phẩm
        boolean isFound = false;

        // 入力を受け取るループ : vòng lặp tìm kiếm
        while (!isFound) {
            input = scanner.nextLine().toLowerCase();
            try {
                // input
                if (input.equals("exit")) {
                    System.out.println(ColorText.YELLOW_BRIGHT + "Đã thoát lệnh xoá" + ColorText.RESET);
                    return false;
                }
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
                    System.err.println(" :( Không tìm thấy danh mục :" + input + ", xin hãy nhập lại !");
                // error
            } catch (Exception e) {
                System.out.println("Error" + e.getMessage());
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

        System.out.println(ColorText.GREEN_BRIGHT +
                "Đã tìm thấy danh mục tên: " + item.getName() + ColorText.RESET);
        System.out.print(ColorText.YELLOW_BRIGHT +
                "Bạn có chắc muốn xoá nhấn ( yes ) để xoá, hoặc ( no ) để thoát lệnh: " + ColorText.RESET);
        do {
            String command = scanner.nextLine().toLowerCase();

            if (command.equals("yes")) {
                categoryList.remove(item);
                System.out.println(ColorText.GREEN_BRIGHT +
                        " ^_^  Đã xoá danh mục tên: " + item.getName() + ColorText.RESET);
                break;
            } else if (command.equals("no")) {
                System.out.println(ColorText.GREEN_BRIGHT +
                        " *_*  Đã huỷ lệnh xoá " + ColorText.RESET + item.getName());
                break;
            } else {
                System.err.println(" *_* Nhập không đúng lệnh, hãy nhập lại lệnh ( yes or no ) ");
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

        System.out.println("-- Hiện tại có " + categoryList.size() + " danh mục trong shop --");
        for (Category item : categoryList) {
            System.out.println(ColorText.WHITE_BRIGHT + "Danh mục ( " + item.getName() +
                    " ) có id là ( " + item.getId() + " )" + ColorText.RESET);
        }
        boolean isFound = false;
        Category foundCategory = null;
        System.out.println("Nhập Id hoặc tên danh mục để vào, hoặc nhập 'exit' để thoát lệnh :");
        while (!isFound) {
            String input = scanner.nextLine().toLowerCase();
            try {
                // Khi nhập exit để thoát lệnh
                if (input.equals("exit")) {
                    System.out.println(ColorText.GREEN_BRIGHT + "Đã thoát lệnh tìm kiếm" + ColorText.RESET);
                    return null;
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
                System.out.println(ColorText.GREEN_BOLD_BRIGHT + "Danh mục hiện tại đang chọn là : "
                        + foundCategory.getName() + ColorText.RESET);
            }
        }
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
        if (categoryList.isEmpty()) {
            System.err.println("Chưa có danh mục nào để hiển thị !");
            return;
        }
        System.out.println("Nhập tên danh mục để tìm kiếm, hoặc nhập 'exit' để thoát tìm kiếm :");
        while (true) {
            String input = scanner.nextLine().toLowerCase();
            try {
                // input
                if (input.equals("exit")) {
                    System.out.println(ColorText.YELLOW_BRIGHT + "Đã thoát lệnh tìm kiếm" + ColorText.RESET);
                    return;
                }
                // logic

                List<Category> filteredListCategory = categoryList.stream()
                        .filter(category -> category.getName().toLowerCase().contains(input)).toList();
                // kẻ bảng
                System.out.println(DesignTable.getBorderCategoryTable());
                System.out.println(DesignTable.getCategoryTitle());
                System.out.println(DesignTable.getBorderCategoryTable());
                filteredListCategory.forEach(Category::displayData);
                System.out.println(DesignTable.getBorderCategoryTable());
                System.out.println("Bạn có thể nhập tên danh mục khác để tìm kiếm, hoặc nhập 'exit' để thoát :");
                // error
            } catch (NumberFormatException e) {
                System.err.println("Lỗi input :" + e.getMessage() + " ?  xin hãy nhập lại");
            } catch (NullPointerException e) {
                System.err.println("Lỗi: " + e.getMessage());
            }
        }
    }
}
