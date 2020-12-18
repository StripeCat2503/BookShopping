/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.daos;

import com.app.constants.MyConstants;
import com.app.dtos.ProductCategoryDTO;
import com.app.dtos.ProductDTO;
import com.app.utils.DBUtil;
import com.app.utils.MyUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DuyNK
 */
public class ProductDAO {

    private final String SQL_GET_ALL_PRODUCTS = "SELECT productID, productName, price, quantity, status, image, description, p.categoryID, c.categoryName "
            + "FROM tblProducts AS p, tblProductCategories AS c "
            + "WHERE p.categoryID = c.categoryID";

    private final String SQL_GET_PRODUCT_BY_ID = "SELECT productID, productName, price, quantity, status, image, description, categoryID "
            + "FROM tblProducts WHERE productID = ?";

    private final String SQL_GET_PRODUCT_BY_CATEGORY = "SELECT productID, productName, price, quantity, status, image, description, categoryID "
            + "FROM tblProducts WHERE categoryID = ?";

    private final String SQL_SEARCH_PRODUCT_BY_NAME = "SELECT productID, productName, price, quantity, status, image, description, categoryID "
            + "FROM tblProducts WHERE nonAccentProductName LIKE ?";

    private final String SQL_GET_PRODUCT_BY_NAME = "SELECT productID "
            + "FROM tblProducts WHERE productName = ?";

    private final String SQL_INSERT_PRODUCT = "INSERT INTO tblProducts(productName, price, quantity, image, description, status, categoryID, nonAccentProductName) "
            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

    private final String SQL_DELETE_PRODUCT = "DELETE FROM tblProducts WHERE productID = ?";

    private final String SQL_UPDATE_PRODUCT = "UPDATE tblProducts SET productName = ?, price = ?, quantity = ?, image = ?, description = ?, "
            + "status = ?, categoryID = ?, nonAccentProductName = ?"
            + "WHERE productID = ?";

    public boolean insertProduct(ProductDTO product) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean success = false;

        try {
            con = DBUtil.getConnection();
            if (con != null) {
             
                stm = con.prepareStatement(SQL_INSERT_PRODUCT);

                stm.setString(1, product.getProductName());
                stm.setDouble(2, product.getPrice());
                stm.setInt(3, product.getQuantity());
                stm.setString(4, product.getImage());
                stm.setString(5, product.getDescription());
                stm.setBoolean(6, product.isStatus());
                stm.setInt(7, product.getCategory().getCategoryID());
                
                String nonAccentProductName = MyUtils.toNonAccentString(product.getProductName());
                stm.setString(8, nonAccentProductName);

                int rows = stm.executeUpdate();

                if (rows > 0) {
                    success = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return success;
    }

    public boolean updateProduct(ProductDTO product) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean success = false;

        try {
            con = DBUtil.getConnection();
            if (con != null) {
                stm = con.prepareStatement(SQL_UPDATE_PRODUCT);

                stm.setString(1, product.getProductName());
                stm.setDouble(2, product.getPrice());
                stm.setInt(3, product.getQuantity());
                stm.setString(4, product.getImage());
                stm.setString(5, product.getDescription());
                stm.setBoolean(6, product.isStatus());
                stm.setInt(7, product.getCategory().getCategoryID());
                
                String nonAccentProductName = MyUtils.toNonAccentString(product.getProductName());
                stm.setString(8, nonAccentProductName);
                
                stm.setInt(9, product.getProductID());

                int rows = stm.executeUpdate();

                if (rows > 0) {
                    success = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return success;
    }

    public boolean deleteProduct(int productID) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean success = false;

        try {
            con = DBUtil.getConnection();
            if (con != null) {
                stm = con.prepareStatement(SQL_DELETE_PRODUCT);

                stm.setInt(1, productID);

                int rows = stm.executeUpdate();

                if (rows > 0) {
                    success = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return success;
    }

    public List<ProductDTO> getAllProducts() throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<ProductDTO> productList = null;

        try {
            con = DBUtil.getConnection();
            if (con != null) {
                stm = con.prepareStatement(SQL_GET_ALL_PRODUCTS);
                rs = stm.executeQuery();

                while (rs.next()) {
                    if (productList == null) {
                        productList = new ArrayList<>();
                    }
                    int productID = rs.getInt("productID");
                    String productName = rs.getString("productName");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    boolean isActive = rs.getBoolean("status");
                    String image = rs.getString("image");
                    image = image.isEmpty() ? MyConstants.DEFAULT_PRODUCT_IMAGE_URL : image.replace("\\", "/");
                    String des = rs.getString("description");
                    des = des.length() > 50 ? des.substring(0, 20) + "..." : des;
                    int categoryID = rs.getInt("categoryID");
                    String categoryName = rs.getString("categoryName");

                    ProductCategoryDTO category = new ProductCategoryDTO(categoryID, categoryName);

                    ProductDTO product = new ProductDTO(productID, productName, price, quantity, isActive, image, des, category);
                    productList.add(product);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return productList;
    }

    public ProductDTO getProductByID(int productID) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ProductDTO product = null;

        try {
            con = DBUtil.getConnection();
            if (con != null) {
                stm = con.prepareStatement(SQL_GET_PRODUCT_BY_ID);
                stm.setInt(1, productID);
                rs = stm.executeQuery();

                if (rs.next()) {

                    String productName = rs.getString("productName");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    boolean isActive = rs.getBoolean("status");
                    String image = rs.getString("image");
                    image = image.isEmpty() ? MyConstants.DEFAULT_PRODUCT_IMAGE_URL : image.replace("\\", "/");
                    String des = rs.getString("description");
                    int categoryID = rs.getInt("categoryID");

                    ProductCategoryDTO category = new ProductCategoryDTO(categoryID);

                    product = new ProductDTO(productID, productName, price, quantity, isActive, image, des, category);
                }
            }
        } catch (Exception e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return product;
    }

    public ProductDTO getProductByCategory(int categoryID) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ProductDTO product = null;

        try {
            con = DBUtil.getConnection();
            if (con != null) {
                stm = con.prepareStatement(SQL_GET_PRODUCT_BY_CATEGORY);
                stm.setInt(1, categoryID);
                rs = stm.executeQuery();

                if (rs.next()) {

                    int productID = rs.getInt("productID");
                    String productName = rs.getString("productName");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    boolean isActive = rs.getBoolean("status");
                    String image = rs.getString("image");
                    String des = rs.getString("des");
                    String categoryName = rs.getString("categoryName");

                    ProductCategoryDTO category = new ProductCategoryDTO(categoryID, categoryName);

                    product = new ProductDTO(productID, productName, price, quantity, isActive, image, des, category);
                }
            }
        } catch (Exception e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return product;
    }

    public List<ProductDTO> searchProductsByName(String searchValue) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<ProductDTO> searchResults = null;
        
        searchValue = MyUtils.toNonAccentString(searchValue);
        
        try {
            con = DBUtil.getConnection();
            if (con != null) {
                stm = con.prepareStatement(SQL_SEARCH_PRODUCT_BY_NAME);
                stm.setString(1, "%" + searchValue + "%");
                rs = stm.executeQuery();

                while (rs.next()) {
                    if (searchResults == null) {
                        searchResults = new ArrayList<>();
                    }
                    int productID = rs.getInt("productID");
                    String productName = rs.getString("productName");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    boolean isActive = rs.getBoolean("status");
                    String image = rs.getString("image");
                    image = image.isEmpty() ? MyConstants.DEFAULT_PRODUCT_IMAGE_URL : image.replace("\\", "/");
                    String des = rs.getString("description");
                    des = des.length() > 20 ? des.substring(0, 20) : des;
                    int categoryID = rs.getInt("categoryID");

                    ProductCategoryDTO category = new ProductCategoryDTO(categoryID, null);

                    ProductDTO product = new ProductDTO(productID, productName, price, quantity, isActive, image, des, category);
                    searchResults.add(product);
                }
            }
        } catch (Exception e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return searchResults;
    }

    public boolean isExistedProduct(String productName) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean isExisted = false;

        try {
            con = DBUtil.getConnection();
            if (con != null) {
                stm = con.prepareStatement(SQL_GET_PRODUCT_BY_NAME);
                stm.setString(1, productName);
                rs = stm.executeQuery();

                if (rs.next()) {
                    isExisted = true;
                }
            }
        } catch (Exception e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return isExisted;
    }
}
