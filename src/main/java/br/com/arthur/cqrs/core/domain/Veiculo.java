package br.com.arthur.cqrs.core.domain;

import java.util.UUID;

public class Veiculo {
    private String id;
    private String marca;
    private String modelo;
    private String ano;
    private String renavam;
    private String placa;
    private String cor;

    public static class Builder {
        private String id;
        private String marca;
        private String modelo;
        private String ano;
        private String renavam;
        private String placa;
        private String cor;

        public Builder() {}


        public Builder comMarca(String marca) {
            this.marca = marca;
            return this;
        }

        public Builder comModelo(String modelo) {
            this.modelo = modelo;
            return this;
        }

        public Builder comAno(String ano) {
            this.ano = ano;
            return this;
        }

        public Builder comRenavam(String renavam) {
            this.renavam = renavam;
            return this;
        }

        public Builder comPlaca(String placa) {
            this.placa = placa;
            return this;
        }

        public Builder comCor(String cor) {
            this.cor = cor;
            return this;
        }

        public Veiculo build() {
            Veiculo veiculo = new Veiculo();
            veiculo.marca = this.marca;
            veiculo.ano = this.ano;
            veiculo.cor = this.cor;
            veiculo.modelo = this.modelo;
            veiculo.placa = this.placa;
            veiculo.validacaoDigitosRenavam(this.renavam);
            veiculo.renavam = this.renavam;
            return veiculo;
        }
    }

    public void validacaoDigitosRenavam(String renavam) {
        if (renavam.length() != 11)
            throw new IllegalArgumentException("RENAVAM inválido: precisa ter 11 digitos!");
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getAno() {
        return ano;
    }

    public String getRenavam() {
        return renavam;
    }

    public String getPlaca() {
        return placa;
    }

    public String getCor() {
        return cor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
