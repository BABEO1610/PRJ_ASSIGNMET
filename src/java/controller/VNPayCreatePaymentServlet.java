package controller;

import dal.*;
import utils.VNPayConfig;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/create-payment")
public class VNPayCreatePaymentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String[] billIds = request.getParameterValues("billIds");

        if (billIds == null) {
            String errBill="Hãy chọn một khoản thanh toán";
            request.setAttribute("errBill", errBill);
            request.getRequestDispatcher("user-bills").forward(request, response);
            //response.sendRedirect("user-bills");
            return;
        }

        BillDAO billDAO = new BillDAO();
        PaymentDAO paymentDAO = new PaymentDAO();

        double totalAmount = 0;
        String txnRef = String.valueOf(System.currentTimeMillis());

        for (String id : billIds) {
            int billId = Integer.parseInt(id);
            double amount = billDAO.getBillAmount(billId);

            totalAmount += amount;
            paymentDAO.insertPendingPayment(billId, amount, txnRef);
        }

        try {

            Map<String, String> vnpParams = new HashMap<>();

            vnpParams.put("vnp_Version", "2.1.0");
            vnpParams.put("vnp_Command", "pay");
            vnpParams.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode);
            vnpParams.put("vnp_Amount", String.valueOf((long)(totalAmount * 100)));
            vnpParams.put("vnp_CurrCode", "VND");
            vnpParams.put("vnp_TxnRef", txnRef);
            vnpParams.put("vnp_OrderInfo", "Thanh toan hoa don");
            vnpParams.put("vnp_OrderType", "other");
            vnpParams.put("vnp_Locale", "vn");
            vnpParams.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
            vnpParams.put("vnp_IpAddr", request.getRemoteAddr());

            String createDate =
                    new SimpleDateFormat("yyyyMMddHHmmss")
                            .format(new Date());

            vnpParams.put("vnp_CreateDate", createDate);

            // 🔥 GỌI HÀM CHUẨN CỦA VNPAYCONFIG
            String paymentUrl = VNPayConfig.getPaymentUrl(vnpParams);

            response.sendRedirect(paymentUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}