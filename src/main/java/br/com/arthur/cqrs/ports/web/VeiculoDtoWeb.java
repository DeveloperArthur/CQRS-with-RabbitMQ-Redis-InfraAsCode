package br.com.arthur.cqrs.ports.web;

import br.com.arthur.cqrs.core.domain.Veiculo;

public class VeiculoDtoWeb {
    private String id;
    private String marca;
    private String modelo;
    private String ano;
    private String renavam;
    private String placa;
    private String cor;

    public VeiculoDtoWeb(Veiculo veiculo) {
        this.id = veiculo.getId();
        this.ano = veiculo.getAno();
        this.cor = veiculo.getCor();
        this.marca = veiculo.getMarca();
        this.modelo = veiculo.getModelo();
        this.placa = veiculo.getPlaca();
        this.renavam = veiculo.getRenavam();
    }

    public VeiculoDtoWeb() {}

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getRenavam() {
        return renavam;
    }

    public void setRenavam(String renavam) {
        this.renavam = renavam;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Veiculo converte() {
        return new Veiculo.Builder()
                .comAno(this.ano)
                .comCor(this.cor)
                .comMarca(this.marca)
                .comModelo(this.modelo)
                .comPlaca(this.placa)
                .comRenavam(this.renavam)
                .build();
    }
}
