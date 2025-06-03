package br.dev.guilhermeViana.ClassificadoraIp.model;

import java.util.ArrayList;
import java.util.List;

public class ClassificadoraIp{
	
	private String ip;
	private int cidr;
	private String primeiroOcteto;
	private String segundoOcteto;
	private String terceiroOcteto;
	private String quartoOcteto;
	private String mascaraBinaria;
	private String mascaraDecimal;
	private String ipClasse;
	private String subRede;
	private double ipsubClasse;
	private List<String> listaSubRede = new ArrayList<>();
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getCidr() {
		return cidr;
	}
	public void setCidr(int cidr) {
		if (cidr < 0 || cidr> 32) {
			throw new IllegalArgumentException("CIDR incorreto: seu cidr deve ser entre 0 a 32");
		}
		this.cidr = cidr;
	}
	public int getPrimeiroOcteto() {
		int primeiroOctetoInt = Integer.parseInt(primeiroOcteto);
		return primeiroOctetoInt;
	}
	public void setPrimeiroOcteto(String primeiroOcteto) {
		this.primeiroOcteto = primeiroOcteto;
	}
	public String getMascaraBinaria() {
		return mascaraBinaria;
	}
	public void setMascaraBinaria(String mascaraBinaria) {
		this.mascaraBinaria = mascaraBinaria;
	}
	public String getMascaraDecimal() {
		return mascaraDecimal;
	}
	public void setMascaraDecimal(String mascaraDecimal) {
		this.mascaraDecimal = mascaraDecimal;
	}
	public String getIpClasse() {
		
		int primeiroOctetoInt = getPrimeiroOcteto();
		if(primeiroOctetoInt >= 1 && primeiroOctetoInt <= 126)
			ipClasse = "A";
		else if (primeiroOctetoInt >= 128 && primeiroOctetoInt <= 191)
			ipClasse = "B";
		else if (primeiroOctetoInt >= 192 && primeiroOctetoInt <= 223)
			ipClasse = "C";
		else if (primeiroOctetoInt >= 224 && primeiroOctetoInt <= 239)
			ipClasse = "D";
		else if (primeiroOctetoInt >= 240 && primeiroOctetoInt <= 255)
			ipClasse = "E";
		else if (primeiroOctetoInt <1 || primeiroOctetoInt > 255)
			throw new IllegalArgumentException("Nao e possivel encontrar endereco IP utilizando-se um numero menor que 1 e maior que 255, por favor coloque um numero valido.");
		return ipClasse;
	}
	
	public int getSegundoOcteto() {
		int segundoOctetoInt = Integer.parseInt(segundoOcteto);
		return segundoOctetoInt;
	}
	
	public void setSegundoOcteto (String segundoOcteto) {
	this.segundoOcteto = segundoOcteto;
	}
	
	public int getTerceiroOcteto() {
		int terceiroOctetoInt = Integer.parseInt(terceiroOcteto);
		return terceiroOctetoInt;
	}
	
	public void setTerceiroOcteto (String terceiroOcteto) {
	this.terceiroOcteto = terceiroOcteto;
	}
	
	public int getQuartoOcteto() {
		int quartoOctetoInt = Integer.parseInt(quartoOcteto);
		return quartoOctetoInt;
	}
	
	public void setQuartoOcteto (String quartoOcteto) {
	this.quartoOcteto = quartoOcteto;
	}
	
	public double getSubClasse() {
		if (cidr > 32) {
			System.out.println("Cidr maior que 32, n�o pode ser utilizado para a realiza��o da conta, por favor insira um numero menor ou igual a 32");
		} else if (cidr < 30) {
			ipsubClasse = Math.pow(2, 32 - cidr) - 2;
		} else {
			ipsubClasse = Math.pow(2, 32 - cidr);
		}
		
		return ipsubClasse;
	}
	
	public int getSubRedes() {
	    int cidrBase = 0;

	    switch (getIpClasse()) {
	        case "A":
	            cidrBase = 8;
	            break;
	        case "B":
	            cidrBase = 16;
	            break;
	        case "C":
	            cidrBase = 24; break;
	        default: return 0;
	    }
	    
	    if (cidr < cidrBase) {
	    	return 0;
	    }

	    return (int) Math.pow(2, cidr - cidrBase);
	}

	
	//gera mascara binaria
	private StringBuilder gerarMascaraBin(int cidr) {
		StringBuilder mascaraBinaria = new StringBuilder();
		for (int i = 0; i < 32; i++) {
			mascaraBinaria.append(i < cidr ? '1' : '0');
		}
		
		return mascaraBinaria;
	}
	
	public String gerarMascaraDecimal (StringBuilder binaria) {
		
		StringBuilder decimal = new StringBuilder();
		
		for (int i = 0; i < 4; i++) {
			String octeto = binaria.substring(i * 8, (i + 1) * 8);
			
			decimal.append(Integer.parseInt(octeto, 2));
			
			if (i < 3)
				decimal.append(".");
		}
		
		return decimal.toString();
	}
	
	private int ipParaDecimal(String ip) {
	    String[] octetos = ip.split("\\.");
	    int resultado = 0;
	    for (int i = 0; i < 4; i++) {
	        resultado |= (Integer.parseInt(octetos[i]) << (24 - (i * 8)));
	    }
	    return resultado;
	}

	
	private String decimalParaIp(int ipDecimal) {
	    return ((ipDecimal >> 24) & 0xFF) + "." +
	           ((ipDecimal >> 16) & 0xFF) + "." +
	           ((ipDecimal >> 8) & 0xFF) + "." +
	           (ipDecimal & 0xFF);
	}

	
	
	
	public void calculoDeSubRedes() {
		
		if (cidr < 24) {
			subRede = "Não há cálculo com cidr abaixo de 24";
		}
		
		if (cidr == 24) {
			subRede = "Não há possibilidades de sub rede";
		}
		
		if (cidr > 24) {
			int bitsInativos = 32 - cidr;
			int bitsAtivos = 8 - bitsInativos;
			
			int numeroDeRede = (int) Math.pow(2, bitsAtivos);
			int numeroDeHost = ((int) Math.pow(2, bitsInativos)) - 2;
			
			int[] binario = {128, 64, 32, 16, 8, 4, 2, 1};
			
			subRede = "Terá " +String.valueOf(numeroDeRede) + " Sub-Redes!";
			
			int valorIncrementoHost = 0;
			String[] octetos = ip.split("\\.");
			String rede = octetos[0] + "." + octetos[1] + "." + octetos[2] + ".";
			String intervalo;
			
			listaSubRede.clear();
			
			for (int i = 1; i <= numeroDeRede; i++) {
				
				listaSubRede.add("Sub-rede: " + i);
				listaSubRede.add("Ip da sub-rede: " + rede + valorIncrementoHost); 
				valorIncrementoHost++;
				intervalo = "Intervalo do Host: " + rede + valorIncrementoHost + " - ";
				valorIncrementoHost += numeroDeHost;
				intervalo += rede +valorIncrementoHost;
				listaSubRede.add(intervalo);
				valorIncrementoHost++;
				listaSubRede.add("IP de broadcast: " + rede + valorIncrementoHost);
				listaSubRede.add("------------------------------------------------------------------------------------");
				valorIncrementoHost++;
			}
		}
	}
	
	public List<String> listarSubRedes() {
	    List<String> lista = new ArrayList<>();
	    
	    int ipDecimal = ipParaDecimal(ip);
	    int quantidadeSubRedes = getSubRedes();

	    if (quantidadeSubRedes == 0) {
	        lista.add("Não há sub-redes calculáveis para este CIDR.");
	        return lista;
	    }

	    int blocos = (int) Math.pow(2, (32 - cidr)); // Tamanho do bloco

	    for (int i = 0; i < quantidadeSubRedes; i++) {
	        int inicio = ipDecimal + (i * blocos);
	        int fim = inicio + blocos - 1;

	        String ipRede = decimalParaIp(inicio);
	        String primeiroValido = decimalParaIp(inicio + 1);
	        String ultimoValido = decimalParaIp(fim - 1);
	        String broadcast = decimalParaIp(fim);

	        lista.add("Sub-rede " + (i + 1) + ":");
	        lista.add("  IP da Sub-rede: " + ipRede);
	        lista.add("  Primeiro IP válido: " + primeiroValido);
	        lista.add("  Último IP válido: " + ultimoValido);
	        lista.add("  IP de Broadcast: " + broadcast);
	        lista.add("---------------------------------------------------");
	    }

	    return lista;
	}



	
	public void mostrarDados() {
		
		mascaraBinaria = gerarMascaraBin(cidr).toString();
		mascaraDecimal = gerarMascaraDecimal(new StringBuilder(mascaraBinaria));
		ipClasse = getIpClasse();
	
		System.out.println("------------------------------------");
		System.out.println("IP informado: " + primeiroOcteto + "." + segundoOcteto + "." + terceiroOcteto + "."
				+ quartoOcteto + "/" + cidr);
		System.out.println("Primeiro octeto: " + primeiroOcteto);
		System.out.println("Classe do IP: " + "Classe " + ipClasse);
		System.out.println("Mascara binaria: " + mascaraBinaria);
		System.out.println("Mascara decimal: " + mascaraDecimal);
		System.out.println("IPs por sub-rede com /" + cidr + ": " + getSubClasse() + " IPs disponiveis");
		System.out.println("Quantidade de sub-redes poss�veis: " + getSubRedes());
		System.out.println("------------------------------------");
	}
	
	}
	



	    
	    