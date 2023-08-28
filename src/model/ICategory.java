package model;

import imp.Category;

import java.util.List;
import java.util.Scanner;

public interface ICategory {
    
    void inputData(Scanner scanner, List<Category> categoryList);

    void displayData();
}
