package com.pet.faq.model.dao;

import static com.pet.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.pet.faq.model.dto.Faq;




public class FaqDao {

    private Properties sql = new Properties();

    {
        String path = FaqDao.class.getResource("/sql/faq/faq_sql.properties").getPath();
        try (FileReader fr = new FileReader(path)) {
            sql.load(fr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Faq> selectFaq(Connection conn, int cPage, int numPerpage) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Faq> result = new ArrayList<>();

        try {
            pstmt = conn.prepareCall(sql.getProperty("selectFaq"));
            pstmt.setInt(1, (cPage - 1) * numPerpage + 1);
            pstmt.setInt(2, cPage * numPerpage);
            rs = pstmt.executeQuery();  
            while (rs.next()) {
                result.add(getFaq(rs));
            }
        } catch (SQLException e) {
            // 적절한 예외 처리를 추가할 수 있습니다.
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return result;
    }
    
    //조회수
    public Faq selectFaqByNo(Connection conn, String faqNo) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Faq f=null;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("selectFaqByNo"));
			pstmt.setString(1, faqNo); 
			rs=pstmt.executeQuery();
			if(rs.next()) f=getFaq(rs);
			conn.commit();
		}catch(SQLException e) {
			e.printStackTrace();
			
		}finally {
			close(rs);
			close(pstmt);
		}return f;
		
	}
    
    //조회수 
    public int updateFaqReadCount(Connection conn, String FaqNo) {
    	PreparedStatement pstmt = null;
    	int result=0; 
    	try {
    		pstmt = conn.prepareStatement(sql.getProperty("updateFaqReadCount"));
    		pstmt.setString(1,FaqNo);
    		result=pstmt.executeUpdate();
    		conn.commit();
    	}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
    }
    

    public int insertFaq (Connection conn, Faq f){
    	PreparedStatement pstmt = null;
    	int result=0;
    	try {
    		pstmt=conn.prepareStatement(sql.getProperty("insertFaq"));
    		pstmt.setString(1,f.getFaqCategory());
    		pstmt.setString(2,f.getFaqTitle());
    		pstmt.setString(3,f.getFaqContent());
    		result=pstmt.executeUpdate();
    	}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
    	
    	
    }
    
    //전체출력 페이징처리 전체 데이터 수 
    public int selectFaqCount(Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;
        try {
            pstmt = conn.prepareStatement(sql.getProperty("selectFaqCount"));
            rs = pstmt.executeQuery();
            if (rs.next()) result = rs.getInt(1);
        } catch (SQLException e) {
            
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return result;
    }

    
    //카테고리별 전체 데이터수 =페이징처리
    public int selectFaqCountByCategory(Connection conn, String category) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;
        try {
            pstmt = conn.prepareStatement(sql.getProperty("selectFaqCountByCategory"));
            pstmt.setString(1, category);
            rs = pstmt.executeQuery();
            if (rs.next()) result = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return result;
    }
    
    
    
    
    public List<Faq> selectFaqCategory(Connection conn, int cPage, int numPerpage, String category){
    	PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Faq> result = new ArrayList<>();

        try {
            pstmt = conn.prepareCall(sql.getProperty("selectFaqCategory"));
            pstmt.setString(1, category);
            pstmt.setInt(2, (cPage - 1) * numPerpage + 1);
            pstmt.setInt(3, cPage * numPerpage);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(getFaq(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return result;
    }
   
    public List<Faq> searchFaqByMenu(Connection conn, String subject, String content, int cPage, int numPerPage) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Faq> result = new ArrayList<>();
        String query = sql.getProperty("searchFaqByMenu");
        query = query.replace("#COL", subject); 

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, content); // content -> 사용자 입력 input value 값을 keyword로 가져오기
            pstmt.setInt(2, (cPage - 1) * numPerPage + 1);
            pstmt.setInt(3, cPage * numPerPage);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                result.add(searchFaq(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }

        return result;
    }
    
    public List<Faq> faqSearchTitle(Connection conn, int cPage, int numPerpage, String keyword){
    	PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Faq> result = new ArrayList<>();
        
        try {
        	pstmt = conn.prepareStatement(sql.getProperty("faqSearchTitle"));
        	pstmt.setString(1, keyword);
            pstmt.setInt(2, (cPage - 1) * numPerpage + 1);
            pstmt.setInt(3, cPage * numPerpage);
            rs = pstmt.executeQuery();
            while(rs.next()) {
            	result.add(getFaq(rs));
            }
        }catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
        return result;     
    } 
    
    public List <Faq> faqSearchContenet(Connection conn, int cPage, int numPerpage, String keyword){
    	PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Faq> result = new ArrayList<>();
        try {
        	pstmt = conn.prepareStatement(sql.getProperty("faqSearchContenet"));
        	pstmt.setString(1, keyword);
            pstmt.setInt(2, (cPage - 1) * numPerpage + 1);
            pstmt.setInt(3, cPage * numPerpage);
            rs = pstmt.executeQuery();
            while(rs.next()) {
            	result.add(getFaq(rs));
            }
        }catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
        return result;     
    } 
    

    private Faq searchFaq(ResultSet rs) throws SQLException {
        return Faq.builder()
                .faqTitle(rs.getString("FAQ_TITLE"))
                .faqContent(rs.getString("FAQ_CONTENT"))
                .build();
    }
    
  
    private Faq getFaq(ResultSet rs) throws SQLException {
        return Faq.builder()
                .faqNo(rs.getString("FAQ_NO"))
                .faqCategory(rs.getString("FAQ_CATEGORY"))
                .faqTitle(rs.getString("FAQ_TITLE"))
                .faqHits(rs.getInt("FAQ_HITS"))
                .faqContent(rs.getString("FAQ_CONTENT"))
                .build();
    }

	public int cntadd(Connection conn, String faqno) {
	      PreparedStatement pstmt = null;
	      System.out.println("cntadd 실행:"+faqno);
          int result = 0;
	        try {
	            pstmt = conn.prepareStatement("update FAQ set FAQ_hits = FAQ_hits+1 where faq_no=?");
	            pstmt.setString(1, faqno); 
	 	        result = pstmt.executeUpdate();
	 	        
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	        	
	          close(pstmt);
	        }

		return result;
	}
}
