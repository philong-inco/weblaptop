package com.dantn.weblaptop.util;

import com.dantn.weblaptop.dto.response.HinhThucThanhToanResponse;
import com.dantn.weblaptop.dto.response.HoaDonHinhThucThanhToanResponse;
import com.dantn.weblaptop.dto.response.HoaDonResponse;
import com.dantn.weblaptop.dto.response.SerialNumberDaBanResponse;
import com.dantn.weblaptop.entity.hoadon.HoaDon;
import com.dantn.weblaptop.entity.sanpham.SerialNumber;
import com.dantn.weblaptop.exception.AppException;
import com.dantn.weblaptop.service.HinhThucThanhToanService;
import com.dantn.weblaptop.service.HoaDonHinhThucThanhToanSerive;
import com.dantn.weblaptop.service.HoaDonService;
import com.dantn.weblaptop.service.SerialNumberDaBanService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SendEmailBill {
    JavaMailSender javaMailSender;
    SerialNumberDaBanService serialNumberDaBanService;
    HoaDonHinhThucThanhToanSerive hoaDonHinhThucThanhToanSerive;

    @Async
    public void sendEmailDaThanhToan(HoaDon hoaDon, String subject) throws AppException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        List<SerialNumberDaBanResponse> serialNumbers = serialNumberDaBanService.getSerialNumberDaBanPage("BILL1679");

        // Tạo bảng HTML
        StringBuilder tableBuilder = new StringBuilder();
        tableBuilder.append("<table style='width: 100%; border-collapse: collapse;'>");
        tableBuilder.append(
                "<thead>" +
                        "<tr>" +
                        "<th style='border: 1px solid #ccc; padding: 8px;'>STT</th>" +
                        "<th style='border: 1px solid #ccc; padding: 8px;'>Tên sản phẩm</th>" +
                        "<th style='border: 1px solid #ccc; padding: 8px;'>Giá</th>" +
                        "<th style='border: 1px solid #ccc; padding: 8px;'>Số lượng</th>" +
                        "<th style='border: 1px solid #ccc; padding: 8px;'>Thành tiền</th>" +
                        "</tr>" +
                        "</thead>");
        tableBuilder.append("<tbody>");

        for (int i = 0; i < serialNumbers.size(); i++) {
            tableBuilder.append("<tr>");
            tableBuilder.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: center;'>" + (i + 1) + "</td>");
            tableBuilder.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: center;'>" + serialNumbers.get(i).getProductName() + "</td>");
            tableBuilder.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: center;'>" + serialNumbers.get(i).getPrice() + "</td>");
            tableBuilder.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: center;'>" + serialNumbers.get(i).getQuantity() + "</td>");
            tableBuilder.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: center;'>" + (serialNumbers.get(i).getPrice().multiply(new BigDecimal(serialNumbers.get(i).getQuantity()))) + "</td>");
            tableBuilder.append("</tr>");
        }

        tableBuilder.append("</tbody></table>");

        // Tạo nội dung email
        String htmlBody = "<html>"
                + "<head>"
                + "<style>"
                + "body { font-family: Arial, sans-serif; background-color: #007bff; color: #ffffff; padding: 20px; }"
                + ".container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #fff; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); border-radius: 4px; }"
                + "h1 { text-align: center; color: #007bff; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class='container'>"
                + "<h1> COMNOONE </h1>"
                + "<h2>Dữ liệu số:</h2>"
                + tableBuilder.toString()
                + "</div>"
                + "</body>"
                + "</html>";

        try {
            helper.setFrom("vutu8288@gmail.com");
            helper.setTo("Vyen597@gmail.com");
            helper.setSubject(subject);
//            helper.setTo("sđgfshfsh");

            helper.setText(htmlBody, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendEmailStatus(HoaDon hoaDon, String subject) throws AppException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        List<SerialNumberDaBanResponse> serialNumbers = serialNumberDaBanService.getSerialNumberDaBanPage(hoaDon.getMa());

        // Tạo bảng HTML
        StringBuilder tableBuilder = new StringBuilder();
        tableBuilder.append("<table style='width: 100%; border-collapse: collapse;'>");
        tableBuilder.append(
                "<thead>" +
                        "<tr>" +
                        "<th style='border: 1px solid #ccc; padding: 8px;'>STT</th>" +
                        "<th style='border: 1px solid #ccc; padding: 8px;'>Tên sản phẩm</th>" +
                        "<th style='border: 1px solid #ccc; padding: 8px;'>Giá</th>" +
                        "<th style='border: 1px solid #ccc; padding: 8px;'>Số lượng</th>" +
                        "<th style='border: 1px solid #ccc; padding: 8px;'>Thành tiền</th>" +
                        "</tr>" +
                        "</thead>");
        tableBuilder.append("<tbody>");

        for (int i = 0; i < serialNumbers.size(); i++) {
            tableBuilder.append("<tr>");
            tableBuilder.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: center;'>" + (i + 1) + "</td>");
            tableBuilder.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: center;'>" + serialNumbers.get(i).getProductName() + "</td>");
            tableBuilder.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: center;'>" + serialNumbers.get(i).getPrice() + "</td>");
            tableBuilder.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: center;'>" + serialNumbers.get(i).getQuantity() + "</td>");
            tableBuilder.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: center;'>" + (serialNumbers.get(i).getPrice().multiply(new BigDecimal(serialNumbers.get(i).getQuantity()))) + "</td>");
            tableBuilder.append("</tr>");
        }

        tableBuilder.append("</tbody></table>");

        // Tạo nội dung email
        String htmlBody = "<html>"
                + "<head>"
                + "<style>"
                + "body { font-family: Arial, sans-serif; background-color: #007bff; color: #ffffff; padding: 20px; }"
                + ".container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #fff; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); border-radius: 4px; }"
                + "h1 { text-align: center; color: #007bff; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class='container'>"
                + "<h1> COMNOONE </h1>"
                + "<h2>Dữ liệu số:</h2>"
                + tableBuilder.toString()
                + "</div>"
                + "</body>"
                + "</html>";

        try {
            helper.setFrom("vutu8288@gmail.com");
            helper.setTo(hoaDon.getEmail());
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendEmailXacNhan(HoaDonResponse response, String subject) throws AppException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        List<SerialNumberDaBanResponse> serialNumbers = serialNumberDaBanService.getSerialNumberDaBanPage(response.getMa());
        List<HoaDonHinhThucThanhToanResponse> hinhThucThanhToanResponses = hoaDonHinhThucThanhToanSerive.getAllByBillCode(response.getMa());
        String headerHtml = "<div style='text-align: center;'>" +
                "<h1 style='color: #800080;'>COMNOONE</h1>" +
                "<p>Chương trình Phổ thông Cao đẳng FPT Polytechnic, Phương Canh, Từ Liêm, Hà Nội, Việt Nam</p>" +
                "<p>SDT: 0338957590</p>" +
                "<p>Mã hóa đơn: " + response.getMa() + " | " + response.getNgayTao() + "</p>" +
                "<img src='[QR_CODE_SRC]' alt='QR Code' style='width: 50px; height: 50px;' />" +
                "<hr style='border: 1px dashed #000; margin: 20px 0;' />" +
                "<p>Khách hàng: " + ((response.getIdKhachHang() == null && response.getTenKhachHang() == null) ? "Khách lẻ" : response.getTenKhachHang()) + "</p>" +
                "<p>Số điện thoại: " + response.getSdt() + "</p>" +
                "<p>Địa chỉ: " + response.getDiaChi() + "</p>" +
                "<hr style='border: 1px dashed #000; margin: 20px 0;' />" +
                "<p><strong>Nội dung đơn hàng ( Tổng số lượng sản phẩm " + response.getTongSanPham() + " )</strong></p>" +
                "</div>";
        StringBuilder tableBuilder = new StringBuilder();
        tableBuilder.append("<table style='width: 100%; border-collapse: collapse;'>");
        tableBuilder.append(
                "<thead>" +
                        "<tr>" +
                        "<th style='border: 1px solid #ccc; padding: 8px;'>STT</th>" +
                        "<th style='border: 1px solid #ccc; padding: 8px;'>Tên sản phẩm</th>" +
                        "<th style='border: 1px solid #ccc; padding: 8px;'>Giá</th>" +
                        "<th style='border: 1px solid #ccc; padding: 8px;'>Số lượng</th>" +
                        "<th style='border: 1px solid #ccc; padding: 8px;'>Thành tiền</th>" +
                        "</tr>" +
                        "</thead>");
        tableBuilder.append("<tbody>");

        for (int i = 0; i < serialNumbers.size(); i++) {
            tableBuilder.append("<tr>");
            tableBuilder.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: center;'>" + (i + 1) + "</td>");
            tableBuilder.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: center;'>" + serialNumbers.get(i).getProductName() + "</td>");
            tableBuilder.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: center;'>" + formatCurrencyVND(serialNumbers.get(i).getPrice()) + "</td>");
            tableBuilder.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: center;'>" + serialNumbers.get(i).getQuantity() + "</td>");
            tableBuilder.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: center;'>" + formatCurrencyVND(serialNumbers.get(i).getPrice().multiply(new BigDecimal(serialNumbers.get(i).getQuantity()))) + "</td>");
            tableBuilder.append("</tr>");
        }

        tableBuilder.append("</tbody>");

        // Thêm thông tin tổng tiền và giảm giá vào sau bảng
        tableBuilder.append("<tfoot>");
        tableBuilder.append("<tr>");
        tableBuilder.append("<td colspan='4' style='border: 1px solid #ccc; padding: 8px; text-align: right;'>Tổng tiền hàng:</td>");
        tableBuilder.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: right;'>" + formatCurrencyVND(response.getTongTienBanDau()) + " </td>");
        tableBuilder.append("</tr>");

        tableBuilder.append("<tr>");
        tableBuilder.append("<td colspan='4' style='border: 1px solid #ccc; padding: 8px; text-align: right;'>Phiếu giảm giá:</td>");
        tableBuilder.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: right;'>" + response.getMaPGG() != null ? response.getMaPGG() : "" + " </td>");
        tableBuilder.append("</tr>");

        tableBuilder.append("<tr>");
        tableBuilder.append("<td colspan='4' style='border: 1px solid #ccc; padding: 8px; text-align: right;'>Giảm giá:</td>");
        tableBuilder.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: right;'> " + (response.getGiaTriPhieuGiamGia() != null ? formatCurrencyVND(response.getGiaTriPhieuGiamGia()) : formatCurrencyVND(BigDecimal.ZERO)) + " </td>");
        tableBuilder.append("</tr>");

        tableBuilder.append("<tr>");
        tableBuilder.append("<td colspan='4' style='border: 1px solid #ccc; padding: 8px; text-align: right;'>Giảm hạng:</td>");
        tableBuilder.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: right;'> " + (response.getTienGiamHangKhachHang() != null ? formatCurrencyVND(response.getTienGiamHangKhachHang()) : formatCurrencyVND(BigDecimal.ZERO)) + "</td>");
        tableBuilder.append("</tr>");

        tableBuilder.append("<tr>");
        tableBuilder.append("<td colspan='4' style='border: 1px solid #ccc; padding: 8px; text-align: right;'>Tiền ship:</td>");
        tableBuilder.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: right;'> " + (response.getTienShip() != null ? formatCurrencyVND(response.getTienShip()) : formatCurrencyVND(BigDecimal.ZERO)) + "</td>");
        tableBuilder.append("</tr>");

        tableBuilder.append("<tr>");
        tableBuilder.append("<td colspan='4' style='border: 1px solid #ccc; padding: 8px; text-align: right; color: red;'>Tổng tiền :</td>");
        tableBuilder.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: right; color: red;'>" + formatCurrencyVND(response.getTongTienPhaiTra()) + "</td>");
        tableBuilder.append("</tr>");
        tableBuilder.append("<tr>");
        tableBuilder.append("<td colspan='4' style='border: 1px solid #ccc; padding: 8px; text-align: right; color: red;'>Trang thái :</td>");
        tableBuilder.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: right; color: red;'>" + response.getTrangThai().getName() + "</td>");
        tableBuilder.append("</tr>");
        tableBuilder.append("</tfoot>");
        tableBuilder.append("</table>");


        StringBuilder tableBuilderHistory = new StringBuilder();
        tableBuilderHistory.append("<table style='border-collapse: collapse; width: 100%; margin-top: 20px;'>");
        tableBuilderHistory.append("<thead>");
        tableBuilderHistory.append("<tr>");
//        tableBuilderHistory.append("<th style='border: 1px solid #ccc; padding: 8px; text-align: left;'>Số tiền</th>");
        tableBuilderHistory.append("<th style='border: 1px solid #ccc; padding: 8px; text-align: left;'>Tiền nhận</th>");
        tableBuilderHistory.append("<th style='border: 1px solid #ccc; padding: 8px; text-align: left;'>Loại thanh toán</th>");
        tableBuilderHistory.append("<th style='border: 1px solid #ccc; padding: 8px; text-align: left;'>Ngày tạo</th>");
        tableBuilderHistory.append("<th style='border: 1px solid #ccc; padding: 8px; text-align: left;'>Ngày sửa</th>");
        tableBuilderHistory.append("<th style='border: 1px solid #ccc; padding: 8px; text-align: left;'>Người tạo</th>");
        tableBuilderHistory.append("</tr>");
        tableBuilderHistory.append("</thead>");
        tableBuilderHistory.append("<tbody>");

        for (HoaDonHinhThucThanhToanResponse payment : hinhThucThanhToanResponses) {
            BigDecimal amount;
            if (payment.getLoaiThanhToan() == 0) {
                amount = payment.getTienNhan() != null ? payment.getTienNhan() : BigDecimal.ZERO;
            } else {
                amount = payment.getSoTien() != null ? payment.getSoTien() : BigDecimal.ZERO;
            }
            tableBuilderHistory.append("<tr>");
//            tableBuilderHistory.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: right;'>" + formatCurrencyVND(payment.getSoTien() != null ? payment.getSoTien() : BigDecimal.ZERO) + "</td>");
            tableBuilderHistory.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: right;'>")
                    .append(formatCurrencyVND(amount))
                    .append("</td>");
            tableBuilderHistory.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: center;'>" + (payment.getLoaiThanhToan() == 0 ? "Đã thanh toán" : "Trả sau") + "</td>");
            tableBuilderHistory.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: center;'>" + payment.getNgayTao() + "</td>");
            tableBuilderHistory.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: center;'>" + payment.getNgaySua() + "</td>");
            tableBuilderHistory.append("<td style='border: 1px solid #ccc; padding: 8px; text-align: center;'>" + payment.getNguoiTao() + "</td>");
            tableBuilderHistory.append("</tr>");
        }

        tableBuilder.append("</tbody>");
        tableBuilder.append("</table>");

        // Tạo nội dung email
        String htmlBody = "<html>"
                + "<head>"
                + "<style>"
                + "body { font-family: Arial, sans-serif; background-color: #007bff; color: #ffffff; padding: 20px; }"
                + ".container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #fff; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); border-radius: 4px; }"
                + "h1 { text-align: center; color: #007bff; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class='container'>"
                + headerHtml
                + tableBuilder.toString()
                + "<h2>Lịch sử thanh toán</h2>"  // Thêm tiêu đề nếu cần
                + tableBuilderHistory.toString()
                + "</div>"
                + "</body>"
                + "</html>";

        try {
            helper.setFrom("vutu8288@gmail.com");
            helper.setTo("nguyenmanh19924@gmail.com");
            helper.setSubject(subject + response.getTrangThai().getName());
            helper.setText(htmlBody, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public String formatCurrencyVND(BigDecimal amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');

        DecimalFormat formatter = new DecimalFormat("#,###.###", symbols);
        return formatter.format(amount) + " VND";
    }
}
