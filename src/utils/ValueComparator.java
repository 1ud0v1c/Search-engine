package utils;

import java.util.Comparator;
import java.util.Map;

public class ValueComparator implements Comparator {
	Map base;
	
	public ValueComparator(Map base) {
		this.base = base;
	}


	@Override
	public int compare(Object o1, Object o2) {
		if(((Double)base.get(o1)) >= ((Double)base.get(o2))) {
			return -1;
		}
		return 1;
	}

}
