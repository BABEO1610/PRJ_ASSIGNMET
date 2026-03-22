<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>

    <head>

        <meta charset="UTF-8">
        <title>Chi tiết công việc</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/home.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/task-detail.css">

    </head>

    <body>

        <div class="task-detail-page">

            <div class="task-card">

                <h2>Chi tiết công việc</h2>

                <div class="task-info">
                    <b>Cư dân:</b> ${task.residentName}
                </div>

                <div class="task-info">
                    <b>Căn hộ:</b> ${task.apartmentNumber}
                </div>

                <div class="task-info">
                    <b>Tiêu đề:</b> ${task.title}
                </div>

                <div class="task-info">
                    <b>Mô tả:</b>
                    <span class="description">${task.description}</span>
                </div>

                <div class="task-info">
                    <b>Trạng thái:</b> ${task.status}
                </div>


                <div class="task-actions">

                    <!-- LEFT BUTTON : CANCEL -->
                    <div>

                        <c:if test="${task.status != 'Completed' && task.status != 'Cancelled'}">

                            <form action="TaskDetail" method="post">

                                <input type="hidden" name="requestId" value="${task.requestId}">
                                <input type="hidden" name="action" value="cancel">

                                <button class="btn btn-cancel">
                                    Hủy
                                </button>

                            </form>

                        </c:if>

                    </div>


                    <!-- RIGHT BUTTONS -->
                    <div class="action-right">

                        <!-- START -->
                        <c:if test="${task.status == 'Approved'}">

                            <form action="TaskDetail" method="post">

                                <input type="hidden" name="requestId" value="${task.requestId}">
                                <input type="hidden" name="action" value="start">

                                <button class="btn btn-start">
                                    Nhận công việc
                                </button>

                            </form>

                        </c:if>


                        <!-- COMPLETE -->
                        <c:if test="${task.status == 'Processing'}">

                            <form action="TaskDetail" method="post">

                                <input type="hidden" name="requestId" value="${task.requestId}">
                                <input type="hidden" name="action" value="complete">

                                <button class="btn btn-complete">
                                    Hoàn thành
                                </button>

                            </form>

                        </c:if>

                    </div>

                </div>


                <div class="back-area">

                    <a href="StaffTasks">
                        <button class="btn btn-back">
                            Quay lại
                        </button>
                    </a>

                </div>

            </div>

        </div>

    </body>
</html>