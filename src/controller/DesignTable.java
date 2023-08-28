package controller;

public class DesignTable {
    public static String getBorderCategoryTable() {
        String border = "-";
        return "+" + border.repeat(12) +
                "+" + border.repeat(22) +
                "+" + border.repeat(22) +
                "+" + border.repeat(22) + "+";
    }

    public static String getCategoryTable() {
        String colorSet = ColorText.WHITE_BOLD_BRIGHT;
        String colorReset = ColorText.RESET;
        return String.format("| " + colorSet + "%-10s" + colorReset +
                        " | " + colorSet + "%-20s" + colorReset +
                        " | " + colorSet + "%-20s" + colorReset +
                        " | " + colorSet + "%-20s" + colorReset + " |",
                "ID", "Tên danh mục", "Mô tả", "Trạng thái");
    }

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

    public static String getProductTable() {
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
