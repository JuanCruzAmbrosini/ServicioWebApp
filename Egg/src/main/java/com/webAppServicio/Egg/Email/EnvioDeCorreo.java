/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webAppServicio.Egg.Email;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Juan Cruz
 */
public class EnvioDeCorreo {

    public void transfer_to_email(String correoEmisor, String mensaje, String asunto) {

        String correoRemitente = "juancruz.ambrosini2@gmail.com";
        String contraseña = "ucpuhmdupjlhzoxc";

        Properties EEmail = new Properties();

        EEmail.put("mail.smtp.host", "smtp.gmail.com");
        EEmail.setProperty("mail.smtp.starttls.enable", "true");
        EEmail.put("mail.smtp.port", "587");
        EEmail.setProperty("mail.smtp.port", "587");
        EEmail.put("mail.smtp.user", correoEmisor);
        EEmail.setProperty("mail.smtp.auth", "true");

        Session sesion = Session.getDefaultInstance(EEmail);
        MimeMessage mail = new MimeMessage(sesion);

        try {
            
            mail.setFrom(new InternetAddress(correoEmisor));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(correoRemitente));
            mail.setSubject(asunto);
            mail.setText(mensaje);

            Transport transporte = sesion.getTransport("smtp");
            transporte.connect(correoEmisor, contraseña);
            transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
            transporte.close();

        } catch (Exception ex) {

            System.out.println(ex.getMessage());
            
        }

    }

}
