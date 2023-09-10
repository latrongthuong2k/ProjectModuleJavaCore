package imp;

import controller.ColorText;
import model.IProduct;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Product implements IProduct {

    /**
     * Các variable của class
     */
    String id;
    String name;
    double importPrice;
    double exportPrice;
    double profit;
    String description;
    boolean status;
    int categoryId;

    /*
     * Setter && Getter method
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(double importPrice) {
        this.importPrice = importPrice;
    }

    public double getExportPrice() {
        return exportPrice;
    }

    public void setExportPrice(double exportPrice) {
        this.exportPrice = exportPrice;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }


    /**
     * コンストラクターメソッド : constructor khởi tạo object với một số thuộc tính mặt đinh
     */
    public Product(int categoryId) {
        this.categoryId = categoryId;
        this.id = "";
        this.name = "";
        this.description = "";
        this.importPrice = 0;
        this.exportPrice = 0;
        this.profit = 0;
        this.status = true;
    }

    public Product() {
        this.id = "";
        this.name = "";
        this.description = "";
        this.importPrice = 0;
        this.exportPrice = 0;
        this.profit = 0;
        this.status = true;
        this.categoryId = 0;
    }

    public Product(String id, String name,
                   double importPrice, double exportPrice,
                   double profit, String description,
                   boolean status, int categoryId) {
        this.id = id;
        this.name = name;
        this.importPrice = importPrice;
        this.exportPrice = exportPrice;
        this.profit = profit;
        this.description = description;
        this.status = status;
        this.categoryId = categoryId;
    }

    /**
     * 入力データを処理するメソッド
     *
     * @param scanner    : 入力スキャナー
     * @param allProduct : 入力商品リスト
     */
    @Override
    public void inputData(Scanner scanner, List<Product> allProduct, List<Category> categoryList) {
        // input ID
        if (!this.id.isEmpty()) {
            if (askForUpdateData(scanner, "'mã sản phẩm'", this.id)) {
                inputID(scanner, allProduct, categoryList); // id
                System.out.println(ColorText.GREEN_BRIGHT + "Cập nhật thành công" + ColorText.RESET);
                System.out.println();
            }
        } else {
            inputID(scanner, allProduct, categoryList);
        }
        // input Name
        if (!this.name.isEmpty()) {
            if (askForUpdateData(scanner, "'tên sản phẩm'", this.name)) {
                inputName(scanner, allProduct, categoryList);
                System.out.println(ColorText.GREEN_BRIGHT + "Cập nhật thành công" + ColorText.RESET);
                System.out.println();
            }
        } else {
            inputName(scanner, allProduct, categoryList);
        }
        // input Desc
        if (!this.description.isEmpty()) {
            if (askForUpdateData(scanner, "'mô tả'", this.description)) {
                inputDescription(scanner);
                System.out.println(ColorText.GREEN_BRIGHT + "Cập nhật thành công" + ColorText.RESET);
                System.out.println();
            }
        } else {
            inputDescription(scanner);
        }
        // input Import
        if (this.importPrice != 0) {
            if (askForUpdateData(scanner, "'giá nhập'", String.valueOf(importPrice))) {
                inputImportPrice(scanner);
                System.out.println(ColorText.GREEN_BRIGHT + "Cập nhật thành công" + ColorText.RESET);
                System.out.println();
            }
        } else {
            inputImportPrice(scanner);
        }
        // input Export
        if (this.exportPrice != 0) {
            if (askForUpdateData(scanner, "'giá xuất'", String.valueOf(exportPrice))) {
                inputExportPrice(scanner);
                System.out.println(ColorText.GREEN_BRIGHT + "Cập nhật thành công" + ColorText.RESET);
                System.out.println();
            }
        } else {
            inputExportPrice(scanner);
        }
        // change status
        if (ashForUpdateStatus(scanner, this.status)) {
            inputStatus(scanner);
        }

        calProfit(); // gọi cuối sau khi nhập Import và Export
    }

    /**
     * Các phương thức dùng trong phương thức inputData
     */
    public void inputID(Scanner scanner, List<Product> allProduct, List<Category> categoryList) {
        // IDを入力
        boolean isTrue = true;
        System.out.print("-- Nhập Id sản phẩm, bắt đầu từ ký tự (P) và có tổng tối đa 4 ký tự: \n");
        while (isTrue) {
            try {
                String id = scanner.nextLine().trim();
                if (!regexProductId(id)) {
                    throw new RuntimeException("Nhập sai yêu cầu !");
                } else if (id.isEmpty()) {
                    throw new RuntimeException("Id không được để trống");
                }
                // check trùng
                for (Product item : allProduct) {
                    if (item.getId().equalsIgnoreCase(id) && !item.getId().equals(this.id)) {
                        String catName = categoryList.stream()
                                .filter(category -> category.getId() == item.getCategoryId())
                                .findFirst().get()
                                .getName();
                        throw new RuntimeException("Id sản phẩm đã tồn tại ở danh mục : "
                                + ColorText.GREEN_BRIGHT + catName + ColorText.RESET);
                    }
                }
                this.id = id;
                isTrue = false;
            } catch (RuntimeException e) {
                System.err.println("Lỗi: " + e.getMessage() + " , xin hãy nhập lại Id");
            } catch (Exception e) {
                System.err.println("Lỗi: " + e.getMessage());
            }
        }
    }

    // kiểm tra cho input ID
    public boolean regexProductId(String productId) {
        String productIdRegex = "^P[\\w\\d]{3}$"; //^P[0-9]{3}$
        return Pattern.matches(productIdRegex, productId);
    }

    // input name
    public void inputName(Scanner scanner, List<Product> allProduct, List<Category> categoryList) {
        boolean isExit = false;
        System.out.print("-- Hãy nhập tên sản phẩm từ 6 - 30 ký tự: ");
        while (!isExit) {
            try {
                // input
                String productName = scanner.nextLine().trim();
                // loop

                if (productName.isEmpty()) {
                    throw new RuntimeException("Tên sản phẩm không được để trống");
                } else if (productName.length() < 6 || productName.length() > 30) {
                    throw new RuntimeException("Tên nhập không đạt yêu cầu!");
                }
                // check trùng
                for (Product item : allProduct) {
                    if (item.getName().equalsIgnoreCase(productName) && !item.getName().equals(this.name)) {
                        String catName = categoryList.stream()
                                .filter(category -> category.getId() == item.getCategoryId())
                                .findFirst().get()
                                .getName();
                        throw new RuntimeException("Tên sản phẩm đã tồn tại ở danh mục "
                                + ColorText.GREEN_BRIGHT + catName + ColorText.RESET);
                    }
                }
                /**
                 * Nếu mọi thứ ok thì gán tên đã kiểm tra cho tên của đối tượng
                 * và thoát khỏi vòng lặp
                 */
                this.name = productName;
                isExit = true;
            } catch (RuntimeException e) {
                System.err.println("Lỗi: " + e.getMessage() + " , xin hãy nhập lại tên ");
            } catch (Exception e) {
                System.out.println("Lỗi" + e.getMessage());
            }
        }
    }

    /**
     * Hàm nhập giá trị giá cho đối tượng
     *
     * @param scanner đối tượng scanner để thực hiện cho việc nhập
     */
    public void inputImportPrice(Scanner scanner) {
        // 購入価格を入力
        boolean isExit = true;
        System.out.print("-- Nhập giá nhập: ");
        do {
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    throw new RuntimeException("Giá nhập không được để trống !");
                }
                double importPrice = Double.parseDouble(input);
                if (importPrice <= 0) {
                    // thả ra lỗi và di tới vòng lặp mới
                    throw new RuntimeException("Giá nhập không được nhỏ hơn hoặc bằng 0");
                }
                // nếu không lỗi thì gán giá cho đối tượng và thoát vòng lặp
                this.importPrice = importPrice;
                isExit = false;
            } catch (InputMismatchException e) {
                System.err.println("Lỗi: Giá nhập phải là một số thực, hãy nhập lại !");
            } catch (RuntimeException e) {
                System.err.println("Lỗi: " + e.getMessage() +
                        ", hãy nhập lại giá nhập !");
            } catch (Exception e) {
                System.err.println("Lỗi" + e.getMessage());
            }
        } while (isExit);
    }

    public void inputExportPrice(Scanner scanner) {
        // 販売価格を入力 : Nhập giá xuất
        boolean isExit = true;
        System.out.print("-- Nhập giá xuất: ");
        do {
            try {
                String inputPrice = scanner.nextLine().trim();
                if (inputPrice.isEmpty()) {
                    throw new RuntimeException("Giá xuất không được để trống !");
                }
                //
                double ExportPrice = Double.parseDouble(inputPrice);
                // check các điều kiện và thả ra lỗi để đi đến vòng lặp mới
                if (ExportPrice <= 0) {
                    throw new RuntimeException("Giá xuất không được nhỏ hơn " +
                            String.format("%.2f", this.importPrice * (MIN_INTEREST_RATE + 1))
                            + " $");
                } else if (ExportPrice < this.importPrice * (MIN_INTEREST_RATE + 1)) {
                    throw new RuntimeException("Giá xuất cần ít nhất trên: " +
                            String.format("%.2f", this.importPrice * (MIN_INTEREST_RATE + 1))
                            + " $");
                }
                // nếu không lỗi thì gán giá export cho đối tượng và thoát vòng lặp
                this.exportPrice = ExportPrice;
                isExit = false;
            }
            // Nơi bắt các lỗi
            catch (InputMismatchException e) {
                System.err.println("Lỗi: Giá xuất phải là một số thực, hãy nhập lại !");
            } catch (RuntimeException e) {
                System.err.println("Lỗi : " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Lỗi : " + e.getMessage());
            }
        } while (isExit);

    }

    // input desc
    public void inputDescription(Scanner scanner) {
        // 商品の説明を入力 : Nhập mô tả
        System.out.print("-- Nhập mô tả cho sản phẩm: ");
        do {
            try {
                this.description = scanner.nextLine().trim();
                if (description.isEmpty()) {
                    throw new RuntimeException("Mô tả không được để trống, xin hãy nhập lại !");
                } else if (description.length() > 30) {
                    throw new RuntimeException("Mô tả chỉ được tối đa 30 ký tự !");
                }
                // nhập xong thì thoát khỏi vòng lặp
                break;
            } catch (RuntimeException e) {
                System.err.println("Lỗi : " + e.getMessage());
            }
        }
        while (true);
    }

    // input status
    public void inputStatus(Scanner scanner) {
        // Hỏi và đưa ra các gợi ý
        System.out.print("-- Nhập trạng thái sản phẩm 'true' : " +
                ColorText.GREEN_BRIGHT + "Còn hàng" + ColorText.RESET +
                " or 'false' : " + ColorText.YELLOW_BRIGHT + "Ngừng kinh doanh" + ColorText.RESET + ": ");
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
                    throw new Exception(" Nhập không đúng yêu cầu. Xin hãy nhập lại !");
                }
                break;
            } catch (Exception e) {
                System.err.println("Lỗi: " + e.getMessage());
            }
        }
        while (true);
    }

    /**
     * Hàm hỏi để cập nhật status true thì sẽ cho cập nhật false thì sẽ huỷ
     *
     * @param scanner nhận vào đối tượng scanner
     * @param status  nhận vào giá trị status của đối tượng product đang thao tác
     * @return trả về kết quả true or false cho cho việc hỏi
     */
    private boolean ashForUpdateStatus(Scanner scanner, boolean status) {
        String textStatus = status ? ColorText.GREEN_BRIGHT + "Còn hàng" + ColorText.RESET :
                ColorText.YELLOW_BRIGHT + "Ngừng kinh doanh" + ColorText.RESET;
        System.out.println("( Hiện tại trạng thái là : '" + textStatus +
                "' , bạn có muốn chọn lại không ? )");
        System.out.print(ColorText.WHITE_BRIGHT + "-- Để cập nhật Status , nhập 'y' hoặc 'yes' để cập nhật," +
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
     * Hỏi để cập nhật các trường input cho đối tượng
     *
     * @param scanner   nhận vào đối tượng scanner
     * @param nameField nhận vào tên trường cụ thể cho mỗi lần hỏi
     * @param oldData   giá trị cũ của trường
     * @return trả về kết quả sau khi hỏi, true cho việc cho phép sửa và false cho việc huỷ sửa
     */
    private boolean askForUpdateData(Scanner scanner, String nameField, String oldData) {
        System.out.println("Giá trị trước đây của "
                + ColorText.GREEN_BRIGHT + nameField + ColorText.RESET
                + " là : " + ColorText.YELLOW_BRIGHT + oldData + ColorText.RESET);
        System.out.print(ColorText.WHITE_BRIGHT + "Để cập nhật " + nameField + ", nhập 'y' hoặc 'yes' để cập nhật," +
                " hoặc nhập bất kỳ để huỷ : " + ColorText.RESET);
        String input = scanner.nextLine().toLowerCase().trim();
        if (input.equals("y") || input.equals("yes")) {
            return true;
        } else {
            System.out.println(ColorText.YELLOW_BRIGHT + "Đã huỷ cập nhật " + nameField + ColorText.RESET);
            System.out.println();
            return false;
        }
    }

    /*
     *--------------------------------------------------------------------------
     */

    /**
     * データを表示するメソッド : phương thức hiển thị thông tin sản phẩm
     */
    @Override
    public void displayData(List<Category> categoryList) {
        // tìm ra category theo idCategory của sản phẩm
        String nameCategory = "";
        for (Category ct : categoryList) {
            if (categoryId == ct.getId())
                nameCategory = ct.getName();
        }

        // Bản hiển thị sản phẩm
        String yellowBright = ColorText.YELLOW_BRIGHT;
        String greenBright = ColorText.GREEN_BRIGHT;
        String cyanBright = ColorText.CYAN_BRIGHT;
        String colorReset = ColorText.RESET;
        String colorDepend;
        if (this.status)
            colorDepend = greenBright;
        else
            colorDepend = yellowBright;
        System.out.printf("|" + cyanBright + " %-10s " + colorReset +
                        "| %-30s " +
                        "| %-20s " +
                        "| %-20s " +
                        "| %-20s " +
                        "| %-30s " +
                        "|" + colorDepend + " %-20s " + colorReset +
                        "| %-30s |\n",
                id,
                name,
                String.format("%.2f", importPrice) + " $",
                String.format("%.2f", exportPrice) + " $",
                String.format("%.2f", profit) + " $",
                description,
                String.format(status ? "Còn hàng" : "Ngừng kinh doanh"),
                nameCategory);
    }

    /**
     * 利益を計算するメソッド : phương thức tính toán lợi nhuận
     */
    @Override
    public void calProfit() {
        this.profit = exportPrice - importPrice;
    }
}
