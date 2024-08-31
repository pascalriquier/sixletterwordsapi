package com.one16.sixletterwordsapi.domain;

public record WoordensamenstellingSolverConfiguration(int aantalWoorden, int woordLengte) {
  
  public static class Builder {
    private int woordLengte = 6;
    private int aantalWoorden = 2;
    
    public WoordensamenstellingSolverConfiguration build() {
      return new WoordensamenstellingSolverConfiguration(aantalWoorden, woordLengte);
    }
    
    public Builder metWoordLengte(int woordLengte) {
      this.woordLengte = woordLengte;
      return this;
    }
    
    public Builder metAantalWoorden(int aantalWoorden) {
      this.aantalWoorden = aantalWoorden;
      return this;
    }
  }
}