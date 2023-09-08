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
        this.status = true;
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
        // id generate
        autoGenerateId(categoryList);
        // input name
        if (!this.name.isEmpty()) {
            if (askForUpdateData(scanner, "'tên danh mục'", this.name)) {
                inputName(scanner, categoryList);
                System.out.println(ColorText.GREEN_BRIGHT + "Cập nhật thành công" + ColorText.RESET);
                System.out.println();
            }
        } else {
            inputName(scanner, categoryList);
        }
        // input desc
        if (!this.description.isEmpty()) {
            if (askForUpdateData(scanner, "'mô tả'", this.description)) {
                inputDescription(scanner);
                System.out.println(ColorText.GREEN_BRIGHT + "Cập nhật thành công" + ColorText.RESET);
                System.out.println();
            }
        } else {
            inputDescription(scanner);
        }
        // input Status
        if (askForUpdateStatus(scanner, this.status))
            inputStatus(scanner);
    }

    /**
     * ID generator
     *
     * @param categoryList : danh sách các danh mục
     */
    public void autoGenerateId(List<Category> categoryList) {
        // Auto check và thêm id
        // nếu id = 0 là mặc định tạo mới, thì sẽ check tồn tại và cộng lên sao cho khác biệt
        if (this.id == 0) {
            int id = 1;
            boolean idExists = true;
            if (!categoryList.isEmpty()) {
                // check tồn tại
                while (idExists) {
                    idExists = false;
                    for (Category item : categoryList) {
                        if (item.getId() == id) {
                            idExists = true;
                            break;
                        }
                    }
                    if (idExists) {
                        id++;
                    }
                }
                // gán id input cho id của đối tượng và thoát vòng lặp nếu các diều kiện được thoả mãn
            }
            this.id = id;
        }
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
            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.err.println("Lỗi : Tên danh mục không được trống. Xin hãy nhập lại ! ");
                continue;
            } else if (name.length() < 6 || name.length() > 30) {
                System.err.println("Lỗi : Tên danh mục không đạt yêu cầu, xin hãy nhập lại !");
                continue;
            }
            // Nếu danh mục đã có những sản phẩm khác thì tiến hành so sánh trùng lặp không case
            boolean nameExists = false;
            if (!categoryList.isEmpty()) {
                for (Category item : categoryList) {
                    if (item.getName().equalsIgnoreCase(name) && !item.getName().equals(this.name)) {
                        System.err.println("Lỗi : Tên danh mục đã tồn tại. Xin hãy nhập lại !");
                        nameExists = true;
                        break;
                    }
                }
            }
            if (!nameExists) {
                this.name = name;
                return; // thoát
            }
        } while (true);
    }

    public void inputDescription(Scanner scanner) {
        // カテゴリーの説明を入力します : Nhập mô tả cho danh mục
        System.out.print("-- Nhập mô tả cho danh mục: ");
        do {
            try {
                this.description = scanner.nextLine().trim(); // input xoá trống
                if (description.isEmpty()) {
                    // Ném ra lỗi và chạy đến lại vòng lặp hỏi mới
                    throw new RuntimeException(" Mô tả không được để trống. Xin hãy nhập lại ! ");
                } else if (description.length() > 30) {
                    throw new RuntimeException("Mô tả chỉ được tối đa 30 ký tự !");
                }
                // Nếu không có lỗi gì thì thoát vòng lặp
                break;
            } catch (RuntimeException e) {
                System.err.println("Lỗi: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Lỗi" + e.getMessage());
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
                String input = scanner.nextLine().toLowerCase().trim();
                if (input.equals("true")) {
                    this.status = true;
                    System.out.println(ColorText.GREEN_BRIGHT + "Cập nhật thành công" + ColorText.RESET);
                    System.out.println();
                } else if (input.equals("false")) {
                    this.status = false;
                    System.out.println(ColorText.GREEN_BRIGHT + "Cập nhật thành công" + ColorText.RESET);
                    System.out.println();
                } else {
                    // Ném ra lỗi và chạy lại đến vòng lặp hỏi mới
                    throw new Exception(" *_* Input không đúng yêu cầu. Xin hãy nhập lại !");
                }

                // Nếu không có lỗi thì thoát vòng lặp
                break;
            } catch (RuntimeException e) {
                System.err.println("Lỗi: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Lỗi: " + e.getMessage());
            }
        }
        while (true);
    }

    private boolean askForUpdateStatus(Scanner scanner, boolean status) {
        String textStatus = status ? ColorText.GREEN_BRIGHT + "Active" + ColorText.RESET :
                ColorText.YELLOW_BRIGHT + "InActive" + ColorText.RESET;
        System.out.println("( Hiện tại trạng thái là : '" + textStatus +
                "' , bạn có muốn chọn lại không ? )");
        System.out.print(ColorText.WHITE_BRIGHT + "Để cập nhật Status , nhập 'y' hoặc 'yes' để cập nhật," +
                " hoặc nhập bất kỳ để huỷ : " + ColorText.RESET);
        String input = scanner.nextLine().toLowerCase().trim();
        if (input.equals("y") || input.equals("yes")) {
            return true;
        } else {
            System.out.println(ColorText.YELLOW_BRIGHT + "Đã huỷ cập nhật " + "Status" + ColorText.RESET);
            System.out.println();
            return false;
        }
    }

    /**
     * Phương thức hỏi trước khi update data, việc phải gọi phương thức này cho phép thực
     * hiện pass input của từng tường dữ liệu bắt buột
     *
     * @param scanner   : đối tượng scanner
     * @param nameField : tên trường truyền vào để hiển thị
     * @return : trả về boolean cho việc đồng ý hay không
     */
    private boolean askForUpdateData(Scanner scanner, String nameField, String oldData) {
        System.out.println("Giá trị trước đây của "
                + ColorText.GREEN_BRIGHT + nameField + ColorText.RESET
                + " là : " + ColorText.YELLOW_BRIGHT + oldData + ColorText.RESET);
        System.out.print(ColorText.WHITE_BRIGHT + "Để Thực hiện update "
                + nameField + ", nhập 'y' hoặc 'yes' để cập nhật,"
                + " hoặc nhập bất kỳ để huỷ : " + ColorText.RESET);
        String input = scanner.nextLine().toLowerCase().trim();
        if (input.equals("y") || input.equals("yes")) {
            return true;
        } else {
            System.out.println(ColorText.YELLOW_BRIGHT + "Đã huỷ cập nhật " + nameField + ColorText.RESET);
            System.out.println();
            return false;
        }
    }

    /**
     * Phương thức hiển thị danh mục
     */
    @Override
    public void displayData() {
        // ID、名前、説明、ステータスの情報を表示します
        String colorFalse = ColorText.YELLOW_BRIGHT;
        String colorTrue = ColorText.GREEN_BRIGHT;
        String selectColor;
        String colorR = ColorText.RESET;
        if (status) {
            selectColor = colorTrue;
        } else {
            selectColor = colorFalse;
        }
        System.out.printf("| %-10d | %-30s | %-30s | " + selectColor + "%-20s" + colorR + " |\n",
                id, name, description, status ? "Active" : "InActive");
    }


}
