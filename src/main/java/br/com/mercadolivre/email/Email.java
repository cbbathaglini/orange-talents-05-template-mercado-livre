package br.com.mercadolivre.email;

import br.com.mercadolivre.model.Usuario;

public class Email implements EmailSender{

    private Usuario usuarioDestinatario;

    private String titulo;

    private String assunto;

    private String mensagem;


    public Email(Usuario usuarioDestinatario, String titulo, String assunto, String mensagem) {
        this.usuarioDestinatario = usuarioDestinatario;
        this.titulo = titulo;
        this.assunto = assunto;
        this.mensagem = mensagem;
    }

    public  String enviar(){
        String email = "| Destinatário: " + this.usuarioDestinatario.getUsername() + "\n";
        if(this.assunto != null) email += "| Assunto: " + this.assunto + "\n";
        if(this.titulo != null) email += "| Título: " + this.titulo + "\n";
        email +=   "| Corpo da mensagem: " + this.mensagem + "\n";

        return email;
    }



}
