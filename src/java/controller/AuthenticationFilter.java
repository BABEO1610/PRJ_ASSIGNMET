package controller;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

// Chặn toàn bộ mọi URL trên trang web
@WebFilter(urlPatterns = {"/*"})
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
            
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        // Lấy đường dẫn mà người dùng đang cố gắng truy cập
        String requestURI = req.getRequestURI();
        
        // Đường dẫn tới trang login của bạn (Sửa lại cho đúng với tên file/servlet của bạn)
        String loginURI = req.getContextPath() + "/login"; 
        String loginJsp = req.getContextPath() + "/login.jsp";

        // Kiểm tra xem người dùng có đang vào những trang KHÔNG CẦN đăng nhập không
        boolean isLoginRequest = requestURI.equals(loginURI) || requestURI.equals(loginJsp);
        
        // (Tùy chọn) Cho phép tải CSS, ảnh, JS để trang Login không bị lỗi giao diện trắng tinh
        boolean isStaticResource = requestURI.startsWith(req.getContextPath() + "/css/") || 
                           requestURI.startsWith(req.getContextPath() + "/images/") ||
                           requestURI.startsWith(req.getContextPath() + "/assets/") || 
                           requestURI.startsWith(req.getContextPath() + "/vendor/");

        // Kiểm tra xem đã có session và thông tin đăng nhập chưa
        boolean isLoggedIn = (session != null && session.getAttribute("USER_INFO") != null);

        // LUỒNG XỬ LÝ CHÍNH:
        if (isLoggedIn || isLoginRequest || isStaticResource) {
            // 1. Nếu đã đăng nhập, HOẶC đang cố vào trang Login, HOẶC đang tải CSS -> Cho đi tiếp
            chain.doFilter(request, response);
        } else {
            // 2. Chưa đăng nhập mà dám gõ URL vào Home, Apartments, Employees... -> Bẻ lái về Login
            res.sendRedirect(loginURI);
        }
    }

    @Override
    public void destroy() {}
}