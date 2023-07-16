package it.polito.tdp.PremierLeague.model;
import java.util.*;
import org.jgrapht.*;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	PremierLeagueDAO dao;
	Graph<Match, DefaultWeightedEdge> grafo;
	List<Arco> archi;
	
	public Model() {
		dao = new PremierLeagueDAO();
	}
	
	public void creagrafo(int mese, int minuti) {
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, dao.getVertex(mese));
		
		archi = new LinkedList<Arco>(dao.getArchi(mese, minuti));
		for(Arco a : archi) {
			Graphs.addEdge(grafo, a.getM1(), a.getM2(), a.getPeso());
		}
	}
	
	public List<Arco> getConnessa() {
		List<Arco> result = new LinkedList<>();
		
		int i = 0;
		Arco best = null;
		
		for(Arco a : archi) {
			if(a.getPeso()>i) {
				best = a;
				i = a.getPeso();
			}
		}
		
		result.add(best);
		
		for(Arco a : archi) {
			if(a.getPeso()==i && !a.equals(best)) {
				result.add(a);
			}
		}
		
		return result;
	}
	
	public boolean isGraphLoaded() {
		if(grafo.vertexSet().size()>0) {
			return true;
		}else {
			return false;
		}
	}
	
	public Graph<Match, DefaultWeightedEdge> getGrafo(){
		return grafo;
	}

	int worst;
	LinkedList<Match> best = new LinkedList<>();
	Match target;
	
	public LinkedList<Match> ricorsione(Match m1, Match m2) {
		worst = Integer.MAX_VALUE;
		best = new LinkedList<>();
		LinkedList<Match> parziale = new LinkedList<>();
		parziale.add(m1);
		target = m2;
		cerca(parziale, 0);
		
		return best;
	}
	
	private void cerca(LinkedList<Match> parziale, int score) {
		
		if(parziale.contains(target)) {
			if(score<worst) {
				worst = score;
				best = new LinkedList<>(parziale);
			}
			return;
		}
		
		for(Match m : Graphs.neighborListOf(grafo, parziale.getLast())){
			//RICORDARTI SEMPRE DI CONTROLLARE SE ACICLICO
			
			//controllo se m non è m
			boolean flag = true;
			
			if(parziale.contains(m)) {
				flag = false;
			}
			
			/*if(flag == true) {
				for(Match mm : parziale) {
					if(m.getTeamHomeID()==mm.getTeamHomeID() && m.getTeamAwayID()==mm.getTeamAwayID()) {
						flag = false;
					}else if(m.getTeamHomeID()==mm.getTeamAwayID() && m.getTeamAwayID()==mm.getTeamHomeID()) {
						flag = false;
					}
				}
			}*/
			
			if(flag == true) {
				
				//aggiorno score
				int i = (int)grafo.getEdgeWeight(grafo.getEdge(m, parziale.getLast()));
				score += i;
				
				//aggiungo m
				parziale.add(m);
				
				//ricorsione
				cerca(parziale, score);
				
				//aggiorno score
				score -=i;
				
				//tolgo m
				parziale.remove(m);
				
			}
			
		}
	}
	
	public int getScore() {
		return worst;
	}

}
