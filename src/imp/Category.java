package imp;

import controller.ColorText;
import model.ICategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Category implements ICategory {

    /**
     * Các variable của class
     */
    private int id;
    private String name;
    private String description;
    private boolean status;

    private final List<Product> productList = new ArrayList<>();

    public List<Product> getProductList() {
        return productList;
    }

    // 取得と設定メソッド : setter và getter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * コンストラクターメソッド : Constructor of category
     */
    public Category() {
        this.id = 0;
        this.name = "";
        this.description = "";
        this.status = false;
    }

    public Category(int id, String name, String description, boolean status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    /**
     * 入力データを処理するメソッド : Phương thức nhập thông tin cho danh mục
     *
     * @param scanner      : 入力スキャナー
     * @param categoryList : カテゴリーリスト
     */
    @Override
    public void inputData(Scanner scanner, List<Category> categoryList) {
        // カテゴリー情報の入力を開始します : Nhập thông tin danh mục
        System.out.println(" <-----------Nhập thông tin danh mục----------->");
        if (!this.name.isEmpty()) {
            if (askForUpdateData(scanner, "id")) {
                inputName(scanner, categoryList);
            }
        } else {
            inputName(scanner, categoryList);
        }
        if (!this.description.isEmpty()) {
            if (askForUpdateData(scanner, "Description")) {
                inputDescription(scanner);
            }
        } else {
            inputDescription(scanner);
        }
        if (askForUpdateData(scanner, "status"))
            inputStatus(scanner);
    }

    /**
     * Các phương thức dùng cho phương thức inputData
     *
     * @param scanner      : đối tượng scanner
     * @param categoryList : Danh sách các danh mục của kho
     */
    public void inputName(Scanner scanner, List<Category> categoryList) {
        // Nhập tên danh mục
        System.out.print("-- Nhập tên danh mục 6 - 30 ký tự: ");
        do {
            String name = scanner.nextLine();
            /**
             * Check kiểm tra tên danh mục nếu có lỗi thì chạy vòng lặp khác và
             * yêu cầu nhập lại
             */
            if (name.isEmpty()) {
                System.err.println("Lỗi : *_* Tên danh mục không được trống. Xin hãy nhập lại ! ");
                continue;
            } else if (name.length() < 6 || name.length() > 30) {
                System.err.println("Lỗi : *_* Tên danh mục không đạt yêu cầu, xin hãy nhập lại !");
                continue;
            }
            boolean nameExists = false;

            /**
             * Nếu danh mục đã có những sản phẩm khác thì tiến hành so sánh trùng lặp
             */
            if (!categoryList.isEmpty()) {
                for (Category item : categoryList) {
                    if (item.getName().equals(name)) {
                        System.err.println("Lỗi : *_* Tên danh mục đã tồn tại. Xin hãy nhập lại !");
                        nameExists = true;
                        break;
                    }
                }
            }
            if (!nameExists) {
                // カテゴリ名が重複しない場合、名前とIDを設定
                this.name = name;
                int id = 0;
                if (!categoryList.isEmpty()) {
                    for (Category item : categoryList) {
                        if (item.getId() >= id) {
                            id = item.getId() + 1;
                        }
                    }
                }

                // gán id input cho id của đối tượng và thoát vòng lặp nếu các diều kiện được thoả mãn
                this.id = id;
                break; // thoát
            }
        } while (true);
    }

    public void inputDescription(Scanner scanner) {
        // カテゴリーの説明を入力します : Nhập mô tả cho danh mục
        System.out.print("-- Nhập mô tả cho danh mục: ");
        do {
            try {
                this.description = scanner.nextLine();
                if (description.isEmpty()) {
                    /**
                     * Ném ra lỗi và chạy đến lại vòng lặp hỏi mới
                     */
                    throw new RuntimeException(" *_* Mô tả không được để trống. Xin hãy nhập lại ! ");
                }
                /**
                 * Nếu không có lỗi gì thì thoát vòng lặp
                 */
                break;
            } catch (RuntimeException e) {
                System.err.println("Lỗi: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error" + e.getMessage());
            }
        }
        while (true);
    }

    public void inputStatus(Scanner scanner) {
        // カテゴリーの状態を入力します : Nhập trạng thái danh mục
        System.out.print("-- Nhập trạng thái 'true' : " + ColorText.GREEN_BRIGHT + "Active" + ColorText.RESET +
                " or 'false' : " + ColorText.YELLOW_BRIGHT + "InActive" + ColorText.RESET + ": ");
        do {
            try {
                String input = scanner.nextLine().toLowerCase();
                if (input.equals("true"))
                    this.status = true;
                else if (input.equals("false"))
                    this.status = false;
                else {
                    /**
                     * Ném ra lỗi và chạy lại đến vòng lặp hỏi mới
                     */
                    throw new Exception(" *_* Input không đúng yêu cầu. Xin hãy nhập lại !");
                }
                /**
                 * Nếu không có lỗi thì thoát vòng lặp
                 */
                break;
            } catch (RuntimeException e) {
                System.err.println("Lỗi: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error" + e.getMessage());
            }
        }
        while (true);
    }

    /**
     * Phương thức hỏi trước khi update data, việc phải gọi phương thức này cho phép thực
     * hiện pass input của từng tường dữ liệu bắt buột
     *
     * @param scanner
     * @param nameField
     * @return
     */
    public boolean askForUpdateData(Scanner scanner, String nameField) {
        if (nameField.equals("status")) {
            System.out.println("Hiện tại mặt định là InActive, bạn có muốn chọn lại không");
            System.out.print("-- Để Thực hiện update status, nhập 'y' hoặc 'yes' để sửa đổi," +
                    " hoặc nhập bất kỳ để huỷ : ");
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else {
                System.out.println(
                        ColorText.YELLOW_BRIGHT + "Đã huỷ nhập " + nameField + ColorText.RESET);
                return false;
            }
        } else {
            System.out.print("-- Để Thực hiện update" + nameField + ", nhập 'y' hoặc 'yes' để cập nhật," +
                    " hoặc nhập bất kỳ để huỷ : ");
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else {
                System.out.println(
                        ColorText.YELLOW_BRIGHT + "Đã huỷ cập nhật " + nameField + ColorText.RESET);
                return false;
            }
        }
    }

    /**
     * Phương thức hiển thị danh mục
     */
    @Override
    public void displayData() {
        // ID、名前、説明、ステータスの情報を表示します
        String colorTrue = ColorText.YELLOW_BRIGHT;
        String colorFalse = ColorText.GREEN_BRIGHT;
        String selectColor = null;
        String colorR = ColorText.RESET;
        if (status)
            selectColor = colorTrue;
        else
            selectColor = colorFalse;
        System.out.printf("| %-10d | %-20s | %-20s | " + selectColor + "%-20s" + colorR + " |\n",
                id, name, description, status ? "Active" : "InActive");
    }
}
