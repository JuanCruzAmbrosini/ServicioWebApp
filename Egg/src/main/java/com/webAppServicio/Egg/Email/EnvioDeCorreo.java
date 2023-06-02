/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webAppServicio.Egg.Email;

import com.webAppServicio.Egg.Exceptions.MyException;
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

    public void transfer_to_email(String correoRemitente, String mensaje, String asunto) throws MyException {

        validarEmail(correoRemitente, mensaje, asunto);
        
        String correoEmisor = "juancruz.ambrosini2@gmail.com";
        String contrasena = "ucpuhmdupjlhzoxc";

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
            transporte.connect(correoEmisor, contrasena);
            transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
            transporte.close();

        } catch (Exception ex) {

            System.out.println(ex.getMessage());
            
        }

    }

    public void validarEmail (String correoRemitente, String mensaje, String asunto) throws MyException{
        
        if (correoRemitente == null || correoRemitente.isEmpty()) {

            throw new MyException("No se registró una entrada válida en el campo del correo. Por favor, inténtelo nuevamente.");

        }

        if (mensaje == null || mensaje.isEmpty()) {

            throw new MyException("No se registró una entrada válida en el campo de la matrícula. Por favor, inténtelo nuevamente.");

        }

        if (asunto.equalsIgnoreCase("Servicio") || asunto.isEmpty()) {

            throw new MyException("No se registró una entrada válida en el campo del nombre. Por favor, inténtelo nuevamente.");

        }
        
    }
    
}
