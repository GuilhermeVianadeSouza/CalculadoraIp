package br.dev.guilhermeViana.ClassificadoraIp.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import br.dev.guilhermeViana.ClassificadoraIp.model.ClassificadoraIp;

public class ClassificadoraIpGui {

    // Componentes da tela
    private JLabel labelIP, labelCIDR, labelErro; 
    private JTextField textOct1, textOct2, textOct3, textOct4, textCIDR;
    private JButton buttonCalcular, buttonApagar;
    private JTextArea areaResultado;
    private JScrollPane scrollResultado;

    public void criarTela() {
        JFrame tela = new JFrame();
        tela.setTitle("Classificadora de IP");
        tela.setSize(600, 650);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setResizable(false);
        tela.setLayout(null);

        Container container = tela.getContentPane();

        // Fontes
        Font fonteTitulo = new Font("Arial", Font.BOLD, 24);
        Font fontePadrao = new Font("Arial", Font.PLAIN, 16);
        Font fonteResultado = new Font("Monospaced", Font.PLAIN, 14);
        Font fontErro = new Font ("Bold", Font.BOLD, 18);

        // Label IP
        labelIP = new JLabel("Endereço IP:");
        labelIP.setFont(fonteTitulo);
        labelIP.setBounds(50, 10, 200, 30);

        // Campos para o IP (4 octetos)
        textOct1 = new JTextField();
        textOct1.setBounds(50, 50, 50, 30);

        textOct2 = new JTextField();
        textOct2.setBounds(110, 50, 50, 30);

        textOct3 = new JTextField();
        textOct3.setBounds(170, 50, 50, 30);

        textOct4 = new JTextField();
        textOct4.setBounds(230, 50, 50, 30);

        // Label CIDR
        labelCIDR = new JLabel("CIDR (/):");
        labelCIDR.setFont(fonteTitulo);
        labelCIDR.setBounds(300, 50, 100, 30);

        // Campo CIDR
        textCIDR = new JTextField();
        textCIDR.setBounds(400, 50, 50, 30);

        // Botão Calcular
        buttonCalcular = new JButton("Calcular");
        buttonCalcular.setBounds(50, 100, 150, 40);

        // Botão Apagar
        buttonApagar = new JButton("Apagar");
        buttonApagar.setBounds(220, 100, 150, 40);

        // Área de texto para resultado dentro de scroll
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(fonteResultado);
        areaResultado.setLineWrap(true);
        areaResultado.setWrapStyleWord(true);

        scrollResultado = new JScrollPane(areaResultado);
        scrollResultado.setBounds(50, 160, 500, 280);

        // Label de erro
        labelErro = new JLabel("Erro: Verifique os campos. Utilize apenas números válidos.");
        labelErro.setForeground(Color.RED);
        labelErro.setBounds(50, 450, 500, 30);
        labelErro.setFont(fontErro);
        labelErro.setVisible(false);

        // Ações dos botões

        buttonCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    labelErro.setVisible(false);

                    String oct1 = textOct1.getText().trim();
                    String oct2 = textOct2.getText().trim();
                    String oct3 = textOct3.getText().trim();
                    String oct4 = textOct4.getText().trim();
                    String cidrStr = textCIDR.getText().trim();
                    
                    int cidr = Integer.parseInt(cidrStr);
                    
                    if (oct1.equals("00") || oct1.equals("000") ||
                            oct2.equals("00") || oct2.equals("000") ||
                            oct3.equals("00") || oct3.equals("000") ||
                            oct4.equals("00") || oct4.equals("000")) {
                            throw new IllegalArgumentException("Erro geral");
                       
                        }
                    
                    if (cidr <= 0 || cidr > 32) {
                    	throw new IllegalArgumentException("Erro geral");
                    }

      

                    ClassificadoraIp ip = new ClassificadoraIp();
                    ip.setPrimeiroOcteto(oct1);
                    ip.setSegundoOcteto(oct2);
                    ip.setTerceiroOcteto(oct3);
                    ip.setQuartoOcteto(oct4);
                    ip.setCidr(cidr);
                    ip.setIp(oct1 + "." + oct2 + "." + oct3 + "." + oct4);

                    ip.mostrarDados();

                    StringBuilder resultado = new StringBuilder();
                    resultado.append("IP Classe: ").append(ip.getIpClasse()).append("\n");
                    resultado.append("Máscara Binária: ").append(ip.getMascaraBinaria()).append("\n");
                    resultado.append("Máscara Decimal: ").append(ip.getMascaraDecimal()).append("\n");
                    resultado.append("IPs por sub-rede: ").append(ip.getSubClasse()).append("\n");
                    resultado.append("Quantidade de sub-redes: ").append(ip.getSubRedes()).append("\n\n");

                    for (String linha : ip.listarSubRedes()) {
                        resultado.append(linha).append("\n");
                    }

                    areaResultado.setText(resultado.toString());

                } catch (Exception ex) {
                    areaResultado.setText("");
                    labelErro.setVisible(true);
                    labelErro.setText("<html><body style='width: 250px'> Numeracao invalida");
                    labelErro.setVisible(true);
                }
            }
        });

        buttonApagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textOct1.setText("");
                textOct2.setText("");
                textOct3.setText("");
                textOct4.setText("");
                textCIDR.setText("");
                areaResultado.setText("");
                labelErro.setVisible(false);
            }
        });

        // Adicionando os componentes
        container.add(labelIP);
        container.add(textOct1);
        container.add(textOct2);
        container.add(textOct3);
        container.add(textOct4);
        container.add(labelCIDR);
        container.add(textCIDR);
        container.add(buttonCalcular);
        container.add(buttonApagar);
        container.add(scrollResultado);
        container.add(labelErro);

        // Exibir tela
        tela.setVisible(true);
    }
}
