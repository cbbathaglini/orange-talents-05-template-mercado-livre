package br.com.mercadolivre.upload;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.stream.Stream;

public interface UploaderImage{
    String gerarURL(MultipartFile file);
    String  imgEmBase64(MultipartFile file) throws IOException;
}
