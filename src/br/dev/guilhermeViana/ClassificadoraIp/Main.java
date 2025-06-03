package br.dev.guilhermeViana.ClassificadoraIp;

import br.dev.guilhermeViana.ClassificadoraIp.gui.ClassificadoraIpGui;
import br.dev.guilhermeViana.ClassificadoraIp.model.ClassificadoraIp;

public class Main {	
    public static void main(String[] args) {
    	
    	ClassificadoraIpGui tela = new ClassificadoraIpGui();
        tela.criarTela();
    	
//    	ip1.setPrimeiroOcteto("192");
//    	ip1.setSegundoOcteto("168");
//    	ip1.setTerceiroOcteto("2");
//    	ip1.setQuartoOcteto("2");
//    	ip1.setCidr(24);
//    	
//    	ip1.setIp(ip1.getPrimeiroOcteto() + "." + ip1.getSegundoOcteto() + "." + ip1.getTerceiroOcteto() + "." + ip1.getQuartoOcteto());
//
//    	
//    	ip1.mostrarDados();
//    	ip1.listarSubRedes();
    }
}