package db;

import model.Category;
import model.Product;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    public List<Category> getAllCategories() {
        String sql = "select id, name from product_category order by id";
        List<Category> list = new ArrayList<>();

        try(Connection conn = DbConnection.getDBConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs  = ps.executeQuery()) {
                while (rs.next()) {
                    Category c = new Category(
                            rs.getInt("id"),
                            rs.getString("name")
                    );
                    list.add(c);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  list;
    }

    public List<Product> getProductList(int page, int size) {
        if (page < 1) page = 1;
        int offset = (page - 1) * size;

        String sql = "SELECT id, name, price, creation_datetime FROM product ORDER BY id LIMIT ? OFFSET ?";
        List<Product> products = new ArrayList<>();

        try (Connection conn = DbConnection.getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, size);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product p = mapRowToProduct(rs);
                    products.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    public List<Product> getProductByCriteria(String productName, String categoryName, Instant creationMax){
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct p.id, p.name, p.creation_datetime")
                .append("from product p");
        boolean joinCategory = (categoryName != null && !categoryName.isBlank());
        if (joinCategory) {
            sb.append("join product_category pc on pc.id_product = p.id");
        }
        List<Object> params = new ArrayList<>();
        List<String> where = new ArrayList<>();
        if (productName != null && !productName.isBlank()) {
            where.add(("p.name ilike ?"));
            params.add("%" + categoryName + "%");
        }
    }

}
