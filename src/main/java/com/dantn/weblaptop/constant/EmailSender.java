package com.dantn.weblaptop.constant;

import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.entity.nhanvien.VaiTro;
import com.dantn.weblaptop.entity.phieugiamgia.PhieuGiamGia;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class EmailSender {
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    // Hàm gửi email chào mừng khách hàng mới
    public void newCustomerSendEmail(KhachHang khachHang) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(khachHang.getEmail());
        helper.setSubject("Chào mừng bạn đến với Cửa hàng Laptop ComNoOne!");

        String emailContent = String.format(
                "<html>" +
                        "<head>" +
                        "<style>" +
                        "body { font-family: Arial, sans-serif; line-height: 1.6; font-size: 14px; color: #333333; }" +
                        ".header { background-color: #4CAF50; padding: 15px; text-align: center; color: white; }" +
                        ".header img { max-width: 150px; }" +
                        ".content { padding: 20px; background-color: #ffffff; }" +
                        ".content h2 { color: #333333; font-size: 18px; }" +
                        ".content p { margin: 10px 0; }" +
                        ".footer { background-color: #f1f1f1; padding: 10px; text-align: center; font-size: 12px; color: #888888; }" +
                        ".button { display: inline-block; padding: 10px 20px; margin-top: 20px; color: #ffffff; background-color: #007bff; text-decoration: none; border-radius: 5px; font-size: 14px; }" +
                        "</style>" +
                        "</head>" +
                        "<body>" +
                        "<div class='header'>" +
                        "<h1>Chào mừng bạn!</h1>" +
                        "</div>" +
                        "<div class='content'>" +
                        "<h2>Xin chào %s,</h2>" +
                        "<p>Chúng tôi rất vui mừng chào đón bạn trở thành khách hàng của Cửa hàng Laptop ComNoOne! Bạn sẽ được trải nghiệm các dịch vụ và sản phẩm chất lượng cao.</p>" +
                        "<a href='#' class='button'>Khám phá ngay</a>" +
                        "</div>" +
                        "<div class='footer'>" +
                        "<p>Trân trọng,</p>" +
                        "<p>Đội ngũ Cửa hàng Laptop ComNoOne</p>" +
                        "<p>&copy; 2024 Laptop ComNoOne. All rights reserved.</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>",
                khachHang.getTen()
        );

        helper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);
    }

    // Hàm gửi email chào mừng nhân viên mới
    public void newEmployeeSendEmail(NhanVien nhanVien, String genPassword, Set<VaiTro> vaiTros) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(nhanVien.getEmail());
        helper.setSubject("Chào mừng bạn trở thành nhân viên Cửa hàng Laptop ComNoOne!");

        String vaiTrosStr = vaiTros.stream()
                .map(VaiTro::getTen)
                .reduce((role1, role2) -> role1 + ", " + role2)
                .orElse("");

        String emailContent = String.format(
                "<html>" +
                        "<head>" +
                        "<style>" +
                        "body { font-family: Arial, sans-serif; line-height: 1.6; color: #333333; }" +
                        ".header { background-color: #800080; padding: 10px; text-align: center; color: white; }" +
                        ".content { padding: 20px; background-color: #ffffff; }" +
                        ".content h2 { color: #333333; }" +
                        ".footer { background-color: #800080; padding: 10px; text-align: center; color: white; }" +
                        ".button { display: inline-block; padding: 10px 20px; margin-top: 20px; color: #ffffff; background-color: #007bff; text-decoration: none; border-radius: 5px; }" +
                        "</style>" +
                        "</head>" +
                        "<body>" +
                        "<div class='header'>" +
                        "<h1>Chào mừng bạn gia nhập đội ngũ!</h1>" +
                        "</div>" +
                        "<div class='content'>" +
                        "<h2>Xin chào %s,</h2>" +
                        "<p>Chúng tôi rất vui mừng chào đón bạn trở thành nhân viên của Cửa hàng Laptop ComNoOne!</p>" +
                        "<p><strong>Tài khoản:</strong> %s</p>" +
                        "<p><strong>Mật khẩu:</strong> %s</p>" +
                        "<p><strong>Chức vụ:</strong> %s</p>" +
                        "<a href='#' class='button'>Đăng nhập ngay</a>" +
                        "</div>" +
                        "<div class='footer'>" +
                        "<p>&copy; 2024 Laptop ComNoOne. All rights reserved.</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>",
                nhanVien.getTen(),
                nhanVien.getEmail(),
                genPassword,
                vaiTrosStr
        );

        helper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);
    }

    // Hàm gửi email khi quên mật khẩu cho nhân viên
    public void sendForgotPasswordEmail(NhanVien nhanVien, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(nhanVien.getEmail());
        message.setSubject("Yêu cầu thay đổi mật khẩu từ ComNoOne");

        String emailContent = String.format(
                "Xin chào %s,\n\n" +
                        "Mật khẩu mới của bạn là: %s\n\n" +
                        "Vui lòng đăng nhập và thay đổi mật khẩu ngay sau khi đăng nhập.\n\n" +
                        "Trân trọng,\n" +
                        "Cửa hàng Laptop ComNoOne",
                nhanVien.getTen(),
                newPassword
        );

        message.setText(emailContent);
        javaMailSender.send(message);
    }

    // Hàm gửi email phiếu giảm giá cho nhân viên
    public void sendEmailCoupons( KhachHang khachHang, PhieuGiamGia phieuGiamGia) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(khachHang.getEmail());
        message.setSubject("Gửi mã khuyến mãi từ Laptop ComNoOne cho [" + khachHang.getTen() + "]");

        String ma = phieuGiamGia.getMa();
        String soLuong = phieuGiamGia.getSoLuong() != null ? String.valueOf(phieuGiamGia.getSoLuong()) : "Không giới hạn";
        String giaTriGiamGia = phieuGiamGia.getGiaTriGiamGia() != null ?
                (phieuGiamGia.getLoaiGiamGia() == 1 ? phieuGiamGia.getGiaTriGiamGia() + " %" : phieuGiamGia.getGiaTriGiamGia() + " VND") :
                "Không giới hạn";
        String giaTriDonToiThieu = phieuGiamGia.getGiaTriDonToiThieu() != null ? phieuGiamGia.getGiaTriDonToiThieu().toString() : "Không giới hạn";
        String giamToiGia = phieuGiamGia.getGiamToiDa() != null ? phieuGiamGia.getGiamToiDa().toString() : "Không giới hạn";
        String ngayBatDau = phieuGiamGia.getNgayBatDau() != null ? phieuGiamGia.getNgayBatDau().toString() : "Không giới hạn";
        String ngayHetHan = phieuGiamGia.getNgayHetHan() != null ? phieuGiamGia.getNgayHetHan().toString() : "Không giới hạn";

        String emailContent = String.format(
                "Xin chào %s,\n\n" +
                        "Chúng tôi xin gửi đến bạn mã khuyến mãi cho khách hàng.\n\n" +
                        "Chi tiết mã giảm giá:\n" +
                        "- Mã giảm giá: %s\n" +
                        "- Số lượng: %s\n" +
                        "- Giá trị giảm giá: %s\n" +
                        "- Giá trị đơn tối thiểu: %s\n" +
                        "- Giảm tối đa: %s\n" +
                        "- Ngày bắt đầu: %s\n" +
                        "- Ngày hết hạn: %s\n\n" +
                        "Trân trọng,\n" +
                        "Cửa hàng Laptop ComNoOne",
                khachHang.getTen(),
                ma,
                soLuong,
                giaTriGiamGia,
                giaTriDonToiThieu,
                giamToiGia,
                ngayBatDau,
                ngayHetHan
        );

        message.setText(emailContent);
        javaMailSender.send(message);
    }
}
