package com.chup.mobcoinsplus.extras;

import java.util.*;

public class CoinsTop {
    public static HashMap<UUID, Integer> sortByValue(HashMap<UUID, Integer> hm)
    {
        List<Map.Entry<UUID, Integer> > list =
                new LinkedList<Map.Entry<UUID, Integer> >(hm.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<UUID, Integer> >() {
            public int compare(Map.Entry<UUID, Integer> o1, Map.Entry<UUID, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        HashMap<UUID, Integer> temp = new LinkedHashMap<UUID, Integer>();
        for (Map.Entry<UUID, Integer> aa : list) { temp.put(aa.getKey(), aa.getValue()); }
        return temp;
    }
}
