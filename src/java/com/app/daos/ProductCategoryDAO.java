/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.daos;

import com.app.dtos.ProductCategoryDTO;
import com.app.utils.DBUtil;
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
public class ProductCategoryDAO {
    
    private final String SQL_GET_ALL_CATEGORY = "SELECT categoryID, categoryName FROM tblProductCategories";
    
    public List<ProductCategoryDTO> getAllCategories() throws SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<ProductCategoryDTO> categoryList = null;

        try {
            con = DBUtil.getConnection();
            if (con != null) {
                stm = con.prepareStatement(SQL_GET_ALL_CATEGORY);
                rs = stm.executeQuery();

                while (rs.next()) {
                    if (categoryList == null) {
                        categoryList = new ArrayList<>();
                    }
                    int categoryID = rs.getInt("categoryID");
                    String categoryName = rs.getString("categoryName");
                    
                    ProductCategoryDTO category = new ProductCategoryDTO(categoryID, categoryName);
                    categoryList.add(category);
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

        return categoryList;
    }
}
