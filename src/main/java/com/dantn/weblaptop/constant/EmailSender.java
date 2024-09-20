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

    public void newCustomerSendEmail(KhachHang khachHang) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(khachHang.getEmail());
        helper.setSubject("Chào mừng bạn đến với Cửa hàng Laptop ComNoOne!");

        String emailContent = String.format(
                "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; margin: 0; padding: 0; }" +
                ".header { background: linear-gradient(135deg, #ff5722, #ff9800); padding: 20px; text-align: center; color: #fff; }" +
                ".header img { max-width: 200px; }" +
                ".content { padding: 20px; background-color: #f4f4f4; }" +
                ".content h2 { color: #333; font-size: 22px; }" +
                ".content p { margin: 15px 0; font-size: 16px; }" +
                ".footer { background-color: #e2e2e2; padding: 15px; text-align: center; font-size: 14px; color: #777; }" +
                ".button { display: inline-block; padding: 12px 25px; margin-top: 20px; color: #fff; background-color: #ff5722; text-decoration: none; border-radius: 5px; font-size: 16px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='header'>" +
                "<img src='https://res.cloudinary.com/daljc2ktr/image/upload/v1723625914/af7ad9ee-03a4-4080-b809-572700a19da6.png' alt='Cửa hàng Laptop ComNoOne' />" +
                "<h1>Chào mừng bạn đến với Cửa hàng Laptop ComNoOne!</h1>" +
                "</div>" +
                "<div class='content'>" +
                "<h2>Xin chào %s,</h2>" +
                "<p>Chúng tôi rất vui mừng thông báo rằng bạn đã trở thành một phần của gia đình Cửa hàng Laptop ComNoOne. Với sự gia nhập của bạn, chúng tôi hy vọng sẽ cung cấp cho bạn trải nghiệm mua sắm tuyệt vời nhất và dịch vụ khách hàng tận tâm.</p>" +
                "<p>Hãy khám phá các sản phẩm và dịch vụ của chúng tôi ngay hôm nay và tận hưởng những ưu đãi đặc biệt dành riêng cho bạn!</p>" +
                "<a href='#' class='button'>Khám Phá Ngay</a>" +
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



    public void newEmployeeSendEmail(NhanVien nhanVien, String genPassword, Set<VaiTro> vaiTros) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(nhanVien.getEmail());
        helper.setSubject("Chào mừng bạn gia nhập đội ngũ Cửa hàng Laptop ComNoOne!");

        String vaiTrosStr = vaiTros.stream()
                .map(VaiTro::getTen)
                .reduce((role1, role2) -> role1 + ", " + role2)
                .orElse("");

        String emailContent = String.format(
                "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; margin: 0; padding: 0; }" +
                ".header { background: linear-gradient(135deg, #ff5722, #ff9800); padding: 20px; text-align: center; color: #fff; }" +
                ".header img { max-width: 200px; }" +
                ".content { padding: 20px; background-color: #f9f9f9; }" +
                ".content h2 { color: #333; font-size: 22px; }" +
                ".content p { margin: 15px 0; font-size: 16px; }" +
                ".footer { background-color: #800080; padding: 15px; text-align: center; color: #fff; }" +
                ".button { display: inline-block; padding: 12px 25px; margin-top: 20px; color: #fff; background-color: #ff5722; text-decoration: none; border-radius: 5px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='header'>" +
                "<img src='https://res.cloudinary.com/daljc2ktr/image/upload/v1723625914/af7ad9ee-03a4-4080-b809-572700a19da6.png' alt='Cửa hàng Laptop ComNoOne' />" +
                "<h1>Chào mừng bạn gia nhập đội ngũ!</h1>" +
                "</div>" +
                "<div class='content'>" +
                "<h2>Xin chào %s,</h2>" +
                "<p>Chúng tôi rất vui mừng chào đón bạn vào gia đình Cửa hàng Laptop ComNoOne. Chúng tôi tin rằng bạn sẽ là một phần quan trọng trong đội ngũ của chúng tôi và đóng góp vào sự phát triển chung.</p>" +
                "<p>Dưới đây là thông tin tài khoản của bạn:</p>" +
                "<p><strong>Tài khoản:</strong> %s</p>" +
                "<p><strong>Mật khẩu:</strong> %s</p>" +
                "<p><strong>Chức vụ:</strong> %s</p>" +
                "<a href='#' class='button'>Đăng Nhập Ngay</a>" +
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

    public void sendEmailCoupons(KhachHang khachHang, PhieuGiamGia phieuGiamGia) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(khachHang.getEmail());
        helper.setSubject("Nhận mã khuyến mãi đặc biệt từ Laptop ComNoOne!");

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
                "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; margin: 0; padding: 0; }" +
                ".header { background: linear-gradient(135deg, #ff5722, #ff9800); padding: 20px; text-align: center; color: #fff; }" +
                ".header img { max-width: 200px; }" +
                ".content { padding: 20px; background-color: #f4f4f4; }" +
                ".content h2 { color: #333; font-size: 22px; }" +
                ".content p { margin: 15px 0; font-size: 16px; }" +
                ".footer { background-color: #e2e2e2; padding: 15px; text-align: center; font-size: 14px; color: #777; }" +
                ".button { display: inline-block; padding: 12px 25px; margin-top: 20px; color: #fff; background-color: #4a90e2; text-decoration: none; border-radius: 5px; font-size: 16px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='header'>" +
                "<img src='https://res.cloudinary.com/daljc2ktr/image/upload/v1723625914/af7ad9ee-03a4-4080-b809-572700a19da6.png' alt='Cửa hàng Laptop ComNoOne' />" +
                "<h1>Nhận mã khuyến mãi từ Laptop ComNoOne!</h1>" +
                "</div>" +
                "<div class='content'>" +
                "<h2>Xin chào %s,</h2>" +
                "<p>Chúng tôi rất vui mừng thông báo rằng bạn đã nhận được mã khuyến mãi đặc biệt từ chúng tôi. Đây là cơ hội tuyệt vời để bạn tiết kiệm nhiều hơn khi mua sắm tại Cửa hàng Laptop ComNoOne.</p>" +
                "<p>Dưới đây là chi tiết về mã giảm giá của bạn:</p>" +
                "<p><strong>Mã giảm giá:</strong> %s</p>" +
                "<p><strong>Số lượng:</strong> %s</p>" +
                "<p><strong>Giá trị giảm giá:</strong> %s</p>" +
                "<p><strong>Giá trị đơn tối thiểu:</strong> %s</p>" +
                "<p><strong>Giảm tối đa:</strong> %s</p>" +
                "<p><strong>Ngày bắt đầu:</strong> %s</p>" +
                "<a href='#' class='button'>Sử Dụng Ngay</a>" +
                "</div>" +
                "<div class='footer'>" +
                "<p>Trân trọng,</p>" +
                "<p>Đội ngũ Cửa hàng Laptop ComNoOne</p>" +
                "<p>&copy; 2024 Laptop ComNoOne. All rights reserved.</p>" +
                "</div>" +
                "</body>" +
                "</html>",
                khachHang.getTen(),
                ma,
                soLuong,
                giaTriGiamGia,
                giaTriDonToiThieu,
                giamToiGia,
                ngayBatDau
        );

        helper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);
    }

}
