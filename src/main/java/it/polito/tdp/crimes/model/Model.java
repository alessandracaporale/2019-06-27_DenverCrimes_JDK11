package it.polito.tdp.crimes.model;

import java.util.*;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.*;


public class Model {

	EventsDAO dao = new EventsDAO();
	Graph<String, DefaultWeightedEdge> graph;
	
	public List<Event> listAllEvents(){
		return dao.listAllEvents();
	}
	
	public List<String> getAllCategories() {
		return dao.getAllCategories();
	}
	
	public List<Adiacenza> getAdiacenze(String category, int month) {
		return dao.getAdiacenze(category, month);
	}
	
	public void creaGrafo(String category, int mese) {
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//vertici
		Graphs.addAllVertices(graph, dao.getOffenseType(category, mese));
		
		//archi
		for (Adiacenza a : this.getAdiacenze(category, mese)) {
			if(this.graph.getEdge(a.getV1(), a.getV2()) == null) {
				Graphs.addEdgeWithVertices(graph, a.getV1(), a.getV2(), a.getPeso());
			}
		}
		
		System.out.println(graph.vertexSet().size());
		System.out.println(graph.edgeSet().size());
	}
	
	public double getPesoMedio() {
		double pesoMedio = 0;
		for (DefaultWeightedEdge e : this.graph.edgeSet()) {
			pesoMedio += this.graph.getEdgeWeight(e);
		}
		pesoMedio = pesoMedio / this.graph.edgeSet().size();
		return pesoMedio;
	}
	
	public String getArchiMaggioriDiPM (String category, int month) {
		List<Adiacenza> listaAdiacenze = new LinkedList<>();
		String result = "";
		
		for(Adiacenza a : this.getAdiacenze(category,  month)) {
			if(a.getPeso() > this.getPesoMedio()) {
				listaAdiacenze.add(a);
			}
		}
		
		for (Adiacenza a : listaAdiacenze) {
			result += a.getV1()+", "+a.getV2()+", "+ a.getPeso()+"\n";
		}
		return result;
	}
 }
