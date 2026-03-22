package controller.Resident;

import dal.*;
import utils.VNPayConfig;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@WebServlet("/vnpay-return")
public class VNPayReturnServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Map<String, String> fields = new HashMap<>();

            for (Enumeration<String> params = request.getParameterNames();
                    params.hasMoreElements();) {

                String fieldName = params.nextElement();
                String fieldValue = request.getParameter(fieldName);

                if (fieldValue != null && fieldValue.length() > 0) {
                    fields.put(fieldName, fieldValue);
                }
            }

            String vnp_SecureHash = fields.remove("vnp_SecureHash");

            List<String> fieldNames
                    = new ArrayList<>(fields.keySet());

            Collections.sort(fieldNames);

            StringBuilder hashData = new StringBuilder();

            for (String field : fieldNames) {

                String value = fields.get(field);

                hashData.append(field);
                hashData.append("=");
                hashData.append(java.net.URLEncoder.encode(value, "US-ASCII"));
                hashData.append("&");
            }
            hashData.deleteCharAt(hashData.length() - 1);

            String checkSum
                    = hmacSHA512(VNPayConfig.vnp_HashSecret,
                            hashData.toString());

            String txnRef = request.getParameter("vnp_TxnRef");
            String responseCode = request.getParameter("vnp_ResponseCode");

            PaymentDAO paymentDAO = new PaymentDAO();
            BillDAO billDAO = new BillDAO();

            if (checkSum.equals(vnp_SecureHash)) {

                if ("00".equals(responseCode)) {

                    paymentDAO.updatePaymentStatus(txnRef, "SUCCESS");
                    billDAO.updateBillsToPaid(txnRef);

                    request.setAttribute("message",
                            "Thanh toán thành công!");

                } else {

                    paymentDAO.updatePaymentStatus(txnRef, "FAILED");

                    request.setAttribute("message",
                            "Thanh toán thất bại!");
                }

            } else {
                request.setAttribute("message",
                        "Bạn đã hủy thanh toán!");
            }

            request.getRequestDispatcher("/Resident/payment-result.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String hmacSHA512(String key, String data)
            throws Exception {

        Mac hmac = Mac.getInstance("HmacSHA512");
        hmac.init(new SecretKeySpec(
                key.getBytes(), "HmacSHA512"));

        byte[] bytes
                = hmac.doFinal(data.getBytes());

        StringBuilder hash = new StringBuilder();

        for (byte b : bytes) {
            hash.append(String.format("%02x", b));
        }

        return hash.toString();
    }
}
