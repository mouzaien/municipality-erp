package com.bkeryah.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AbcFactory4 {

	public static Collection<Abcd2> generateCollection() {
		List<Abcd2> coll = new ArrayList<>();
		coll.add(new Abcd2());
		return coll;
	}

}
