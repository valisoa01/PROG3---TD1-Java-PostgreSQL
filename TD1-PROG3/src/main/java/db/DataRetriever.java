package db;

import model.Category;
import model.Product;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    public List<Category> getAllCategories() {
        String sql = "SELECT id, name FROM product_category ORDER BY id";
        List<Category> list = new ArrayList<>();

        try (Connection conn = DbConnection.getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Category(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> getProductList(int page, int size) {
        if (page < 1) page = 1;
        int offset = (page - 1) * size;

        String sql = "SELECT id, name, creation_datetime FROM product " +
                "ORDER BY id LIMIT ? OFFSET ?";
        List<Product> list = new ArrayList<>();

        try (Connection conn = DbConnection.getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, size);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToProduct(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> getProductsByCriteria(
            String productName,
            String categoryName,
            Instant creationMin,
            Instant creationMax
    ) {

        StringBuilder sb = new StringBuilder(
                "SELECT DISTINCT p.id, p.name, p.creation_datetime " +
                        "FROM product p "
        );

        boolean joinCategory = (categoryName != null && !categoryName.isBlank());
        if (joinCategory) {
            sb.append("JOIN product_category pc ON pc.product_id = p.id ");
        }

        List<String> where = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        if (productName != null && !productName.isBlank()) {
            where.add("p.name ILIKE ?");
            params.add("%" + productName + "%");
        }

        if (categoryName != null && !categoryName.isBlank()) {
            where.add("pc.name ILIKE ?");
            params.add("%" + categoryName + "%");
        }

        if (creationMin != null) {
            where.add("p.creation_datetime >= ?");
            params.add(Timestamp.from(creationMin));
        }

        if (creationMax != null) {
            where.add("p.creation_datetime <= ?");
            params.add(Timestamp.from(creationMax));
        }

        if (!where.isEmpty()) {
            sb.append("WHERE ").append(String.join(" AND ", where)).append(" ");
        }

        sb.append("ORDER BY p.id");

        List<Product> list = new ArrayList<>();

        try (Connection conn = DbConnection.getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sb.toString())) {

            for (int i = 0; i < params.size(); i++) {
                Object o = params.get(i);
                if (o instanceof String) {
                    ps.setString(i + 1, (String) o);
                } else if (o instanceof Timestamp) {
                    ps.setTimestamp(i + 1, (Timestamp) o);
                }
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToProduct(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Product> getProductsByCriteria(
            String productName,
            String categoryName,
            Instant creationMin,
            Instant creationMax,
            int page,
            int size
    ) {

        List<Product> all = getProductsByCriteria(
                productName,
                categoryName,
                creationMin,
                creationMax
        );

        if (page < 1) page = 1;
        int start = (page - 1) * size;
        int end = Math.min(start + size, all.size());

        if (start >= all.size()) return new ArrayList<>();

        return all.subList(start, end);
    }

    private Product mapRowToProduct(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));

        Timestamp ts = rs.getTimestamp("creation_datetime");
        if (ts != null) {
            p.setCreationDatetime(ts.toInstant());
        }

        return p;
    }
}
