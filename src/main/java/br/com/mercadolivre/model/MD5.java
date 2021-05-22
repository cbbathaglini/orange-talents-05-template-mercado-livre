package br.com.mercadolivre.model;

import java.security.*;
import java.math.*;


/*
* O MD5 (Message-Digest algorithm 5) é um algoritmo de hash de 128 bits unidirecional desenvolvido
* pela RSA Data Security, Inc., descrito na RFC 1321, usado por softwares com protocolo ponto-a-ponto (P2P),
* verificação de integridade e logins. Foi desenvolvido para suceder ao MD4 que tinha alguns problemas de segurança.
* */

public class MD5 {
    public MD5() {
    }

    public String criptografar(String palavra){

        try {
            MessageDigest m = MessageDigest.getInstance("MD5"); //Obter uma instância do algoritmo a ser usado.
            m.update(palavra.getBytes(), 0, palavra.length()); //Passar a informação que se deseja criptografar para o algoritmo.
            return new BigInteger(1, m.digest()).toString(16);//Efetuar a criptografia.
        }catch (Exception e){
            System.out.println(e);
            return e.getMessage();
        }
    }
}