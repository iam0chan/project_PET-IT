package com.pet.cart.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.pet.cart.model.dto.Cart;
import com.pet.cart.model.service.CartService;
import com.pet.member.dto.Member;

/**
 * Servlet implementation class CartList
 */
@WebServlet("/cart/cartList.do")
public class CartList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		 // 사용자의 세션에서 회원 아이디를 가져오기
        request.setCharacterEncoding("utf-8");
		Member member = (Member)request.getSession().getAttribute("loginMember");
		
     // 장바구니 서비스를 통해 장바구니 목록을 가져오기
        CartService cartService = new CartService();
        List<Cart> cartList = cartService.selectCart(1, 10, member.getMemberId());

//        Cart c = new Cart();// dto에 주문정보 담아서 전달 방법
        
     // JSP 페이지로 전달하기 위해 request 속성에 장바구니 목록 설정
        request.setAttribute("cartList", cartList);
        request.setAttribute("cartListJson", new Gson().toJson(cartList));
//        String productNo = request.getParameter("productNo");
//        Product product = new ProductService().selectProductByNo(productNo);
		/* c.setP(product); */ // dto 생성 후 전달방법
//        System.out.println("cartServlet : "+product.toString());
          
//        String orderPrice = request.getParameter("orderPrice");
//        String orderAmount = request.getParameter("orderAmount");
//        String optionPrice = request.getParameter("optionPrice");
//        String optionName = request.getParameter("optionName");
//        System.out.println("주문가격 : "+orderPrice+" 주문 수량 : "+orderAmount+" 옵션이름 : "+ optionName+" 옵션가격 : "+optionPrice);
//        
        
        
        request.setAttribute("cartList",cartList);

        // JSP 페이지로 포워딩
        request.getRequestDispatcher("/views/cart/cartList.jsp").forward(request, response);
    }
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
