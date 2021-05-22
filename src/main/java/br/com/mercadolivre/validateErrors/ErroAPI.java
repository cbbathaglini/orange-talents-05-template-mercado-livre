package br.com.mercadolivre.validateErrors;

public class ErroAPI {

	private String campo;
	private String erro;

	public ErroAPI(String campo, String erro) {
		this.campo = campo;
		this.erro = erro;
	}

	public String getCampo() {
		return campo;
	}

	public String getErro() {
		return erro;
	}



}
