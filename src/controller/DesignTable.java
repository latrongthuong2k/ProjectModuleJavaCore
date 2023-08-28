package controller;

public class DesignTable {

    // khung của bảng category
    public static String getBorderCategoryTable() {
        String border = "-";
        return "+" + border.repeat(12) +
                "+" + border.repeat(22) +
                "+" + border.repeat(22) +
                "+" + border.repeat(22) + "+";
    }

    // kung title của bảng categories
    public static String getCategoryTitle() {
        String colorSet = ColorText.WHITE_BOLD_BRIGHT;
        String colorReset = ColorText.RESET;
        return String.format("| " + colorSet + "%-10s" + colorReset +
                        " | " + colorSet + "%-20s" + colorReset +
                        " | " + colorSet + "%-20s" + colorReset +
                        " | " + colorSet + "%-20s" + colorReset + " |",
                "ID", "Tên danh mục", "Mô tả", "Trạng thái");
    }

    // Khung viền bảng của category
    public static String getBorderProductTable() {
        String border = "-";
        return "+" + border.repeat(12) +
                "+" + border.repeat(22) +
                "+" + border.repeat(22) +
                "+" + border.repeat(22) +
                "+" + border.repeat(22) +
                "+" + border.repeat(22) +
                "+" + border.repeat(22) +
                "+" + border.repeat(22) +
                "+";
    }

    // kung title của bảng products
    public static String getProductTitle() {
        String colorSet = ColorText.WHITE_BOLD_BRIGHT;
        String colorReset = ColorText.RESET;
        return String.format("| " + colorSet + "%-10s" + colorReset +
                        " | " + colorSet + "%-20s" + colorReset +
                        " | " + colorSet + "%-20s" + colorReset +
                        " | " + colorSet + "%-20s" + colorReset +
                        " | " + colorSet + "%-20s" + colorReset +
                        " | " + colorSet + "%-20s" + colorReset +
                        " | " + colorSet + "%-20s" + colorReset +
                        " | " + colorSet + "%-20s" + colorReset + " |",
                "ID", "Tên", "Giá nhập", "Giá xuất", "Lợi nhuận", "Mô tả", "Trạng thái", "Tên danh mục");
    }
}
