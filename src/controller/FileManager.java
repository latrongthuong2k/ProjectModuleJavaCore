package controller;

import imp.Category;
import imp.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    //------------------------------------------------------------------------
//
//    /**
//     * Phương thức đọc data từ file categoryList.txt
//     *
//     * @param filePath            : đường dẫn đến file lưu
//     * @param inventoryManagement : tham chiếu đến inventoryManagement
//     */
//
//    public void readDataCategory(String filePath, InventoryManagement inventoryManagement) {
//        List<Category> categoryList = inventoryManagement.getCategoryList();
//        File file = new File(filePath);
//        if (!file.exists())
//            return;
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            //
//            Category category = null;
//            Product product = null;
//
//            while ((line = reader.readLine()) != null) {
//                if (line.startsWith("****Category****")) { // tạo một category mới tại vùng nhớ mới
//                    category = new Category();
//
//                } else if (line.startsWith("Id: ") && category != null) {
//                    category.setId(Integer.parseInt(getValueFromLine(line, "Id: ")));
//                } else if (line.startsWith("Name: ") && category != null) {
//                    category.setName(getValueFromLine(line, "Name: "));
//                } else if (line.startsWith("Description: ") && category != null) {
//                    category.setDescription(getValueFromLine(line, "Description: "));
//                } else if (line.startsWith("Status: ") && category != null) {
//                    category.setStatus(Boolean.parseBoolean(getValueFromLine(line, "Status: ")));
//                } else if (line.startsWith("Product Id: ")) {
//                    // tạo một product mới tại vùng nhớ mới
//                    product = new Product();
//                    product.setId(getValueFromLine(line, "Product Id: "));
//                } else if (line.startsWith("Product Name: ") && product != null) {
//                    product.setName(getValueFromLine(line, "Product Name: "));
//                } else if (line.startsWith("Product Description: ") && product != null) {
//                    product.setDescription(getValueFromLine(line, "Product Description: "));
//                } else if (line.startsWith("Product Status: ") && product != null) {
//                    product.setStatus(Boolean.parseBoolean(getValueFromLine(line, "Product Status: ")));
//                } else if (line.startsWith("Import Price: ") && product != null) {
//                    product.setImportPrice(Double.parseDouble(getValueFromLine(line, "ImportPrice: ")));
//                } else if (line.startsWith("Export Price: ") && product != null) {
//                    product.setExportPrice(Double.parseDouble(getValueFromLine(line, "ExportPrice: ")));
//                } else if (line.startsWith("Profit: ") && product != null) {
//                    product.setProfit(Double.parseDouble(getValueFromLine(line, "Profit: ")));
//                } else if (line.startsWith("CategoryId: ") && product != null && category != null) {
//                    product.setCategoryId(Integer.parseInt(getValueFromLine(line, "CategoryId: ")));
//                    category.getProductList().add(product);
//                    product = null; // trỏ lại về null để thực hiện gán new product mới nếu đọc tới line cần tạo
//                } else if (line.trim().equals("]")) {
//                    categoryList.add(category);
//                    category = null; // trỏ lại về null để thực hiện gán new category mới
//                }
//            }
//
//        } catch (IOException e) {
//            System.err.println("Error reading from file: " + e.getMessage() + filePath);
//        } catch (NullPointerException e) {
//            System.err.println("Lỗi: ");
//        }
//    }

    //------------------------------------------------------------------------

    public String getValueFromLine(String line, String startWithText) {
        return line.substring(startWithText.length());
    }

    //------------------------------------------------------------------------


    public void writeFileCategory(List<Category> categoryList,
                                  String projectDirectory) {
        try (PrintWriter writer = new PrintWriter("repository.txt")) {

            for (Category category : categoryList) {
                writer.println("****Category****");
                writer.println("Id: " + category.getId());
                writer.println("Name: " + category.getName());
                writer.println("Description: " + category.getDescription());
                writer.println("Status: " + category.isStatus());
                writer.println();
                writer.println("****Products****");
                writer.println("[");
                category.getProductList().stream()
                        .map(product -> "Product Id: " + product.getId() +
                                "\nProduct Name: " + product.getName() +
                                "\nProduct Description: " + product.getDescription() +
                                "\nProduct Status: " + product.isStatus() +
                                "\nImport Price: " + product.getImportPrice() +
                                "\nExport Price: " + product.getExportPrice() +
                                "\nProfit: " + product.getProfit() +
                                "\nCategoryId: " + product.getCategoryId() + "\n")
                        .forEach(writer::println);
                writer.println("]");
                writer.println();
            }
            System.out.println("Đã lưu về repository.txt");
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    //
    public void readDataProduct(InventoryManagement inventoryManagement) {
        List<Category> categoryList = inventoryManagement.getCategoryList();
        List<Product> sampleProductList = new ArrayList<>();
        File file = new File("products.txt");
        if (!file.exists())
            return;
        Product product = null;
        try (BufferedReader reader = new BufferedReader(new FileReader("products.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Product Id: ")) {
                    product = new Product();
                    product.setId(getValueFromLine(line, "Product Id: "));
                } else if (line.startsWith("Product Name: ") && product != null) {
                    product.setName(getValueFromLine(line, "Product Name: "));
                } else if (line.startsWith("Product Description: ") && product != null) {
                    product.setDescription(getValueFromLine(line, "Product Description: "));
                } else if (line.startsWith("Product Status: ") && product != null) {
                    product.setStatus(Boolean.parseBoolean(getValueFromLine(line, "Product Status: ")));
                } else if (line.startsWith("Import Price: ") && product != null) {
                    product.setImportPrice(Double.parseDouble(getValueFromLine(line, "ImportPrice: ")));
                } else if (line.startsWith("Export Price: ") && product != null) {
                    product.setExportPrice(Double.parseDouble(getValueFromLine(line, "ExportPrice: ")));
                } else if (line.startsWith("Profit: ") && product != null) {
                    product.setProfit(Double.parseDouble(getValueFromLine(line, "Profit: ")));
                } else if (line.startsWith("CategoryId: ") && product != null) {
                    product.setCategoryId(Integer.parseInt(getValueFromLine(line, "CategoryId: ")));
                } else if (line.trim().isEmpty()) {
                    sampleProductList.add(product);
                    product = null;
                }
            }
            for (Category cate : categoryList) {
                int cateId = cate.getId();
                cate.getProductList().addAll(sampleProductList.stream()
                        .filter(prod -> prod.getCategoryId() == cateId)
                        .toList());
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage() + "products.txt");
        } catch (NullPointerException e) {
            System.err.println("Lỗi: ");
        }
    }

    //
    public void readDataCategory2(InventoryManagement inventoryManagement) {
        List<Category> categoryList = inventoryManagement.getCategoryList();
        File file = new File("categories.txt");
        if (!file.exists())
            return;
        try (BufferedReader reader = new BufferedReader(new FileReader("categories.txt"))) {
            String line;

            Category category = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Id: ")) {
                    category = new Category();
                    category.setId(Integer.parseInt(getValueFromLine(line, "Id: ")));
                } else if (line.startsWith("Name: ") && category != null) {
                    category.setName(getValueFromLine(line, "Name: "));
                } else if (line.startsWith("Description: ") && category != null) {
                    category.setDescription(getValueFromLine(line, "Description: "));
                } else if (line.startsWith("Status: ") && category != null) {
                    category.setStatus(Boolean.parseBoolean(getValueFromLine(line, "Status: ")));
                } else if (line.trim().isEmpty()) {
                    categoryList.add(category);
                    category = null;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage() + "categories.txt");
        } catch (NullPointerException e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }

    //
    public void readAll(InventoryManagement inventoryManagement) {
        readDataCategory2(inventoryManagement);
        readDataProduct(inventoryManagement);
    }

    public void writeFileCategory2(List<Category> categoryList) {
        try (PrintWriter writer = new PrintWriter("categories.txt")) {
            for (Category category : categoryList) {
                writer.println("****Category****");
                writer.println("Id: " + category.getId());
                writer.println("Name: " + category.getName());
                writer.println("Description: " + category.getDescription());
                writer.println("Status: " + category.isStatus());
                writer.println();
                try (PrintWriter writer2 = new PrintWriter("products.txt")) {
                    writer2.println("****Products****");
                    category.getProductList().stream()
                            .map(product -> "Product Id: " + product.getId() +
                                    "\nProduct Name: " + product.getName() +
                                    "\nProduct Description: " + product.getDescription() +
                                    "\nProduct Status: " + product.isStatus() +
                                    "\nImport Price: " + product.getImportPrice() +
                                    "\nExport Price: " + product.getExportPrice() +
                                    "\nProfit: " + product.getProfit() +
                                    "\nCategoryId: " + product.getCategoryId() + "\n")
                            .forEach(writer2::println);
                    System.out.println("Đã lưu về products.txt");
                } catch (IOException e) {
                    System.out.println("Error saving to file: " + e.getMessage());
                }
            }
            System.out.println("Đã lưu về categories.txt");
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }
    //------------------------------------------------------------------------

}
