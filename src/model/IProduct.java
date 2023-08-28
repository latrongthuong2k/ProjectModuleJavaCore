package model;

import imp.Category;
import imp.Product;

import java.util.List;
import java.util.Scanner;

public interface IProduct {
    float MIN_INTEREST_RATE = 0.2f;

    void inputData(Scanner scanner, List<Product> productList);

    void displayData(List<Category> categoryList);

    void calProfit();
}
