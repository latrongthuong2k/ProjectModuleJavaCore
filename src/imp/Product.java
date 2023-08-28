package imp;

import controller.ColorText;
import model.IProduct;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

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
        this.status = false;
    }

    public Product() {
        this.id = "";
        this.name = "";
        this.description = "";
        this.importPrice = 0;
        this.exportPrice = 0;
        this.profit = 0;
        this.status = false;
        this.categoryId = 0;
    }

    public Product(String id, String name, double importPrice, double exportPrice, double profit, String description, boolean status, int categoryId) {
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
     * @param scanner     : 入力スキャナー
     * @param productList : 入力商品リスト
     */
    @Override
    public void inputData(Scanner scanner, List<Product> productList) {
        if (!this.id.isEmpty()) {
            if (askForUpdateData(scanner, "Id")) {
                inputID(scanner, productList);
            }
        } else {
            inputID(scanner, productList);
        }
        //
        if (!this.name.isEmpty()) {
            if (askForUpdateData(scanner, "Name")) {
                inputName(scanner, productList);
            }
        } else {
            inputName(scanner, productList);
        }
        //
        if (!this.description.isEmpty()) {
            if (askForUpdateData(scanner, "description")) {
                inputDescription(scanner);
            }
        } else {
            inputDescription(scanner);
        }
        //
        if (this.importPrice != 0) {
            if (askForUpdateData(scanner, "import")) {
                inputImportPrice(scanner);
            }
        } else {
            inputImportPrice(scanner);
        }
        //
        if (this.exportPrice != 0) {
            if (askForUpdateData(scanner, "export")) {
                inputExportPrice(scanner);
            }
        } else {
            inputExportPrice(scanner);
        }
        //
        if (askForUpdateData(scanner, "status")) {
            inputStatus(scanner);
        }


        calProfit(); // gọi cuối sau khi nhập Import và Export
    }

    /**
     * Các phương thức dùng trong phương thức inputData
     */
    public void inputID(Scanner scanner, List<Product> productList) {
        // IDを入力
        boolean isTrue = true;
        while (isTrue) {
            System.out.print("-- Nhập Id sản phẩm, bắt đầu từ ký tự (P) và có tổng tối đa 4 ký tự: ");
            try {
                System.out.print("Nhập id: ");
                String id = scanner.nextLine();
                if (id.length() == 4 && id.charAt(0) == 'P') {
                    this.id = id;
                    isTrue = false;

                } else if (id.isEmpty()) {
                    throw new RuntimeException("Id không được để trống");
                } else
                    throw new RuntimeException("Nhập sai yêu cầu !");
                // check trùng
                for (Product item : productList) {
                    if (item.getName().equals(id)) {
                        throw new RuntimeException("Id sản phẩm đã tồn tại.");
                    }
                }
            } catch (RuntimeException e) {
                System.err.println("Lỗi: " + e.getMessage() + " , xin hãy nhập lại Id");
            } catch (Exception e) {
                System.out.println("Error" + e.getMessage());
            }
        }
    }

    public void inputName(Scanner scanner, List<Product> productList) {
        boolean isExit = false;
        while (!isExit) {
            System.out.print("-- Hãy nhập tên sản phẩm từ 6 - 30 ký tự: ");
            try {
                // input
                String productName = scanner.nextLine();
                // loop

                if (productName.isEmpty()) {
                    throw new RuntimeException("Tên sản phẩm không được để trống.");
                } else if (productName.length() < 6 || productName.length() > 30) {
                    throw new RuntimeException("Nhập sai yêu cầu! ");
                }
                // check trùng
                for (Product item : productList) {
                    if (item.getName().equals(productName)) {
                        throw new RuntimeException("Tên sản phẩm đã tồn tại.");
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
                System.out.println("Error" + e.getMessage());
            }
        }
    }

    public void inputImportPrice(Scanner scanner) {
        // 購入価格を入力
        boolean isExit = true;
        do {
            try {
                System.out.print("-- Nhập giá nhập: ");
                String input = scanner.nextLine();
                double importPrice = Double.parseDouble(input);
                if (importPrice <= 0) {
                    // thả ra lỗi và di tới vòng lặp mới
                    throw new RuntimeException("Giá nhập không được nhỏ hơn hoặc bằng 0 !");
                }
                // nếu không lỗi thì gán giá cho đối tượng và thoát vòng lặp
                this.importPrice = importPrice;
                isExit = false;
            } catch (InputMismatchException e) {
                System.err.println("Lỗi: Giá nhập phải là một số thực, hãy nhập lại !");
            } catch (RuntimeException e) {
                System.err.println("Lỗi: " + e.getMessage() + ", hãy nhập lại giá nhập !");
            } catch (Exception e) {
                System.out.println("Error" + e.getMessage());
            }
        } while (isExit);
    }

    public void inputExportPrice(Scanner scanner) {
        // 販売価格を入力 : Nhập giá xuất
        boolean isExit = true;
        do {
            System.out.print("-- Nhập giá xuất: ");
            try {
                String inputPrice = scanner.nextLine();
                double ExportPrice = Double.parseDouble(inputPrice);
                // check các điều kiện và thả ra lỗi để đi đến vòng lặp mới
                if (ExportPrice <= 0) {
                    throw new RuntimeException("Giá xuất không được nhỏ hơn" + MIN_INTEREST_RATE + "hoặc bằng 0 !");
                } else if (ExportPrice < MIN_INTEREST_RATE) {
                    throw new RuntimeException("Giá xuất cần tối thiểu: " + MIN_INTEREST_RATE + " $");
                }
                // nếu không lỗi thì gán giá export cho đối tượng và thoát vòng lặp
                this.exportPrice = ExportPrice;
                isExit = false;
            }
            // Nơi bắt các lỗi
            catch (InputMismatchException e) {
                System.err.println("Lỗi: Giá xuất phải là một số thực, hãy nhập lại !");
            } catch (RuntimeException e) {
                System.err.println("Lỗi : " + e.getMessage() + ", hãy nhập lại giá xuất !");
            }
        } while (isExit);

    }

    public void inputDescription(Scanner scanner) {
        // 商品の説明を入力 : Nhập mô tả
        do {
            try {
                System.out.print("-- Nhập mô tả cho sản phẩm: ");
                this.description = scanner.nextLine();
                if (description.isEmpty()) {
                    throw new RuntimeException("Mô tả không được để trống, xin hãy nhập lại !");
                }
                // nhập xong thì thoát khỏi vòng lặp
                break;
            } catch (RuntimeException e) {
                System.err.println("Lỗi : " + e.getMessage());
            }
        }
        while (true);
    }

    public void inputStatus(Scanner scanner) {
        // 状態を入力 : nhập trạng thái
        do {
            // Hỏi và đưa ra các gợi ý
            System.out.print("-- Nhập trạng thái sản phẩm 'true' : " +
                    ColorText.GREEN_BRIGHT + "Active" + ColorText.RESET +
                    " or 'false' : " + ColorText.YELLOW_BRIGHT + "InActive" + ColorText.RESET + ": ");
            try {
                String input = scanner.nextLine().toLowerCase();
                if (input.equals("true"))
                    this.status = true;
                else if (input.equals("false"))
                    this.status = false;
                else {
                    throw new Exception(" Nhập không đúng yêu cầu. Xin hãy nhập lại !");
                }
                break;
            } catch (Exception e) {
                System.err.println("Lỗi: " + e.getMessage());
            }
        }
        while (true);
    }

    private boolean askForUpdateData(Scanner scanner, String nameField) {
        if (nameField.equals("status")) {
            System.out.println("Hiện tại mặt định là ngừng kinh doanh, bạn có muốn chọn lại không");
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
        /**
         * Bản hiển thị sản phẩm
         */
        System.out.printf("| %-10s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s |\n",
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
