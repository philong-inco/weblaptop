package com.dantn.weblaptop.constant;

import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.entity.nhanvien.NhanVien;
import com.dantn.weblaptop.entity.nhanvien.VaiTro;
import com.dantn.weblaptop.repository.VaiTro_Repository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

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

    public void newEmployeeSendEmail(KhachHang khachHang) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setTo(khachHang.getEmail());
        helper.setSubject("Chào mừng bạn trở thành khách hàng Cửa hàng bán Laptop ComNoOne.");
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
                        "<p>Chúng tôi rất vui mừng chào đón bạn trở thành khách hàng của Cửa hàng Laptop ComNoOne! Dưới đây là thông tin tài khoản của bạn:</p>" +
                        "<a href='#' class='button'>Trang Chủ</a>" +
                        "</div>" +
                        "<div class='footer'>" +
                        "<p>Trân trọng,</p>" +
                        "<p>Đội ngũ Cửa hàng Laptop ComNoOne</p>" +
                        "<p>&copy; 2024 Laptop ComNoOne. All rights reserved.</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>",
                khachHang.getTen(),
                khachHang.getEmail()
        );
        helper.setText(emailContent, true); // true indicates the email content is HTML

        javaMailSender.send(mimeMessage);
    }

    public void signupNhanVienSendEmail(NhanVien nhanVien, String genPassWord, Set<VaiTro> vaiTros) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(nhanVien.getEmail());
        helper.setSubject("Chào mừng bạn trở thành nhân viên Cửa hàng bán Laptop ComNoOne.");

        String vaiTrosStr = vaiTros.stream()
                .map(VaiTro::getTen)
                .reduce((role1, role2) -> role1 + ", " + role2)
                .orElse("");

        String emailContent = String.format(
                "<html>" +
                        "<head>" +
                        "<style>" +
                        "body { font-family: Arial, sans-serif; line-height: 1.6; }" +
                        ".header { background-color: #800080; padding: 10px; text-align: center; }" +
                        ".header img { max-width: 150px; }" +
                        ".content { padding: 20px; background-color: #ffffff; }" +
                        ".content h2 { color: #333333; }" +
                        ".footer { background-color: #800080; padding: 10px; text-align: center; font-size: 18px; color: #888888; }" +
                        ".button { display: inline-block; padding: 10px 20px; margin-top: 20px; color: #ffffff; background-color: #007bff; text-decoration: none; border-radius: 5px; }" +
                        "</style>" +
                        "</head>" +
                        "<body>" +
                        "<div class='header'>" +
                        "<img src='https://res.cloudinary.com/daljc2ktr/image/upload/v1723625914/af7ad9ee-03a4-4080-b809-572700a19da6.png' alt='Laptop ComNoOne Logo'>" +
                        "</div>" +
                        "<div class='content'>" +
                        "<h2>Xin chào %s,</h2>" +
                        "<p>Chúng tôi rất vui mừng chào đón bạn gia nhập đội ngũ của Cửa hàng Laptop ComNoOne! Dưới đây là thông tin đăng nhập cần thiết để bạn có thể truy cập vào hệ thống của chúng tôi:</p>" +
                        "<p><strong>Tài khoản (email):</strong> %s</p>" +
                        "<p><strong>Mật khẩu:</strong> %s</p>" +
                        "<p><strong>Chức vụ:</strong> %s</p>" +
                        "<p>Vui lòng lưu ý rằng đây là mật khẩu tạm thời. Chúng tôi khuyến nghị bạn nên thay đổi mật khẩu ngay sau khi đăng nhập lần đầu. Hãy đảm bảo rằng bạn giữ thông tin đăng nhập này một cách cẩn thận để tránh rủi ro về việc lạm dụng tài khoản.</p>" +
                        "<p>Nếu bạn gặp bất kỳ vấn đề gì trong quá trình đăng nhập hoặc sử dụng hệ thống, vui lòng liên hệ với bộ phận hỗ trợ của chúng tôi qua địa chỉ <a href='mailto:support@comnoone.com'>support@comnoone.com</a> hoặc số điện thoại <strong>[số điện thoại hỗ trợ]</strong>. Chúng tôi luôn sẵn sàng hỗ trợ bạn.</p>" +
                        "<p>Chúc bạn một trải nghiệm mua sắm thật thoải mái và thú vị tại Laptop ComNoOne. Chúng tôi hy vọng bạn sẽ đóng góp mạnh mẽ vào sự phát triển của đội ngũ và sự thành công của cửa hàng.</p>" +
                        "<a href='#' class='button'>Đăng nhập ngay</a>" +
                        "</div>" +
                        "<div class='footer'>" +
                        "<p>Trân trọng,</p>" +
                        "<p>Vũ Ngọc Tú<br/>Giám đốc Cửa hàng Laptop ComNoOne</p>" +
                        "<p>&copy; 2024 Laptop ComNoOne. All rights reserved.</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>",
                nhanVien.getTen(),
                nhanVien.getEmail(),
                genPassWord,
                vaiTrosStr
        );

        helper.setText(emailContent, true); // true indicates the email content is HTML

        javaMailSender.send(mimeMessage);
    }



    public void sendForgotPasswordEmailForNhanVien(NhanVien nhanVien, String newPlainTextPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(nhanVien.getEmail());
        message.setSubject("Thực hiện thay đổi mật khẩu \"ComNoOne\"");
        message.setText("Chào anh/chị, " +
                "Dưới đậy là mật khẩu mới của bạn" +
                "Mật khẩu: " + newPlainTextPassword + "\n" +
                "Vui lòng dùng mật khẩu này để đăng nhập lại tài khoản của bạn" + "\n" +
                "Trân trọng" + "\n" +
                "Của hàng bán áo đấu thể thao PSG");
        javaMailSender.send(message);
    }
}
