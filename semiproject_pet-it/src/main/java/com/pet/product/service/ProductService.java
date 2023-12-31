package com.pet.product.service;

import static com.pet.common.JDBCTemplate.close;
import static com.pet.common.JDBCTemplate.commit;
import static com.pet.common.JDBCTemplate.getConnection;
import static com.pet.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.pet.product.model.dao.ProductDao;
import com.pet.product.model.dto.Product;
import com.pet.product.model.dto.ProductImageFile;
import com.pet.product.model.dto.ProductOption;

public class ProductService {

   private ProductDao dao = new ProductDao();

   public int insertProduct(Product item, String oriname, String rename, Map<String, String> options) {
      Connection conn = getConnection();
      int result = dao.insertProduct(conn, item);
      int fileUploadResult = 0;
      int optionResult = 0;
      if (result > 0) {
         fileUploadResult = dao.insertMainImageFile(conn, oriname, rename);
         if (item.getProductOptionStatus().equals("Y")) {
            optionResult = dao.insertOption(conn, options);
         }
         if (fileUploadResult > 0) {
            commit(conn);
         }
      } else {
         rollback(conn);
      }
      close(conn);
      return result;
   }

   public List<Product> selectProductListAll(int cPage, int numPerpage, String type) {
      Connection conn = getConnection();
      List<Product> result = dao.selectProductListAll(conn, cPage, numPerpage, type);
      close(conn);

      return result;
   }

   public int getProductCount() {
      Connection conn = getConnection();
      int count = dao.getProductCount(conn);
      close(conn);

      return count;
   }

   public Product selectProductByNo(String productNo) {
      Connection conn = getConnection();
      Product product = dao.selectProductByNo(conn, productNo);
      List<ProductOption> options = dao.selecOptionByProductNo(conn, productNo);
      System.out.println("******************옵션 수: " + options.size());
      for (ProductOption po : options) {
         System.out.println("******************옵션 내용:" + po);
      }
      product.setProductOption(options);
      close(conn);

      return product;
   }

   public List<ProductImageFile> selectMainImageFileAll() {
      Connection conn = getConnection();
      List<ProductImageFile> files = dao.selectMainImageFileAll(conn);
      close(conn);

      return files;
   }

   public ProductImageFile selectMainImageFile(String productNo) {
      Connection conn = getConnection();
      ProductImageFile file = dao.selectMainImageFile(conn, productNo);
      close(conn);

      return file;
   }

   public int deleteProductByProductNo(String productNo) {
      Connection conn = getConnection();
      int result = dao.deleteProductByProductNo(conn, productNo);
      if (result > 0) {
         commit(conn);
      } else {
         rollback(conn);
      }
      close(conn);

      return result;

   }

   public int getProductCountByType(String type) {
      Connection conn = getConnection();
      int dataCount = dao.getProductCountByType(conn, type);
      close(conn);

      return dataCount;
   }

   public int deleteProductImage(String productNo) {
      Connection conn = getConnection();
      int result = dao.deleteProductImage(conn, productNo);
      if (result > 0) {
         commit(conn);
      } else {
         rollback(conn);
      }
      close(conn);

      return result;
   }

   public int updateProduct(Product p) {
      Connection conn = getConnection();
      int updateResult = dao.updateProduct(conn, p);
      if (updateResult > 0) {
         commit(conn);
      } else {
         rollback(conn);
      }
      close(conn);

      return updateResult;
   }

   public int updateMainImg(String productNo, String oriname, String rename) {
      Connection conn = getConnection();
      int updateResult = dao.updateMainImg(conn, productNo, oriname, rename);
      close(conn);

      return updateResult;
   }

   public List<ProductOption> selectProductOptionsByNo(String productNo) {
      Connection conn = getConnection();
      List<ProductOption> options = dao.selecOptionByProductNo(conn, productNo);
      close(conn);

      return options;
   }

   public int updateProductOption(String productNo, List<ProductOption> options) {
      Connection conn = getConnection();
      int updateResult = 0;
      int deleteOptionResult = 0;
      Product p = dao.selectProductByNo(conn, productNo);
  
   	  deleteOptionResult = dao.deleteProductOption(conn, productNo);
      
      System.out.println("옵션삭제 확인: "+deleteOptionResult);
      if (deleteOptionResult > 0 && p.getProductOption()==null) {
    	  System.out.println("삭제가 문제없니?");
    	 updateResult = dao.updateOption(conn, productNo, options);
    	 if(updateResult>0) commit(conn);
      } else {
         rollback(conn);
      }

      close(conn);

      return updateResult;
   }
   
   public int selectProductCountByKeyword(String keyword) {
	   Connection conn = getConnection();
	   int totalData = dao.selectProductCountByKeyword(conn,keyword);
	   close(conn);
	   
	   return totalData;
   }
   
   public List<Product> selectProductListByKeyword(int cPage, int numPerPage, String keyword){
	  Connection conn = getConnection();
	  List<Product> products = dao.selectProductListByKeyword(conn, cPage, numPerPage, keyword);
	  close(conn);
	  
	  return products;
	   
   }
}