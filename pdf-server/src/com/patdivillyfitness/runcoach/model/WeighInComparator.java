package com.patdivillyfitness.runcoach.model;
import java.util.Comparator;


public class WeighInComparator implements Comparator<STWeighIn> {
	@Override
	public int compare(STWeighIn o1, STWeighIn o2) {
		return o1.getDate().compareTo(o2.getDate());
	}
}