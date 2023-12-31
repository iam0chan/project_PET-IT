package com.pet.product.model.dao;


import static com.pet.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.pet.common.JDBCTemplate.*;
import com.pet.product.model.dto.Product;
import com.pet.product.model.dto.ProductImageFile;
import com.pet.product.model.dto.ProductOption;

public class ProductDao {

   private Properties sql = new Properties();
   {
      String path = ProductDao.class.getResource("/sql/product/product_sql.properties").getPath(); 
      try (FileReader fr = new FileReader(path)){
         sql.load(fr);
      }catch(IOException e) {
         e.printStackTrace();
      }
   }
   
   public int insertProduct(Connection conn, Product item) {
      PreparedStatement pstmt = null;
      int result = 0;
      /*
       * SEQUENCE-NAME = SEQ_PRODUCT_NO
       * PRODUCT_NO, CATEGORY_NO, TYPE_NO, PRODUCT_NAME, 
       * PRODUCT_PRICE, PRODUCT_STOCK, PRODUCT_TOTAL_SALES,
       * PRODUCT_INFO, PRODUCT_ENROLL_DATE, PRODUCT_ENROLL_STATUS,
       * PRODUCT_OPTION_STATUS, PRODUCT_DISCOUNT, PRODUCT_POINT,
       * PRODUCT_CONTENT
       */
      System.out.println(item.toString());
      try {
         pstmt = conn.prepareStatement(sql.getProperty("insertProduct"));
         pstmt.setString(1, item.getCategoryNo());
         pstmt.setString(2, item.getTypeNo());
         pstmt.setString(3, item.getProductName());
         pstmt.setInt(4, item.getProductPrice());
         pstmt.setInt(5, item.getProductStock());
         pstmt.setString(6, item.getProductInfo());
         pstmt.setString(7, item.getProductOptionStatus()); // 23/11/17 00:45 추가
         pstmt.setString(8,item.getProductDiscount());
         pstmt.setInt(9,item.getProuctPoint());
         pstmt.setString(10, item.getProductContent());
         
         result = pstmt.executeUpdate();
      }catch(SQLException e) {
         e.printStackTrace();
      }finally {
         close(pstmt);         
      }
      return result;
   
   }
   
   public int insertMainImageFile(Connection conn, String oriname, String rename) {
      /*
       * SEQUENCE-NAME = SEQ_PRODUCT_FILE_NO
       * PRODUCT_FILE_NO
       * PRODUCT_NO(FK)
       * PRODUCT_FILE_ORINAME
       * PRODUCT_FILE_RENAME
       * PRODUCT_FILE_ENROLL_DATE
       * PRODUCT_FILE_MAIN_IMAGE
       * PRODUCT_FILE_DETAIL_IMAGE
       */
      PreparedStatement pstmt = null;
      int fileUploadResult = 0;
      try {
         pstmt = conn.prepareStatement(sql.getProperty("insertMainImageFile"));
         pstmt.setString(1, oriname);
         pstmt.setString(2, rename);
         
         fileUploadResult = pstmt.executeUpdate();
         
      }catch(SQLException e) {
         e.printStackTrace();
      }finally {
         close(pstmt);
      }
      
      return fileUploadResult;
   }
   
   public int insertOption(Connection conn, Map<String,String> options) {
      /*
       * SEQUENCE-NAME = SEQ_OPTION_NO
       * PRODUCT_OPTION_NO
       * PRODUCT_OPTION_NAME
       * PRODUCT_NO
       * PRODUCT_OPTION_PRICE
       */
      PreparedStatement pstmt = null;
      int optionResult = 0;
      int inputCount = 0;
      try {
         for(Map.Entry<String,String> entry: options.entrySet()) {
            pstmt = conn.prepareStatement(sql.getProperty("insertOption"));
            pstmt.setString(1,entry.getKey());
            pstmt.setInt(2, Integer.parseInt(entry.getValue()));
            optionResult = pstmt.executeUpdate();
            if(optionResult>0) {
               ++inputCount;
            }
         }
         System.out.println("입력 성공 옵션 수 : "+inputCount);
         
      }catch(SQLException e) {
         e.printStackTrace();
      }finally {
         close(pstmt);
      }
      
      return optionResult;
   }
   
   public int insertNewOption(Connection conn, String productNo, List<ProductOption> options) {
         PreparedStatement pstmt = null;
         int insertNewOptionResult = 0;
         int inputCount = 0;
         
         try {
            for(ProductOption po : options) {
               pstmt = conn.prepareStatement(sql.getProperty("insertNewOption"));
               pstmt.setString(1, productNo);
               pstmt.setString(2, po.getProductOptionName());
               pstmt.setInt(3, po.getProductOptionPrice());
               insertNewOptionResult = pstmt.executeUpdate();
               if(insertNewOptionResult>0) {
                   ++inputCount;
                }
            }
            System.out.println("입력 성공 옵션 수 : "+inputCount);
         }catch(SQLException e) {
            e.printStackTrace();
         }
         
         return insertNewOptionResult;
   }
   
   
   public List<Product> selectProductListAll(Connection conn,  int cPage, int numPerpage, String type){
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      List<Product> result = new ArrayList<>();
      try {
        if(type.equals("all")) {
            pstmt = conn.prepareStatement(sql.getProperty("selectProductListAll"));
            pstmt.setInt(1, (cPage-1)*numPerpage+1);
            pstmt.setInt(2, cPage*numPerpage);
        }else if(type.equals("new")) {
           pstmt = conn.prepareStatement(sql.getProperty("selectProductListByNew"));
            pstmt.setInt(1, (cPage-1)*numPerpage+1);
            pstmt.setInt(2, cPage*numPerpage);
        }else {
           pstmt = conn.prepareStatement(sql.getProperty("selectProductListByType"));
             pstmt.setString(1, type);
             pstmt.setInt(2, (cPage-1)*numPerpage+1);
             pstmt.setInt(3, cPage*numPerpage);
        }
        rs = pstmt.executeQuery();
         while(rs.next()) {
            result.add(getProduct(rs));
         }
      }catch(SQLException e) {
         e.printStackTrace();
      }
      
      return result; 
   }
   
   public int getProductCount(Connection conn) {
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      int result = 0;
      try {
         pstmt = conn.prepareStatement(sql.getProperty("getProductCount"));
         rs = pstmt.executeQuery();
         
         if(rs.next()) {
            result = rs.getInt("count");
         }
      }catch(SQLException e) {
         e.printStackTrace();
      }
      
      return result;
   }
   
   public Product selectProductByNo(Connection conn, String ProductNo) {
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      Product product = null;
      try {
         pstmt = conn.prepareStatement(sql.getProperty("selectProductByNo"));
         pstmt.setString(1, ProductNo);
         rs = pstmt.executeQuery();
         
         if(rs.next()) {
            product = getProduct(rs);
         }
         
      }catch(SQLException e) {
         e.printStackTrace();
      }
      System.out.println(product.toString());
      return product;
   }
   
   
   public List<ProductImageFile> selectMainImageFileAll(Connection conn){
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      List<ProductImageFile> files = new ArrayList<>();
      
      try {
         pstmt = conn.prepareStatement(sql.getProperty("selectMainImageFileAll"));
         rs = pstmt.executeQuery();
         
         while(rs.next()) {
            files.add(getMainImageFile(rs));
         }
      }catch(SQLException e) {
         e.printStackTrace();
      }finally {
         close(rs);
         close(pstmt);
      }
      
      return files;      
   }
   
   public ProductImageFile selectMainImageFile(Connection conn, String productNo) {
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      ProductImageFile file = null;
      try {
         pstmt = conn.prepareStatement(sql.getProperty("selectMainImageFile"));
         pstmt.setString(1, productNo);
         rs = pstmt.executeQuery();
         if(rs.next()) file = getMainImageFile(rs);
        // System.out.println(file.toString());
      }catch(SQLException e) {
         e.printStackTrace();
      }finally {
         close(rs);
         close(pstmt);
      }      
      return file;
   }
   
   public List<ProductOption> selecOptionByProductNo(Connection conn, String productNo){
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      List<ProductOption> options = new ArrayList<>();
      try {
         pstmt = conn.prepareStatement(sql.getProperty("selecOptionByProductNo"));
         pstmt.setString(1, productNo);
         rs = pstmt.executeQuery();
         while(rs.next()) {
            options.add(getProductOption(rs));
         }
         System.out.print(options);
      }catch(SQLException e) {
         e.printStackTrace();
      }finally {
         close(rs);
         close(pstmt);
      }
      
      return options;
   }
   
   public int deleteProductByProductNo(Connection conn, String productNo) {
      PreparedStatement pstmt = null;
      int result = 0;
      try {
         pstmt = conn.prepareStatement(sql.getProperty("deleteProductStatus"));
         pstmt.setString(1, productNo);
         result = pstmt.executeUpdate();
      }catch(SQLException e) {
         e.printStackTrace();
      }finally {
         close(pstmt);
      }
      
      return result;
   }
   
   public int getProductCountByType(Connection conn, String type) {
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      int dataCount = 0;
      
      try {
         if(type.equals("all")) {
         pstmt = conn.prepareStatement(sql.getProperty("getProductCount"));
         }else if(type.equals("new")) {
         //new -> 등록일 3일 이내 상품
            pstmt = conn.prepareStatement(sql.getProperty("getNewProductCountByNew"));
         }else {
            pstmt = conn.prepareStatement(sql.getProperty("getProductCountByType"));
            pstmt.setString(1, type);
         }
         rs = pstmt.executeQuery();
         if(rs.next()) dataCount = rs.getInt(1);
      }catch(SQLException e) {
         e.printStackTrace();
      }finally {
         close(rs);
         close(pstmt);
      }
      
      return dataCount;
   }
   
   public int updateProduct(Connection conn, Product p) {
      PreparedStatement pstmt = null;
     
      int updateResult = 0;
      try {
         pstmt = conn.prepareStatement(sql.getProperty("updateProductByNo"));
         pstmt.setString(1, p.getCategoryNo());
         pstmt.setString(2,p.getTypeNo());
         pstmt.setInt(3, p.getProductPrice());
         pstmt.setInt(4, p.getProductStock());
         pstmt.setString(5, p.getProductInfo());
         pstmt.setString(6,p.getProductDiscount());
         pstmt.setString(7,p.getProductContent());
         pstmt.setString(8,p.getProductOptionStatus());
         pstmt.setString(9, p.getProductNo());
         
         updateResult = pstmt.executeUpdate();
         
      }catch(SQLException e) {
         e.printStackTrace();
      }finally {
         close(pstmt);
      }
      
      return updateResult;
   }
   
   public int updateMainImg(Connection conn, String productNo, String ori, String re) {
      PreparedStatement pstmt = null;
      int updateImg = 0;
      try {
         pstmt = conn.prepareStatement(sql.getProperty("updateMainImg"));
         pstmt.setString(1,ori);
         pstmt.setString(2,re);
         pstmt.setString(3,productNo);
         updateImg = pstmt.executeUpdate();
         
      }catch(SQLException e) {
         e.printStackTrace();
      }finally {
         close(pstmt);
      }
      
      return updateImg;
   }
   
   public int updateOption(Connection conn,String productNo ,List<ProductOption> options) {
      PreparedStatement pstmt = null;
      int updateOption = 0;
      try {
         for(ProductOption po: options) {
            pstmt = conn.prepareStatement(sql.getProperty("updateOptions"));
            pstmt.setString(1, po.getProductOptionName());
            pstmt.setInt(2, po.getProductOptionPrice());
            pstmt.setString(3, productNo);
            updateOption += pstmt.executeUpdate();
            
         }

      }catch(SQLException e) {
         
      }finally {
         close(pstmt);
      }
      
      return updateOption;
   }
   
   public int deleteProductOption(Connection conn, String productNo) {
      PreparedStatement pstmt = null;
      int delResult = 0;
      try {
            pstmt = conn.prepareStatement(sql.getProperty("deleteOptions"));
            pstmt.setString(1, productNo);           
            delResult=pstmt.executeUpdate();
      }catch(SQLException e) {
         e.printStackTrace();
      }finally {
         close(pstmt);
      }
      
      return delResult;
   }
   
   
   public int deleteProductImage(Connection conn, String productNo) {
      PreparedStatement pstmt = null;
      int delResult = 0;
      
      try {
         pstmt = conn.prepareStatement(sql.getProperty("delMainImg"));
         pstmt.setString(1, productNo);
         delResult = pstmt.executeUpdate();
      }catch(SQLException e) {
         e.printStackTrace();
      }finally {
         close(pstmt);
      }
      
      return delResult;
      
   }
   
   public int selectProductCountByKeyword(Connection conn, String keyword) {
	   PreparedStatement pstmt = null;
	   ResultSet rs = null;
	   int totalData = 0;
	   try {
		   pstmt = conn.prepareStatement(sql.getProperty("selectProductCountByKeyword"));
		   pstmt.setString(1,'%'+keyword+'%');
		   rs = pstmt.executeQuery();
		   
		   if(rs.next()) totalData = rs.getInt(1);
	   }catch(SQLException e) {
		   e.printStackTrace();
	   }finally {
		   close(rs);
		   close(pstmt);
	   }
	   
	   return totalData;
   }
   
   public List<Product> selectProductListByKeyword(Connection conn, int cPage, int numPerPage, String keyword){
	   PreparedStatement pstmt = null;
	   ResultSet rs = null;
	   List<Product> products = new ArrayList<>();
	   
	   try {
		   pstmt = conn.prepareStatement(sql.getProperty("selectProductListByKeyword"));
		   pstmt.setString(1, '%'+keyword+'%');
		   pstmt.setInt(2, (cPage-1)*numPerPage+1);
		   pstmt.setInt(3, cPage*numPerPage);
		   rs = pstmt.executeQuery();
		   while(rs.next()) {
			   products.add(getProduct(rs));
		   }
		   
	   }catch(SQLException e) {
		   e.printStackTrace();
	   }finally {
		   close(rs);
		   close(pstmt);
	   }
	   
	   return products;
   }
   
   /*
    * public ProductOption getOptionName(Connection conn, String productNo, String
    * optionPrice) { PreparedStatement pstmt = null; ResultSet rs = null;
    * 
    * try { pstmt = conn.prepareStatement(sql.getProperty("getOptionName"));
    * 
    * }catch(SQLException e) { e.printStackTrace(); }
    * 
    * }
    */
   
   /* Product-Table
    * PRODUCT_NO, CATEGORY_NO, TYPE_NO, PRODUCT_NAME, 
    * PRODUCT_PRICE, PRODUCT_STOCK, PRODUCT_TOTAL_SALES,
    * PRODUCT_INFO, PRODUCT_ENROLL_DATE, PRODUCT_ENROLL_STATUS,
    * PRODUCT_OPTION_STATUS, PRODUCT_DISCOUNT, PRODUCT_POINT,
    * PRODUCT_CONTENT
    */
   private Product getProduct(ResultSet rs) throws SQLException{
      return Product.builder()
            .productNo(rs.getString("product_no"))
            .categoryNo(rs.getString("category_no"))
            .typeNo(rs.getString("type_no"))
            .productName(rs.getString("product_name"))
            .productPrice(rs.getInt("product_price"))
            .productStock(rs.getInt("product_stock"))
            .productTotalSales(rs.getInt("product_total_sales"))
            .productInfo(rs.getString("product_info"))
            .productEnrollDate(rs.getDate("product_enroll_date"))
            .productEnrollStatus(rs.getString("product_enroll_status"))
            .productOptionStatus(rs.getString("product_option_status"))
            .productDiscount(rs.getString("product_discount"))
            .prouctPoint(rs.getInt("product_point"))
            .productContent(rs.getString("product_content"))
            .build();
            
   }
   
   private ProductImageFile getMainImageFile(ResultSet rs) throws SQLException {
      return ProductImageFile.builder()
            .productFileNo(rs.getString("product_file_no"))
            .productNo(rs.getString("product_no"))
            .productFileOriName(rs.getString("product_file_oriname"))
            .productFileRename(rs.getString("product_file_rename"))
            .productFileEnrollDate(rs.getDate("product_file_enroll_date"))
            .productFileMainImage(rs.getString("product_file_main_Image"))
            .build();
   }
   
   private ProductOption getProductOption(ResultSet rs) throws SQLException{
      return ProductOption.builder()
            .productOptionNo(rs.getString("product_option_no"))
            .productNo(rs.getString("product_no"))
            .productOptionName(rs.getString("product_option_name"))
            .productOptionPrice(rs.getInt("product_option_price")).build();
            
      
   }
   
}