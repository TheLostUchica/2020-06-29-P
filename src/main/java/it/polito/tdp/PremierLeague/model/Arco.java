package it.polito.tdp.PremierLeague.model;

public class Arco {
	
	Match m1;
	Match m2;
	int peso;
	public Arco(Match m1, Match m2, int peso) {
		super();
		this.m1 = m1;
		this.m2 = m2;
		this.peso = peso;
	}
	public Match getM1() {
		return m1;
	}
	public Match getM2() {
		return m2;
	}
	public int getPeso() {
		return peso;
	}
	@Override
	public String toString() {
		return m1 + " - " + m2 + " : " + peso;
	}
	
	

}
