package controller;

public class DesignTable {

    // khung của bảng category
    public static String getBorderCategoryTable() {
        String border = "-";
        return "+" + border.repeat(12) +
                "+" + border.repeat(32) +
                "+" + border.repeat(32) +
                "+" + border.repeat(22) + "+";
    }

    public static String getBorderCategoryTable2() {
        String border = "=";
        return "+" + border.repeat(12) +
                "+" + border.repeat(32) +
                "+" + border.repeat(32) +
                "+" + border.repeat(22) + "+";
    }

    // kung title của bảng categories
    public static String getCategoryTitle() {
        String colorSet = ColorText.WHITE_BOLD_BRIGHT;
        String colorReset = ColorText.RESET;
        return String.format("| " + colorSet + "%-10s" + colorReset +
                        " | " + colorSet + "%-30s" + colorReset +
                        " | " + colorSet + "%-30s" + colorReset +
                        " | " + colorSet + "%-20s" + colorReset + " |",
                "ID", "Tên danh mục", "Mô tả", "Trạng thái");
    }

    // Khung viền bảng của category
    public static String getBorderProductTable() {
        String border = "-";
        return "+" + border.repeat(12) +
                "+" + border.repeat(32) +
                "+" + border.repeat(22) +
                "+" + border.repeat(22) +
                "+" + border.repeat(22) +
                "+" + border.repeat(32) +
                "+" + border.repeat(22) +
                "+" + border.repeat(32) +
                "+";
    }

    public static String getBorderProductTable2() {
        String border = "=";
        return "+" + border.repeat(12) +
                "+" + border.repeat(32) +
                "+" + border.repeat(22) +
                "+" + border.repeat(22) +
                "+" + border.repeat(22) +
                "+" + border.repeat(32) +
                "+" + border.repeat(22) +
                "+" + border.repeat(32) +
                "+";
    }

    // kung title của bảng products
    public static String getProductTitle() {
        String colorSet = ColorText.WHITE_BOLD_BRIGHT;
        String colorReset = ColorText.RESET;
        return String.format("| " + colorSet + "%-10s" + colorReset +
                        " | " + colorSet + "%-30s" + colorReset +
                        " | " + colorSet + "%-20s" + colorReset +
                        " | " + colorSet + "%-20s" + colorReset +
                        " | " + colorSet + "%-20s" + colorReset +
                        " | " + colorSet + "%-30s" + colorReset +
                        " | " + colorSet + "%-20s" + colorReset +
                        " | " + colorSet + "%-30s" + colorReset + " |",
                "ID", "Tên", "Giá nhập", "Giá xuất", "Lợi nhuận", "Mô tả", "Trạng thái", "Tên danh mục");
    }


    //
    static public String splitText(String field) {
        int maxLength = 21;

        if (field.length() > maxLength) {
            int lastSpaceIndex = field.lastIndexOf(" ", maxLength);

            if (lastSpaceIndex != -1) {
                field = field.substring(0, lastSpaceIndex) + "\n" + field.substring(lastSpaceIndex + 1);
            } else {
                // Nếu không tìm thấy khoảng trắng, thì cắt ở độ dài tối đa
                field = field.substring(0, maxLength) + "\n" + field.substring(maxLength);
            }
        }

        return field;
    }

    static public String statisticForTableCategory(int size) {
        String border = "-";
        String statistic = (ColorText.YELLOW_BOLD_BRIGHT + border.repeat(33) + " Hiện có tổng: "
                + size + ", danh mục trong kho " + border.repeat(33) + ColorText.RESET);
        return statistic;
    }

    static public String statisticForTableProduct(int size) {
        String border = "-";
        String statistic = (ColorText.YELLOW_BOLD_BRIGHT + border.repeat(84) + " Hiện có tổng: "
                + size + ", sản phẩm trong kho " + border.repeat(84) + ColorText.RESET);
        return statistic;
    }

}
