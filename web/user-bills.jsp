<%-- 
    Document   : user-bills
    Created on : Mar 3, 2026, 5:03:01 PM
    Author     : ADMIN
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*,model.Bills,model.Payments" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Thanh toán</title>
        <link rel="stylesheet" href="assets/user_bill.css">
        <link rel="stylesheet" href="assets/home.css">
    </head>

    <body>
        <div class="top-bar">
            Cổng thông tin Cư dân
        </div>
        <div class="container">

            <!-- SIDEBAR -->
            <div class="sidebar">
                <div class="user-profile">
                    <div class="avatar-circle">
                        <svg width="60" height="60" fill="currentColor" viewBox="0 0 16 16">
                        <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                        <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                        </svg>
                    </div>
                    <h4>Xin chào, ${sessionScope.USER_INFO.fullName}</h4>
                </div>

                <a href="Home_Residents" class="menu-item ">Trang chủ</a>
                <a href="Room_info" class="menu-item">Thông tin căn hộ</a>
                <a href="Services" class="menu-item">Dịch vụ</a>
                <a href="user-bills" class="menu-item active">Thanh toán</a>
                <a href="Requirements" class="menu-item">Ý kiến</a>
                <a href="Logout" class="menu-item" style="margin-top: 20px;">Đăng xuất</a>
            </div>

            <!-- MAIN CONTENT -->
            <div class="main-content">

                <!-- HEADER GRADIENT -->
                <div class="header-banner">
                    Thanh toán dịch vụ
                </div>

                <div class="content">

                    <h2>Hóa đơn chưa thanh toán</h2>

                    <form action="create-payment" method="post">

                        <table class="table-style">
                            <tr>
                                <th>Chọn</th>
                                <th>Mã hóa đơn</th>
                                <th>Tháng</th>
                                <th>Năm</th>
                                <th>Số tiền</th>
                            </tr>

                            <%
                                List<Bills> bills =
                                        (List<Bills>) request.getAttribute("bills");

                                if (bills != null) {
                                    for (Bills b : bills) {
                            %>

                            <tr>
                                <td>
                                    <input type="checkbox"
                                           name="billIds"
                                           value="<%=b.getBillId()%>">
                                </td>
                                <td><%=b.getBillId()%></td>
                                <td><%=b.getBillingMonth()%></td>
                                <td><%=b.getBillingYear()%></td>
                                <td><%=b.getTotalAmount()%> VNĐ</td>
                            </tr>

                            <%
                                    }
                                }
                            %>

                        </table>
                        <%
                            String errBill = (String) request.getAttribute("errBill");
                            if (errBill != null && !errBill.isEmpty()) {
                        %>
                        <div style="color: red; margin-bottom: 10px;">
                            <%= errBill %>
                        </div>
                        <%
                            }
                        %>
                        <button type="submit" class="btn-pay">
                            Thanh toán VNPay
                        </button>

                    </form>

                    <!-- LỊCH SỬ -->
                    <!-- LỊCH SỬ -->
                    <h2 style="margin-top:40px;">Lịch sử thanh toán</h2>

                    <!-- FILTER THÁNG NĂM -->
                    <%
                    String selectedMonth = request.getParameter("month");
                    String selectedYear = request.getParameter("year");
                    %>
                    <form action="user-bills" method="get" style="margin-bottom:15px;">

                        Tháng:
                        <select name="month">
                            <option value="">Tất cả</option>

                            <% for(int i=1;i<=12;i++){ %>

                            <option value="<%=i%>"
                                    <%= (selectedMonth != null && selectedMonth.equals(String.valueOf(i))) ? "selected" : "" %>>
                                <%=i%>
                            </option>

                            <% } %>

                        </select>

                        Năm:
                        <select name="year">

                            <option value="">Tất cả</option>

                            <%
                            int currentYear = java.time.Year.now().getValue();

                            for(int i=currentYear-3;i<=currentYear+1;i++){
                            %>

                            <option value="<%=i%>"
                                    <%= (selectedYear != null && selectedYear.equals(String.valueOf(i))) ? "selected" : "" %>>
                                <%=i%>
                            </option>

                            <% } %>

                        </select>
                        <button type="submit" class="btn-pay">
                            Xem
                        </button>

                    </form>


                    <table class="table-style">

                        <tr>
                            <th>Mã giao dịch</th>
                            <th>Ngày</th>
                            <th>Số tiền</th>
                            <th>Trạng thái</th>
                        </tr>

                        <%
                        List<Payments> payments =
                                (List<Payments>) request.getAttribute("payments");

                        if (payments != null && !payments.isEmpty()) {

                            for (Payments p : payments) {
                        %>

                        <tr>
                            <td><%=p.getTransactionCode()%></td>
                            <td><%=p.getPaymentDate()%></td>
                            <td><%=p.getAmount()%> VNĐ</td>
                            <td style="color:green;"><%=p.getStatus()%></td>
                        </tr>

                        <%
                            }

                        } else {
                        %>

                        <tr>
                            <td colspan="4" style="text-align:center;">
                                Không có giao dịch trong thời gian này
                            </td>
                        </tr>

                        <%
                        }
                        %>

                    </table>

                </div>

            </div>
        </div>

    </body>
</html>
