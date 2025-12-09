import db.DataRetriever;
import model.Product;
import model.Category;

import java.time.Instant;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        DataRetriever dr = new DataRetriever();

        System.out.println("===== TEST 1 : Toutes les catégories =====");
        List<Category> categories = dr.getAllCategories();
        categories.forEach(System.out::println);

        System.out.println("\n===== TEST 2 : Pagination simple =====");
        List<Product> page1 = dr.getProductList(1, 2);
        page1.forEach(System.out::println);

        System.out.println("\n===== TEST 3 : Filtrage par nom produit =====");
        List<Product> byName = dr.getProductsByCriteria("Laptop", null, null, null);
        byName.forEach(System.out::println);

        System.out.println("\n===== TEST 4 : Filtrage par nom category =====");
        List<Product> byCategory = dr.getProductsByCriteria(null, "Electronics", null, null);
        byCategory.forEach(System.out::println);

        System.out.println("\n===== TEST 5 : Filtrage par date min =====");
        List<Product> byDateMin = dr.getProductsByCriteria(null, null, Instant.parse("2024-01-01T00:00:00Z"), null);
        byDateMin.forEach(System.out::println);

        System.out.println("\n===== TEST 6 : Filtrage + Pagination =====");
        List<Product> filteredPaged = dr.getProductsByCriteria(
                null,
                "Electronics",
                null,
                null,
                1,
                1
        );
        filteredPaged.forEach(System.out::println);
    }
}
