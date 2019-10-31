package com.bkeryah.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AbcFactory3 {

	public static Collection<ArcRecords> generateCollection() {
		List<ArcRecords> coll = new ArrayList<>();
		coll.add(new ArcRecords());
		return coll;
	}

}
